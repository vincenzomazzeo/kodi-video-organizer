package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.job.CacheRemoteImageAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalSeasonImageAsyncJob;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.AbstractTvSerieImage;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.tvserie.model.TvSerieSeasonImage;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.MessageDialog;
import it.ninjatech.kvo.ui.tvserie.TvSerieImageChoiceDialog.ImageChoiceController;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.MemoryUtils;
import it.ninjatech.kvo.util.Utils;
import it.ninjatech.kvo.worker.CachedImageFullWorker;
import it.ninjatech.kvo.worker.ImageFullWorker;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import org.apache.commons.lang3.StringUtils;

import com.alee.laf.list.WebList;

public class TvSerieSeasonController implements ImageChoiceController {

	private static final DataFlavor VIDEO_FILE_DATA_FLAVOR = new DataFlavor(TvSerieSeasonController.class, "VideoFileDND");
	private static final DataFlavor SUBTITLE_FILE_DATA_FLAVOR = new DataFlavor(TvSerieSeasonController.class, "SubtitleFileDND");

	private final TvSerieSeason season;
	private final TvSerieSeasonListModel videoFileListModel;
	private final TvSerieSeasonListModel subtitleFileListModel;
	private final TvSerieEpisodeController episodeController;
	private final TvSerieSeasonDialog view;
	private final Map<TvSerieEpisode, String> videoEpisodeMap;
	private final Map<TvSerieEpisode, Map<String, EnhancedLocale>> subtitleEpisodeMap;
	private final TvSerieImageLoaderAsyncJobHandler mainJobHandler;
	private final TvSerieImageLoaderAsyncJobHandler imageChoiceJobHandler;
	private TvSerieEpisode selectedEpisodeView;
	private File currentSeasonImage;
	private boolean confirmed;

	public TvSerieSeasonController(TvSerieSeason season) {
		this.season = season;
		this.videoFileListModel = new TvSerieSeasonListModel(TvSerieHelper.getVideoFilesNotReferenced(this.season.getTvSerie().getTvSeriePathEntity(), this.season.getNumber()));
		this.subtitleFileListModel = new TvSerieSeasonListModel(TvSerieHelper.getSubtitleFilesNotReferenced(this.season.getTvSerie().getTvSeriePathEntity(), this.season.getNumber()));
		this.episodeController = new TvSerieEpisodeController();
		this.view = TvSerieSeasonDialog.getInstance(this, this.season, this.videoFileListModel, this.subtitleFileListModel, Desktop.isDesktopSupported(), Desktop.isDesktopSupported() && TvSerieHelper.existsLocalSeason(season));
		this.videoEpisodeMap = new HashMap<>();
		this.subtitleEpisodeMap = new HashMap<>();
		this.mainJobHandler = new TvSerieImageLoaderAsyncJobHandler();
		this.imageChoiceJobHandler = new TvSerieImageLoaderAsyncJobHandler();
		this.currentSeasonImage = new File(this.season.getTvSerie().getTvSeriePathEntity().getPath(), TvSerieHelper.getSeasonPosterFilename(this.season));
		this.confirmed = false;
		
		for (TvSerieEpisode episode : this.season.getEpisodes()) {
			if (episode.getFilename() != null) {
				this.view.setEpisodeVideoFile(episode, TvSerieHelper.getEpisodeFileName(episode), false);
			}
			for (String subtitleFilename : TvSerieHelper.getEpisodeSubtitleFileNames(episode)) {
				EnhancedLocale language = Utils.getLanguageFromSubtitleFile(subtitleFilename);
				this.view.addEpisodeSubtitle(episode, language, subtitleFilename, false);
			}
		}
	}

	@Override
	public void notifyImageChoiceClosing() {
		this.imageChoiceJobHandler.cancelAll();
	}

	@Override
	public void notifyImageChoiceLeftClick(AbstractTvSerieImage image) {
		this.currentSeasonImage = new File(Utils.getCacheDirectory(), image.getId());
		CacheRemoteImageAsyncJob job = new CacheRemoteImageAsyncJob(image.getId(), image.getProvider(), image.getPath(), this.view.getSeasonImageSize(), "jpg");
		this.mainJobHandler.handle(job, this.view);
	}

	@Override
	public void notifyImageChoiceRightClick(AbstractTvSerieImage image) {
		CachedImageFullWorker worker = new CachedImageFullWorker(image.getId());
		UIUtils.showFullImage(worker, Labels.LOADING_SEASON_IMAGE, Labels.getTvSerieSeason(this.season));
	}

	public TvSerieSeasonDialog getView() {
		return this.view;
	}

	public void start() {
		TvSerieLocalSeasonImageAsyncJob job = new TvSerieLocalSeasonImageAsyncJob(this.season, this.view.getSeasonImageSize());
		this.mainJobHandler.handle(job, this.view);

		for (TvSerieEpisode episode : this.season.getEpisodes()) {
			if (StringUtils.isNotBlank(episode.getArtwork())) {
				CacheRemoteImageAsyncJob episodeImageJob = new CacheRemoteImageAsyncJob(episode.getId(), ImageProvider.TheTvDb, episode.getArtwork(), this.view.getEpisodeImageSize(), "jpg");
				this.mainJobHandler.handle(episodeImageJob, this.view);
			}
		}
	}

	public boolean isConfirmed() {
		return this.confirmed;
	}

	protected void notifyConfirm() {
	    this.confirmed = TvSerieManager.getInstance().handleSeason(this.season, this.videoEpisodeMap, this.subtitleEpisodeMap, this.currentSeasonImage);
	    if (!this.confirmed) {
	        MessageDialog.getInstance(Labels.ERROR, Labels.FAILED_TO_UPDATE_SEASON, MessageDialog.Type.Error).setVisible(true);
	    }

		destroy();
	}

	protected void notifyCancel() {
		this.confirmed = false;
		
		destroy();
	}

	protected void notifyTvSerieTitleLeftClick() {
		try {
			File path = new File(this.season.getTvSerie().getTvSeriePathEntity().getPath());
			Desktop.getDesktop().open(path);
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
	}

	protected void notifyTvSerieSeasonTitleLeftClick() {
		try {
			File path = TvSerieHelper.getLocalSeasonPath(this.season);
			Desktop.getDesktop().open(path);
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
	}

	protected void notifySeasonLeftClick() {
		MemoryUtils.printMemory("Before season choice");
		Set<TvSerieSeasonImage> images = this.season.getImages();
		Dimension imageSize = Dimensions.getTvSerieSeasonChooserSize();
		TvSerieImageChoiceDialog dialog = TvSerieImageChoiceDialog.getInstance(this, Labels.getTvSerieSeason(this.season), images, imageSize);
		for (TvSerieSeasonImage image : images) {
			CacheRemoteImageAsyncJob job = new CacheRemoteImageAsyncJob(image.getId(), image.getProvider(), image.getPath(), imageSize, "jpg");
			this.imageChoiceJobHandler.handle(job, dialog);
		}

		dialog.setVisible(true);
		MemoryUtils.printMemory("After season choice");
	}

	protected void notifySeasonRightClick() {
		ImageFullWorker worker = new ImageFullWorker(this.currentSeasonImage.getParent(), this.currentSeasonImage.getName());
		UIUtils.showFullImage(worker, Labels.LOADING_SEASON_IMAGE, Labels.getTvSerieSeason(this.season));
	}

	protected void notifyVideoFileRightClick(TvSerieEpisode episode, TvSerieEpisodeTile tile) {
		String filename = this.videoEpisodeMap.remove(episode);
		this.videoFileListModel.addElement(filename);

		if (this.selectedEpisodeView != null && this.selectedEpisodeView.equals(episode)) {
			this.episodeController.notifyVideoFileRemoved();
		}
		
		tile.clearVideoFile();
	}

	protected void notifyVideoFileRightClick(int index) {
	    String filename = this.videoFileListModel.getElementAt(index);
	    
	    for (TvSerieEpisode episode : this.season.getEpisodes()) {
	        if (episode.getFilename() == null && !this.videoEpisodeMap.containsKey(episode)) {
	            handleVideo(episode, filename);
	            break;
	        }
	    }
	}
	
	protected void notifySubtitleFileRightClick(int index) {
	    String filename = this.subtitleFileListModel.getElementAt(index);
	    
	    TvSerieEpisode selectedEpisode = null;
	    int selectedEpisodeSubtitleCount = Integer.MAX_VALUE;
	    for (TvSerieEpisode episode : this.season.getEpisodes()) {
	        int episodeSubtitleCount = episode.getSubtitleFilenames().size();
	        Map<String, EnhancedLocale> languages = this.subtitleEpisodeMap.get(episode);
            if (languages != null) {
                episodeSubtitleCount += languages.size();
            }
            if (episodeSubtitleCount < selectedEpisodeSubtitleCount) {
                selectedEpisode = episode;
                selectedEpisodeSubtitleCount = episodeSubtitleCount;
            }
	    }
	    
	    handleSubtitle(selectedEpisode, filename);
	}
	
	protected void notifyLanguageRightClick(TvSerieEpisode episode, TvSerieEpisodeTile tile, String filename) {
		this.subtitleEpisodeMap.get(episode).remove(filename);
		this.subtitleFileListModel.addElement(filename);

		if (this.selectedEpisodeView != null && this.selectedEpisodeView.equals(episode)) {
			this.episodeController.notifySubtitleFileRemove(filename);
		}
		
		tile.removeSubtitle(filename);
	}

	protected void notifyEpisodeClick(TvSerieEpisode episode) {
		if (this.selectedEpisodeView == null || !this.selectedEpisodeView.equals(episode)) {
			MemoryUtils.printMemory("Before episode view");
			this.selectedEpisodeView = episode;
			this.episodeController.showTvSerieEpisode(episode, this.videoEpisodeMap.get(episode), this.subtitleEpisodeMap.get(episode));
			this.view.setEpisodeView(this.episodeController.getView());
			MemoryUtils.printMemory("After episode view");
		}
	}

	protected EpisodeTileDropTransferHandler makeEpisodeTileDropTransferHandler(TvSerieEpisode episode) {
		return new EpisodeTileDropTransferHandler(this, episode);
	}

	protected VideoSubtitleTransferHandler makeVideoDragTransferHandler() {
		return new VideoSubtitleTransferHandler(VIDEO_FILE_DATA_FLAVOR);
	}

	protected VideoSubtitleTransferHandler makeSubtitleDragTransferHandler() {
		return new VideoSubtitleTransferHandler(SUBTITLE_FILE_DATA_FLAVOR);
	}

	private void destroy() {
		this.mainJobHandler.cancelAll();
		this.episodeController.destroy();
		this.videoEpisodeMap.clear();
		this.subtitleEpisodeMap.clear();

		this.view.setVisible(false);
		this.view.release();
		this.view.dispose();
	}

	private void handleVideo(TvSerieEpisode episode, String filename) {
	    this.videoEpisodeMap.put(episode, filename);
        this.view.setEpisodeVideoFile(episode, filename, true);
        this.videoFileListModel.removeElement(filename);
        
        if (this.selectedEpisodeView != null && this.selectedEpisodeView.equals(episode)) {
            this.episodeController.notifyVideoFileAdded(filename);
        }
	}
	
	private boolean handleSubtitle(TvSerieEpisode episode, String filename) {
	    boolean result = false;
	    
	    EnhancedLocale language = null;
	    
	    if (this.view.isSubtitleLanguageLocked()) {
	        language = this.view.getSubtitleLanguage();
	        result = true;
	    }
	    else {
	        TvSerieEpisodeSubtitleDialog dialog = TvSerieEpisodeSubtitleDialog.getInstance(episode, filename);
	        dialog.setVisible(true);
	        result = dialog.isConfirmed();
	        if (result) {
	            language = dialog.getLanguage();
	        }
	        dialog.release();
	    }
	    
        if (result) {
            this.view.addEpisodeSubtitle(episode, language, filename, true);
            Map<String, EnhancedLocale> languages = this.subtitleEpisodeMap.get(episode);
            if (languages == null) {
                languages = new HashMap<>();
                this.subtitleEpisodeMap.put(episode, languages);
            }
            languages.put(filename, language);
            this.subtitleFileListModel.removeElement(filename);
            
            if (this.selectedEpisodeView != null && this.selectedEpisodeView.equals(episode)) {
                this.episodeController.notifySubtitleFileAdded(filename, language);
            }
        }
        
        return result;
	}
	
	protected class TvSerieSeasonListModel extends AbstractListModel<String> {

		private static final long serialVersionUID = 1576352902620800824L;

		private final List<String> data;

		private TvSerieSeasonListModel(Set<String> data) {
			this.data = new ArrayList<>(data);
		}

		@Override
		public int getSize() {
			return this.data.size();
		}

		@Override
		public String getElementAt(int index) {
			return this.data.get(index);
		}

		private void addElement(String element) {
			this.data.add(element);
			Collections.sort(this.data);
			int index = this.data.indexOf(element);
			fireIntervalAdded(this, index, index);
		}

		private void removeElement(String element) {
			int index = this.data.indexOf(element);
			this.data.remove(index);
			fireIntervalRemoved(this, index, index);
		}

	}

	protected static class VideoSubtitleTransferHandler extends TransferHandler {

		private static final long serialVersionUID = 3329599794478692344L;

		private final DataFlavor dataFlavor;

		private VideoSubtitleTransferHandler(DataFlavor dataFlavor) {
			this.dataFlavor = dataFlavor;
		}

		@Override
		public int getSourceActions(JComponent component) {
			return MOVE;
		}

		@Override
		protected Transferable createTransferable(JComponent component) {
			Transferable result = null;

			WebList source = (WebList)component;
			result = new VideoSubtitleTransferable((String)source.getSelectedValue(), this.dataFlavor);

			return result;
		}

		@Override
		protected void exportDone(JComponent source, Transferable data, int action) {
		}

	}

	protected static class EpisodeTileDropTransferHandler extends TransferHandler {

		private static final long serialVersionUID = 9063205345756195612L;

		private final TvSerieSeasonController controller;
		private final TvSerieEpisode episode;

		private EpisodeTileDropTransferHandler(TvSerieSeasonController controller, TvSerieEpisode episode) {
			this.controller = controller;
			this.episode = episode;
		}

		@Override
		public boolean importData(TransferSupport support) {
			boolean result = true;

			try {
				String filename = (String)support.getTransferable().getTransferData(support.getDataFlavors()[0]);
				if (support.getDataFlavors()[0].getHumanPresentableName().equals(VIDEO_FILE_DATA_FLAVOR.getHumanPresentableName())) {
				    this.controller.handleVideo(this.episode, filename);
				}
				else if (support.getDataFlavors()[0].getHumanPresentableName().equals(SUBTITLE_FILE_DATA_FLAVOR.getHumanPresentableName())) {
				    this.controller.handleSubtitle(this.episode, filename);
				}
			}
			catch (UnsupportedFlavorException | IOException e) {
			}

			return result;
		}

		@Override
		public boolean canImport(TransferSupport support) {
			boolean result = false;

			if (support.getDataFlavors()[0].getHumanPresentableName().equals(VIDEO_FILE_DATA_FLAVOR.getHumanPresentableName())) {
				result = this.episode.getFilename() == null && !this.controller.videoEpisodeMap.containsKey(this.episode);
			}
			else if (support.getDataFlavors()[0].getHumanPresentableName().equals(SUBTITLE_FILE_DATA_FLAVOR.getHumanPresentableName())) {
				result = true;
			}

			return result;
		}

	}

	protected static class VideoSubtitleTransferable implements Transferable {

		private final String value;
		private final DataFlavor[] dataFlavor;

		private VideoSubtitleTransferable(String value, DataFlavor dataFlavor) {
			this.value = value;
			this.dataFlavor = new DataFlavor[] { dataFlavor };
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			return this.dataFlavor;
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
			return this.dataFlavor[0].equals(dataFlavor);
		}

		@Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			return this.value;
		}

	}

}
