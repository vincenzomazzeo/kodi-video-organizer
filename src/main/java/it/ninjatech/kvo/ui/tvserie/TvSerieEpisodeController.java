package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.job.CacheRemoteImageAsyncJob;
import it.ninjatech.kvo.async.job.PersonAsyncJob;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.AbstractImageSlider.ImageSliderListener;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.TvSerieUtils;
import it.ninjatech.kvo.util.Utils;
import it.ninjatech.kvo.worker.CachedImageFullWorker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;

import org.apache.commons.lang3.StringUtils;

public class TvSerieEpisodeController implements ImageSliderListener {

	protected static final String ARTWORK_ID = "Artwork";
	protected static final String DIRECTORS_SLIDER_ID = "Directors";
	protected static final String GUEST_STARS_SLIDER_ID = "GuestStars";
	protected static final String WRITERS_SLIDER_ID = "Writers";
	
	private final FileListModel fileListModel;
	private final TvSerieEpisodeView view;
	private final TvSerieImageLoaderAsyncJobHandler mainJobHandler;
	private TvSerieEpisode tvSerieEpisode;

	public TvSerieEpisodeController() {
		this.fileListModel = new FileListModel();
		this.view = new TvSerieEpisodeView(this, this.fileListModel);
		this.mainJobHandler = new TvSerieImageLoaderAsyncJobHandler();
	}
	
	@Override
	public void notifyImageSliderLeftClick(String id, Object data) {
	}

	@Override
	public void notifyImageSliderRightClick(String id, Object data) {
		UIUtils.showPersonFullImage((String)data);
	}

	public TvSerieEpisodeView getView() {
		return this.view;
	}
	
	public void showTvSerieEpisode(TvSerieEpisode tvSerieEpisode, String videoFile, Map<String, EnhancedLocale> subtitleFiles) {
		this.mainJobHandler.cancelAll();
		this.view.destroy();
		
		this.tvSerieEpisode = tvSerieEpisode;
		
		this.view.fill(tvSerieEpisode);
		
		if (StringUtils.isNotBlank(tvSerieEpisode.getArtwork())) {
			CacheRemoteImageAsyncJob job = new CacheRemoteImageAsyncJob(tvSerieEpisode.getId(), ImageProvider.TheTvDb, tvSerieEpisode.getArtwork(), this.view.getArtworkSize());
			this.mainJobHandler.handle(job, this.view, ARTWORK_ID);
		}
		
		// Files
		Map<String, EnhancedLocale> mySubtitleFiles = new HashMap<>();
		for (String subtitleFile : tvSerieEpisode.getSubtitleFilenames()) {
			mySubtitleFiles.put(subtitleFile, Utils.getLanguageFromSubtitleFile(subtitleFile));
		}
		if (subtitleFiles != null) {
			mySubtitleFiles.putAll(subtitleFiles);
		}
		this.fileListModel.setData(StringUtils.isBlank(tvSerieEpisode.getFilename()) ? videoFile : tvSerieEpisode.getFilename(), mySubtitleFiles);
		
		// Directors
		for (String director : TvSerieUtils.getEpisodeDirectors(tvSerieEpisode)) {
			PersonAsyncJob job = new PersonAsyncJob(director, director, this.view.getDirectorSize());
			this.mainJobHandler.handle(job, this.view, DIRECTORS_SLIDER_ID);
		}
		
		// Writers
		for (String writer : TvSerieUtils.getEpisodeWriters(tvSerieEpisode)) {
			PersonAsyncJob job = new PersonAsyncJob(writer, writer, this.view.getWriterSize());
			this.mainJobHandler.handle(job, this.view, WRITERS_SLIDER_ID);
		}
		
		// Guest Stars
		for (String guestStar : TvSerieUtils.getEpisodeGuestStars(tvSerieEpisode)) {
			PersonAsyncJob job = new PersonAsyncJob(guestStar, guestStar, this.view.getGuestStarSize());
			this.mainJobHandler.handle(job, this.view, GUEST_STARS_SLIDER_ID);
		}
	}
	
	public void destroy() {
		this.mainJobHandler.cancelAll();
		this.view.destroy();
	}
	
	protected void notifyArtworkRightClick() {
		CachedImageFullWorker worker = new CachedImageFullWorker(this.tvSerieEpisode.getId());
		UIUtils.showFullImage(worker, Labels.LOADING_EPISODE_IMAGE, this.tvSerieEpisode.getName());
	}
	
	protected void notifyVideoFileRemoved() {
		this.fileListModel.removeVideoFile();
	}
	
	protected void notifySubtitleFileRemove(String subtitleFile) {
		this.fileListModel.removeSubtitleFile(subtitleFile);
	}
	
	protected void notifyVideoFileAdded(String videoFile) {
		this.fileListModel.addVideoFile(videoFile);
	}
	
	protected void notifySubtitleFileAdded(String subtitleFile, EnhancedLocale language) {
		this.fileListModel.addSubtitleFile(subtitleFile, language);
	}
	
	protected static class FileListModel extends AbstractListModel<FileData> {
		
		private static final long serialVersionUID = -3626644786540392180L;
		
		private final List<FileData> files;
		
		private FileListModel() {
			this.files = new ArrayList<>();
		}
		
		@Override
		public int getSize() {
			return this.files.size();
		}

		@Override
		public FileData getElementAt(int index) {
			return this.files.get(index);
		}
		
		private void setData(String videoFile, Map<String, EnhancedLocale> subtitleFiles) {
			this.files.clear();
			if (videoFile != null) {
				this.files.add(new FileData(true, videoFile, null));
			}
			for (String file : subtitleFiles.keySet()) {
				this.files.add(new FileData(false, file, subtitleFiles.get(file)));
			}
			Collections.sort(this.files);
			fireContentsChanged(this, 0, getSize());
		}
		
		private void removeVideoFile() {
			this.files.remove(0);
			fireIntervalRemoved(this, 0, 0);
		}
		
		private void removeSubtitleFile(String subtitleFile) {
			for (int i = 0, n = this.files.size(); i < n; i++) {
				if (this.files.get(i).getFile().equals(subtitleFile)) {
					this.files.remove(i);
					fireIntervalRemoved(this, i, i);
					break;
				}
			}
		}
		
		private void addVideoFile(String videoFile) {
			this.files.add(0, new FileData(true, videoFile, null));
			fireIntervalAdded(this, 0, 0);
		}
		
		private void addSubtitleFile(String subtitleFile, EnhancedLocale language) {
			this.files.add(new FileData(false, subtitleFile, language));
			Collections.sort(this.files);
			fireIntervalAdded(this, 0, this.files.size());
		}
		
	}
	
	protected static class FileData implements Comparable<FileData> {
		
		private final boolean video;
		private final String file;
		private final EnhancedLocale language;
		
		private FileData(boolean video, String file, EnhancedLocale language) {
			super();
			this.video = video;
			this.file = file;
			this.language = language;
		}

		@Override
		public int compareTo(FileData other) {
			int result = 0;

			if (this.video) {
				result = -1;
			}
			else if (other.video) {
				result = 1;
			}
			else if (this.language.getLanguage().equals(other.language.getLanguage())) {
				result = this.file.compareTo(other.file);
			}
			else {
				result = this.language.getLanguage().compareTo(other.language.getLanguage());
			}
			
			return result;
		}

		public boolean isVideo() {
			return this.video;
		}

		public String getFile() {
			return this.file;
		}

		public EnhancedLocale getLanguage() {
			return this.language;
		}
		
	}
	
}
