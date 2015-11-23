package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.tvserie.worker.TvSerieSeasonWorker.TvSerieSeasonWorkerInputData;
import it.ninjatech.kvo.util.Labels;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

public class TvSerieSeasonWorker extends AbstractTvSerieWorker<TvSerieSeasonWorkerInputData, Boolean> {

    public static TvSerieSeasonWorkerInputData makeInputData(TvSerieSeason season,
                                                             Map<TvSerieEpisode, String> videoEpisodeMap,
                                                             Map<TvSerieEpisode, Map<String, EnhancedLocale>> subtitleEpisodeMap,
                                                             File seasonImage) {
        return new TvSerieSeasonWorkerInputData(season, videoEpisodeMap, subtitleEpisodeMap, seasonImage);
    }
    
    private static String getSubtitleFilename(String filename, EnhancedLocale language, TvSerieEpisode episode) {
        String result = null;

        String ext = filename.substring(filename.lastIndexOf('.'));

        int index = 1;
        do {
            String indexSuffix = index == 1 ? "" : String.format(".%d", index);
            result = String.format("%s%s.%s%s", TvSerieHelper.getFullEpisodeName(episode), indexSuffix, language.getLanguageCode(), ext);
            index++;
        } while (episode.getSubtitleFilenames().contains(result));

        return result;
    }
    
    public TvSerieSeasonWorker(TvSerieSeasonWorkerInputData input) {
        super(input);
    }
    
    @Override
    public Boolean work() throws Exception {
        Boolean result = true;
     
        this.progressNotifier.notifyWorkerMessage(Labels.tvSerieSeasonWorker(Labels.TV_SERIE_SEASON_WORKER_1,
                                                                             this.input.season.getTvSerie().getName(),
                                                                             this.input.season.getNumber()));
        this.progressNotifier.notifyTaskInit(null, this.input.videoEpisodeMap.size() + this.input.subtitleEpisodeMap.size() + 2);

        int update = 0;

        File seasonDir = TvSerieHelper.getLocalSeasonPath(this.input.season);
        for (TvSerieEpisode episode : this.input.videoEpisodeMap.keySet()) {
            this.progressNotifier.notifyTaskUpdate(Labels.tvSerieSeasonWorker(Labels.TV_SERIE_SEASON_WORKER_2, TvSerieHelper.getEpisodeName(episode), null), null);

            String filename = this.input.videoEpisodeMap.get(episode);
            File sourceFile = new File(seasonDir, filename);
            String ext = filename.substring(filename.lastIndexOf('.'));
            File targetFile = new File(seasonDir, String.format("%s%s", TvSerieHelper.getFullEpisodeName(episode), ext));

            if (!sourceFile.renameTo(targetFile)) {
                throw new Exception(Labels.getFailedToRename(sourceFile.getAbsolutePath(), targetFile.getAbsolutePath()));
            }
            episode.setFilename(targetFile.getAbsolutePath());

            this.progressNotifier.notifyTaskUpdate(null, ++update);
        }

        for (TvSerieEpisode episode : this.input.subtitleEpisodeMap.keySet()) {
            this.progressNotifier.notifyTaskUpdate(Labels.tvSerieSeasonWorker(Labels.TV_SERIE_SEASON_WORKER_3, TvSerieHelper.getEpisodeName(episode), null), null);

            Map<String, EnhancedLocale> subtitleMap = this.input.subtitleEpisodeMap.get(episode);
            for (String filename : subtitleMap.keySet()) {
                File sourceFile = new File(seasonDir, filename);
                File targetFile = new File(seasonDir, getSubtitleFilename(filename, subtitleMap.get(filename), episode));

                if (!sourceFile.renameTo(targetFile)) {
                    throw new Exception(Labels.getFailedToRename(sourceFile.getAbsolutePath(), targetFile.getAbsolutePath()));
                }

                episode.addSubtitleFilename(targetFile.getAbsolutePath());
            }

            this.progressNotifier.notifyTaskUpdate(null, ++update);
        }

        if (!this.input.seasonImage.getParentFile().equals(seasonDir.getParentFile())) {
            File targetFile = new File(seasonDir.getParent(), TvSerieHelper.getSeasonPosterFilename(this.input.season));
            Files.copy(this.input.seasonImage.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        this.progressNotifier.notifyTaskUpdate(null, ++update);

        this.progressNotifier.notifyTaskUpdate(Labels.STORING_TV_SERIE_SEASON, null);
        // TODO
//      PersistenceHandler.save(this.season);
        this.progressNotifier.notifyTaskUpdate(null, ++update);

        return result;
    }
    
    public static class TvSerieSeasonWorkerInputData {
        
        private final TvSerieSeason season;
        private final Map<TvSerieEpisode, String> videoEpisodeMap;
        private final Map<TvSerieEpisode, Map<String, EnhancedLocale>> subtitleEpisodeMap;
        private final File seasonImage;
        
        private TvSerieSeasonWorkerInputData(TvSerieSeason season, Map<TvSerieEpisode, String> videoEpisodeMap, Map<TvSerieEpisode, Map<String, EnhancedLocale>> subtitleEpisodeMap, File seasonImage) {
            this.season = season;
            this.videoEpisodeMap = videoEpisodeMap;
            this.subtitleEpisodeMap = subtitleEpisodeMap;
            this.seasonImage = seasonImage;
        }
        
    }
    
}
