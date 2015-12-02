package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.FsElement;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.dbmapper.TvSerieDbMapper;
import it.ninjatech.kvo.tvserie.dbmapper.TvSeriesPathEntityDbMapper;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.util.Labels;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.alee.utils.filefilter.DirectoriesFilter;

public final class TvSerieWorkerTasks {

    private static final Set<String> VIDEO_FILE_EXTENSIONS = new HashSet<>(Arrays.asList(new String[] { "webm", "mkv", "flv", "vob", "ogv", "ogg", "drc",
                                                                                                       "mng", "avi", "mov", "qt", "wmv", "yuv", "rm",
                                                                                                       "rmvb", "asf", "mp4", "m4p", "m4v", "mpg", "mp2",
                                                                                                       "mpeg", "mpe", "mpv", "m2v", "svi", "3gp", "3g2",
                                                                                                       "mxf", "roq", "nsv" }));

    protected static List<TvSerie> search(String name, EnhancedLocale language, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
        List<TvSerie> result = new ArrayList<>();

        progressNotifier.notifyTaskUpdate(name, null);
        result.addAll(TheTvDbManager.getInstance().search(name, language));

        return result;
    }

    protected static boolean check(File path, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
        boolean result = false;

        progressNotifier.notifyTaskUpdate(Labels.TV_SERIE_TASK_CHECK, null);
        result = path.exists() && path.isDirectory();

        return result;
    }

    protected static void scan(TvSeriesPathEntity tvSeriesPathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
        File root = new File(tvSeriesPathEntity.getPath());
        File[] directories = root.listFiles(new DirectoriesFilter());
        progressNotifier.notifyTaskInit(Labels.START_SCANNING, directories.length);
        for (int i = 0; i < directories.length; i++) {
            File directory = directories[i];
            progressNotifier.notifyTaskUpdate(directory.getName(), null);
            tvSeriesPathEntity.addTvSerie(directory);
            progressNotifier.notifyTaskUpdate(directory.getName(), i + 1);
        }
    }

    protected static void save(TvSeriesPathEntity tvSeriesPathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
        progressNotifier.notifyTaskInit(Labels.dbSavingEntity(tvSeriesPathEntity.getLabel()), 1);
        TvSeriesPathEntityDbMapper mapper = new TvSeriesPathEntityDbMapper();
        mapper.save(tvSeriesPathEntity);
        progressNotifier.notifyTaskUpdate(Labels.dbSavingEntity(tvSeriesPathEntity.getLabel()), 1);
    }

    protected static void delete(TvSeriesPathEntity tvSeriesPathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
        progressNotifier.notifyTaskUpdate(Labels.TV_SERIE_TASK_DELETE, null);
        TvSeriesPathEntityDbMapper mapper = new TvSeriesPathEntityDbMapper();
        mapper.delete(tvSeriesPathEntity);
    }

    protected static void scan(TvSeriePathEntity tvSeriePathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
        progressNotifier.notifyTaskUpdate(Labels.TV_SERIE_TASK_SCAN, null);

        TvSerie tvSerie = tvSeriePathEntity.getTvSerie();
        if (tvSerie != null) {
            for (TvSerieSeason season : tvSerie.getSeasons()) {
                for (TvSerieEpisode episode : season.getEpisodes()) {
                    episode.clearFilenames();
                }
            }
        }

        SortedSet<FsElement> fsElements = new TreeSet<>();
        SortedSet<String> extraFanarts = new TreeSet<>();
        Map<Integer, SortedSet<String>> videoFiles = new HashMap<>();
        Map<Integer, SortedSet<String>> subtitleFiles = new HashMap<>();

        File main = new File(tvSeriePathEntity.getPath());
        Deque<Entry<File, FsElement>> toScan = new ArrayDeque<>();
        File[] files = main.listFiles();
        for (File file : files) {
            if (!file.isHidden()) {
                FsElement fsElement = new FsElement(file.getName(), file.isDirectory());
                fsElements.add(fsElement);
                if (file.isDirectory()) {
                    toScan.offer(new SimpleEntry<>(file, fsElement));
                }
            }
        }

        while (!toScan.isEmpty()) {
            Entry<File, FsElement> elementToScan = toScan.pop();
            boolean extrafanart = elementToScan.getValue().getName().equalsIgnoreCase(TvSerieHelper.EXTRAFANART);
            Integer seasonNumber = null;
            if (StringUtils.startsWithIgnoreCase(elementToScan.getValue().getName(), TvSerieHelper.SEASON)) {
                String fileName = StringUtils.normalizeSpace(elementToScan.getValue().getName());
                seasonNumber = Integer.valueOf(fileName.substring(fileName.lastIndexOf(' ') + 1));
            }

            files = elementToScan.getKey().listFiles();
            for (File file : files) {
                if (!file.isHidden()) {
                    FsElement fsElement = new FsElement(file.getName(), file.isDirectory());
                    elementToScan.getValue().addChild(fsElement);
                    if (file.isDirectory()) {
                        toScan.offer(new SimpleEntry<>(file, fsElement));
                    }
                    else {
                        if (extrafanart && StringUtils.endsWithIgnoreCase(file.getName(), "jpg")) {
                            extraFanarts.add(file.getName());
                        }
                        else if (seasonNumber != null) {
                            if (isVideoFile(file.getName())) {
                                if (tvSerie == null || !TvSerieHelper.setEpisodeFilename(tvSerie, file)) {
                                    SortedSet<String> seasonVideoFiles = videoFiles.get(seasonNumber);
                                    if (seasonVideoFiles == null) {
                                        seasonVideoFiles = new TreeSet<>();
                                        videoFiles.put(seasonNumber, seasonVideoFiles);
                                    }
                                    seasonVideoFiles.add(file.getAbsolutePath());
                                }
                            }
                            else if (isSubtitleFile(file.getName())) {
                                if (tvSerie == null || !TvSerieHelper.addEpisodeSubtitleFilename(tvSerie, file)) {
                                    SortedSet<String> seasonSubtitleFiles = subtitleFiles.get(seasonNumber);
                                    if (seasonSubtitleFiles == null) {
                                        seasonSubtitleFiles = new TreeSet<>();
                                        subtitleFiles.put(seasonNumber, seasonSubtitleFiles);
                                    }
                                    seasonSubtitleFiles.add(file.getAbsolutePath());
                                }
                            }
                        }
                    }
                }
            }
        }

        tvSeriePathEntity.setFsElements(fsElements);
        tvSeriePathEntity.setVideoFiles(videoFiles);
        tvSeriePathEntity.setSubtitleFiles(subtitleFiles);
        tvSeriePathEntity.setExtraFanarts(extraFanarts);
    }

    protected static void fetch(TvSeriePathEntity tvSeriePathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
        TvSerie tvSerie = tvSeriePathEntity.getTvSerie();
        tvSerie.clear();

        progressNotifier.notifyTaskUpdate(Labels.TV_SERIE_TASK_FETCH_THE_TV_DB, null);
        TheTvDbManager.getInstance().getData(tvSeriePathEntity.getTvSerie());
        progressNotifier.notifyTaskUpdate(Labels.TV_SERIE_TASK_FETCH_FANART_TV, null);
        FanarttvManager.getInstance().getData(tvSeriePathEntity.getTvSerie());
    }

    protected static void save(TvSeriePathEntity tvSeriePathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
        progressNotifier.notifyTaskUpdate(Labels.TV_SERIE_TASK_SAVE, null);
        TvSerieDbMapper mapper = new TvSerieDbMapper();
        mapper.save(tvSeriePathEntity.getTvSerie());
    }

    protected static void delete(TvSeriePathEntity tvSeriePathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
        progressNotifier.notifyTaskUpdate(Labels.TV_SERIE_TASK_DELETE, null);
        TvSerieDbMapper mapper = new TvSerieDbMapper();
        mapper.delete(tvSeriePathEntity.getTvSerie());
    }

    private static boolean isVideoFile(String name) {
        boolean result = false;

        String ext = name.substring(name.lastIndexOf('.') + 1);
        result = VIDEO_FILE_EXTENSIONS.contains(ext);

        return result;
    }

    private static boolean isSubtitleFile(String name) {
        return StringUtils.endsWithIgnoreCase(name, "srt");
    }

    private TvSerieWorkerTasks() {
    }

}
