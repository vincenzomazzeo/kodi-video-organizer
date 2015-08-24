package it.ninjatech.kvo.tvserie;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class TvSerieScanWorkerTask implements TvSerieWorkerTask<TvSeriePathEntity> {

	private static final Set<String> VIDEO_FILE_EXTENSIONS = new HashSet<>(Arrays.asList(new String[] {"webm", "mkv", "flv", "vob", "ogv", "ogg", "drc", 
	                                                                                                   "mng", "avi", "mov", "qt", "wmv", "yuv", "rm", 
	                                                                                                   "rmvb", "asf", "mp4", "m4p", "m4v", "mpg", "mp2", 
	                                                                                                   "mpeg", "mpe", "mpv", "m2v", "svi", "3gp", "3g2",
	                                                                                                   "mxf", "roq", "nsv"}));
	
	protected TvSerieScanWorkerTask() {}
	
	@Override
	public boolean doTask(TvSeriePathEntity tvSeriePathEntity) throws Exception {
		File root = new File(tvSeriePathEntity.getPath());
		File[] files = root.listFiles(new TvSerieFileFilter());
		
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			if (fileName.equalsIgnoreCase(TvSerieHelper.EXTRAFANART)) {
				tvSeriePathEntity.setExtraFanarts(new TreeSet<>(Arrays.asList(files[i].list(new ExtrafanartFilenameFilter()))));
			}
			else if (StringUtils.startsWithIgnoreCase(fileName, TvSerieHelper.SEASON)) {
				fileName = StringUtils.normalizeSpace(fileName);
				Integer number = Integer.valueOf(fileName.substring(fileName.lastIndexOf(' ') + 1));
				String[] videoFiles = files[i].list(new SeasonVideoFilenameFilter());
				if (videoFiles.length > 0) {
					tvSeriePathEntity.setVideoFiles(number, new TreeSet<>(Arrays.asList(videoFiles)));
				}
				String[] subtitleFiles = files[i].list(new SeasonSubtitleFilenameFilter());
				if (subtitleFiles.length > 0) {
					tvSeriePathEntity.setSubtitleFiles(number, new TreeSet<>(Arrays.asList(subtitleFiles)));
				}
			}
		}
		
		return true;
	}

	private static class TvSerieFileFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			boolean result = false;

			if (pathname.isDirectory()) {
				result = pathname.getName().equalsIgnoreCase(TvSerieHelper.EXTRAFANART) || StringUtils.startsWithIgnoreCase(pathname.getName(), TvSerieHelper.SEASON);
			}

			return result;
		}

	}

	private static class ExtrafanartFilenameFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			return StringUtils.endsWithIgnoreCase(name, "jpg");
		}

	}

	private static class SeasonVideoFilenameFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			boolean result = false;
			
			String ext = name.substring(name.lastIndexOf('.') + 1);
			result = VIDEO_FILE_EXTENSIONS.contains(ext);
			
			return result;
		}

	}
	
	private static class SeasonSubtitleFilenameFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			return StringUtils.endsWithIgnoreCase(name, "srt");
		}

	}
	
}
