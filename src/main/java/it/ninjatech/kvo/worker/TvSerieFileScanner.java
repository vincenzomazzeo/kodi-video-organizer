package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.TvSerieUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

public class TvSerieFileScanner extends AbstractWorker<Void> {

	private static final Set<String> VIDEO_FILE_EXTENSIONS = new HashSet<>(Arrays.asList(new String[] {"webm", "mkv", "flv", "vob", "ogv", "ogg", "drc", 
	                                                                                                   "mng", "avi", "mov", "qt", "wmv", "yuv", "rm", 
	                                                                                                   "rmvb", "asf", "mp4", "m4p", "m4v", "mpg", "mp2", 
	                                                                                                   "mpeg", "mpe", "mpv", "m2v", "svi", "3gp", "3g2",
	                                                                                                   "mxf", "roq", "nsv"}));
	
	private final TvSerie tvSerie;
	private final File[] files;

	public TvSerieFileScanner(TvSerie tvSerie) {
		this.tvSerie = tvSerie;
		
		File root = new File(this.tvSerie.getTvSeriePathEntity().getPath());
		this.files = root.listFiles(new TvSerieFileFilter());
	}

	@Override
	public Void work() throws Exception {
		notifyInit("", this.files.length);

		for (int i = 0; i < this.files.length; i++) {
			String fileName = this.files[i].getName();
			if (fileName.equalsIgnoreCase(TvSerieUtils.EXTRAFANART)) {
				notifyUpdate(Labels.SCANNING_EXTRAFANARTS, null);
				this.tvSerie.getTvSeriePathEntity().setExtraFanarts(new TreeSet<>(Arrays.asList(this.files[i].list(new ExtrafanartFilenameFilter()))));
			}
			else if (StringUtils.startsWithIgnoreCase(fileName, TvSerieUtils.SEASON)) {
				notifyUpdate(Labels.getScanning(fileName), null);
				fileName = StringUtils.normalizeSpace(fileName);
				Integer number = Integer.valueOf(fileName.substring(fileName.lastIndexOf(' ') + 1));
				String[] videoFiles = this.files[i].list(new SeasonVideoFilenameFilter());
				if (videoFiles.length > 0) {
					this.tvSerie.getTvSeriePathEntity().setVideoFiles(number, new TreeSet<>(Arrays.asList(videoFiles)));
				}
				String[] subtitleFiles = this.files[i].list(new SeasonSubtitleFilenameFilter());
				if (subtitleFiles.length > 0) {
					this.tvSerie.getTvSeriePathEntity().setSubtitleFiles(number, new TreeSet<>(Arrays.asList(subtitleFiles)));
				}
			}
			notifyUpdate(null, i + 1);
		}

		return null;
	}
	
	public int getFileCount() {
		return this.files.length;
	}

	private static class TvSerieFileFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			boolean result = false;

			if (pathname.isDirectory()) {
				result = pathname.getName().equalsIgnoreCase(TvSerieUtils.EXTRAFANART) || StringUtils.startsWithIgnoreCase(pathname.getName(), TvSerieUtils.SEASON);
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
