package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.FsElement;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.util.Labels;

import java.io.File;
import java.io.FilenameFilter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.alee.utils.filefilter.DirectoriesFilter;

public final class TvSerieWorkerTasks {

	protected static boolean check(File path, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
		boolean result = false;

		String message = "Checking " + path.getName() + " existence";

		progressNotifier.notifyTaskInit(message, 1);
		result = path.exists() && path.isDirectory();
		progressNotifier.notifyTaskUpdate(message, 1);

		return result;
	}

	protected static TvSeriePathEntity fetch(TvSeriePathEntity tvSeriePathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
		TvSeriePathEntity result = null;

		// TODO handle progress notifier
		if (check(new File(tvSeriePathEntity.getPath()), progressNotifier)) {
			if (tvSeriePathEntity.getTvSerie() != null) {
				TheTvDbManager.getInstance().getData(tvSeriePathEntity.getTvSerie());
				FanarttvManager.getInstance().getData(tvSeriePathEntity.getTvSerie());
				// TODO scan
				// TODO update object
				// TODO save
			}
		}
		else {
			if (tvSeriePathEntity.getTvSerie() != null) {
				// TODO delete
			}
		}

		return result;
	}

	protected static void save(TvSeriesPathEntity tvSeriesPathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
		progressNotifier.notifyTaskInit(Labels.dbSavingEntity(tvSeriesPathEntity.getLabel()), 1);
		TvSeriesPathEntityDbMapper mapper = new TvSeriesPathEntityDbMapper();
		mapper.save(tvSeriesPathEntity);
		progressNotifier.notifyTaskUpdate(Labels.dbSavingEntity(tvSeriesPathEntity.getLabel()), 1);
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

	protected static TvSeriePathEntity scan(TvSeriePathEntity tvSeriePathEntity, AbstractTvSerieWorker.ProgressNotifier progressNotifier) throws Exception {
		TvSeriePathEntity result = null;

		// TODO handle progress notifier
		if (check(new File(tvSeriePathEntity.getPath()), progressNotifier)) {
			tvSeriePathEntity.clearFileData();
			
			int taskNumber = 1;
			int taskCount = 1;
			
			Set<String> videoFilesRelated = new HashSet<>();
			Set<String> subtitleFilesRelated = new HashSet<>();
			if (tvSeriePathEntity.getTvSerie() != null) {
				taskCount += 2;
				
				progressNotifier.notifyTaskInit(getTaskMessage(taskNumber, taskCount, "Getting related files"), 1);
				for (TvSerieSeason season : tvSeriePathEntity.getTvSerie().getSeasons()) {
					for (TvSerieEpisode episode : season.getEpisodes()) {
						if (StringUtils.isNotBlank(episode.getFilename())) {
							videoFilesRelated.add(episode.getFilename());
						}
						subtitleFilesRelated.addAll(episode.getSubtitleFilenames());
					}
				}
				progressNotifier.notifyTaskUpdate(getTaskMessage(taskNumber, taskCount, "Getting related files"), 1);
				taskNumber++;
			}
			
			fsScan(tvSeriePathEntity, videoFilesRelated, subtitleFilesRelated, progressNotifier, taskNumber, taskCount);
			taskNumber++;
			
			if (tvSeriePathEntity.getTvSerie() != null) {
				// TODO save
			}
			
			result = tvSeriePathEntity;
		}
		else {
			if (tvSeriePathEntity.getTvSerie() != null) {
				// TODO delete
			}
		}

		return result;
	}

	// TODO controllare che videoFilesRelated & subtitleFilesRelated contengano i path completi dei file
	private static void fsScan(TvSeriePathEntity tvSeriePathEntity, Set<String> videoFilesRelated, Set<String> subtitleFilesRelated, AbstractTvSerieWorker.ProgressNotifier progressNotifier, int taskNumber, int taskCount) throws Exception {
		progressNotifier.notifyTaskInit(getTaskMessage(taskNumber, taskCount, "Preparing to scan files"), 1);
		
		File main = new File(tvSeriePathEntity.getPath());

		Deque<Entry<File, FsElement>> toScan = new ArrayDeque<>();
		File[] files = main.listFiles();
		for (File file : files) {
			if (!file.isHidden()) {
				progressNotifier.notifyTaskUpdate(getTaskMessage(taskNumber, taskCount, file.getName()), null);
				FsElement fsElement = new FsElement(file.getName(), file.isDirectory());
				tvSeriePathEntity.addFsElement(fsElement);

				if (fsElement.getDirectory()) {
					toScan.offer(new SimpleEntry<>(file, fsElement));

					if (fsElement.getName().equalsIgnoreCase(TvSerieHelper.EXTRAFANART)) {
						tvSeriePathEntity.setExtraFanarts(new TreeSet<>(Arrays.asList(file.list(new ExtrafanartFilenameFilter()))));
					}
					else if (StringUtils.startsWithIgnoreCase(fsElement.getName(), TvSerieHelper.SEASON)) {
						// TODO questa parte qua non va bane: bisogna ristrutturare il tutto in modo da capire se i file degli episodi esistono ancora. nel caso non ci fossero più bisogna rimuoverli per poter fare l'update nel db
						
						String fileName = StringUtils.normalizeSpace(fsElement.getName());
						Integer number = Integer.valueOf(fileName.substring(fileName.lastIndexOf(' ') + 1));
						String[] videoFiles = file.list(new SeasonVideoFilenameFilter(videoFilesRelated));
						if (videoFiles.length > 0) {
							tvSeriePathEntity.setVideoFiles(number, new TreeSet<>(Arrays.asList(videoFiles)));
						}
						String[] subtitleFiles = file.list(new SeasonSubtitleFilenameFilter(subtitleFilesRelated));
						if (subtitleFiles.length > 0) {
							tvSeriePathEntity.setSubtitleFiles(number, new TreeSet<>(Arrays.asList(subtitleFiles)));
						}
					}
				}
			}
		}

		while (!toScan.isEmpty()) {
			Entry<File, FsElement> element = toScan.poll();

			files = element.getKey().listFiles();
			for (File file : files) {
				if (!file.isHidden()) {
					progressNotifier.notifyTaskUpdate(getTaskMessage(taskNumber, taskCount, file.getName()), null);
					FsElement fsElement = new FsElement(file.getName(), file.isDirectory());
					element.getValue().addChild(fsElement);

					if (fsElement.getDirectory()) {
						toScan.offer(new SimpleEntry<>(file, fsElement));
					}
				}
			}
		}
	}

	private static String getTaskMessage(int taskNumber, int taskCount, String message) {
		return String.format("(%d/%d) %s", taskNumber, taskCount, message);
	}

	private static class ExtrafanartFilenameFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			return StringUtils.endsWithIgnoreCase(name, "jpg");
		}

	}
	
	private static class SeasonVideoFilenameFilter implements FilenameFilter {

		private static final Set<String> VIDEO_FILE_EXTENSIONS = new HashSet<>(Arrays.asList(new String[] {"webm", "mkv", "flv", "vob", "ogv", "ogg", "drc", 
		                                                                                                   "mng", "avi", "mov", "qt", "wmv", "yuv", "rm", 
		                                                                                                   "rmvb", "asf", "mp4", "m4p", "m4v", "mpg", "mp2", 
		                                                                                                   "mpeg", "mpe", "mpv", "m2v", "svi", "3gp", "3g2",
		                                                                                                   "mxf", "roq", "nsv"}));
		
		private final Set<String> videoFilesRelated;
		
		private SeasonVideoFilenameFilter(Set<String> videoFilesRelated) {
			this.videoFilesRelated = videoFilesRelated;
		}
		
		@Override
		public boolean accept(File dir, String name) {
			boolean result = false;
			
			String ext = name.substring(name.lastIndexOf('.') + 1);
			result = !this.videoFilesRelated.contains(dir.getAbsolutePath()) && VIDEO_FILE_EXTENSIONS.contains(ext);
			
			return result;
		}

	}
	
	private static class SeasonSubtitleFilenameFilter implements FilenameFilter {

		private final Set<String> subtitleFilesRelated;
		
		private SeasonSubtitleFilenameFilter(Set<String> subtitleFilesRelated) {
			this.subtitleFilesRelated = subtitleFilesRelated;
		}
		
		@Override
		public boolean accept(File dir, String name) {
			return !this.subtitleFilesRelated.contains(dir.getAbsolutePath()) && StringUtils.endsWithIgnoreCase(name, "srt");
		}

	}
	
	private TvSerieWorkerTasks() {
	}

}
