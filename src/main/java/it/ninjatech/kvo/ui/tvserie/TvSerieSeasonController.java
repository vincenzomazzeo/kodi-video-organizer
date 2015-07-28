package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.job.TvSerieCacheRemoteImageAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalSeasonImageAsyncJob;
import it.ninjatech.kvo.model.AbstractTvSerieImage;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSerieImageProvider;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.model.TvSerieSeasonImage;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.tvserie.TvSerieImageChoiceDialog.ImageChoiceController;
import it.ninjatech.kvo.util.MemoryUtils;
import it.ninjatech.kvo.util.Utils;
import it.ninjatech.kvo.worker.CachedImageFullWorker;
import it.ninjatech.kvo.worker.ImageFullWorker;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import org.apache.commons.lang3.StringUtils;

import com.alee.laf.list.WebList;

public class TvSerieSeasonController implements ImageChoiceController {

	private static final DataFlavor VIDEO_FILE_DATA_FLAVOR = new DataFlavor(TvSerieSeasonController.class, "VideoFileDND");
	private static final DataFlavor SUBTITLE_FILE_DATA_FLAVOR = new DataFlavor(TvSerieSeasonController.class, "SubtitleFileDND");

	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieSeason season;
	private final TvSerieSeasonListModel videoFileListModel;
	private final TvSerieSeasonListModel subtitleFileListModel;
	private final TvSerieSeasonDialog view;
	private final Map<TvSerieEpisode, String> videoEpisodeMap;
	private final Map<TvSerieEpisode, Map<String, EnhancedLocale>> subtitleEpisodeMap;
	private final TvSerieImageLoaderAsyncJobHandler mainJobHandler;
	private final TvSerieImageLoaderAsyncJobHandler imageChoiceJobHandler;
	private File currentSeasonImage;

	public TvSerieSeasonController(TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season) {
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.season = season;
		this.videoFileListModel = new TvSerieSeasonListModel(TvSerieUtils.getVideoFiles(this.tvSeriePathEntity, this.season.getNumber()));
		this.subtitleFileListModel = new TvSerieSeasonListModel(TvSerieUtils.getSubtitleFiles(this.tvSeriePathEntity, this.season.getNumber()));
		this.view = new TvSerieSeasonDialog(this, this.tvSeriePathEntity, this.season, this.videoFileListModel, this.subtitleFileListModel);
		this.videoEpisodeMap = new HashMap<>();
		this.subtitleEpisodeMap = new HashMap<>();
		this.mainJobHandler = new TvSerieImageLoaderAsyncJobHandler();
		this.imageChoiceJobHandler = new TvSerieImageLoaderAsyncJobHandler();
		this.currentSeasonImage = new File(this.tvSeriePathEntity.getPath(), this.season.getPosterFilename());
	}

	@Override
	public void notifyClosing(String id) {
		this.imageChoiceJobHandler.cancelAll();
	}

	@Override
	public void notifyImageLeftClick(String id, AbstractTvSerieImage image) {
		this.currentSeasonImage = new File(Utils.getCacheDirectory(), image.getId());
		TvSerieCacheRemoteImageAsyncJob job = new TvSerieCacheRemoteImageAsyncJob(image.getId(), image.getProvider(), image.getPath(), this.view.getSeasonImageSize());
		this.mainJobHandler.handle(job, this.view);
	}

	@Override
	public void notifyImageRightClick(String id, AbstractTvSerieImage image) {
		CachedImageFullWorker worker = new CachedImageFullWorker(image.getId());
		UIUtils.showFullImage(worker, "Loading season image", TvSerieUtils.getSeasonName(this.season));
	}

	public TvSerieSeasonDialog getView() {
		return this.view;
	}

	public void start() {
		TvSerieLocalSeasonImageAsyncJob job = new TvSerieLocalSeasonImageAsyncJob(this.tvSeriePathEntity, this.season, this.view.getSeasonImageSize());
		this.mainJobHandler.handle(job, this.view);

		for (TvSerieEpisode episode : this.season.getEpisodes()) {
			if (StringUtils.isNotBlank(episode.getArtwork())) {
				TvSerieCacheRemoteImageAsyncJob episodeImageJob = new TvSerieCacheRemoteImageAsyncJob(episode.getId(), TvSerieImageProvider.TheTvDb, episode.getArtwork(), this.view.getEpisodeImageSize());
				this.mainJobHandler.handle(episodeImageJob, this.view);
			}
		}
	}

	protected void notifyConfirm() {
		this.mainJobHandler.cancelAll();
		
		this.view.setVisible(false);
		this.view.destroy();
		this.view.dispose();

		// TODO
		
		destroy();
	}

	protected void notifyCancel() {
		this.mainJobHandler.cancelAll();
		
		this.view.setVisible(false);
		this.view.destroy();
		this.view.dispose();
		
		destroy();
	}
	
	protected void notifySeasonLeftClick() {
		MemoryUtils.printMemory("Before season choice");
		Set<TvSerieSeasonImage> images = this.season.getImages();
		Dimension imageSize = Dimensions.getTvSerieSeasonChooserSize();
		TvSerieImageChoiceDialog<TvSerieSeasonImage> dialog = new TvSerieImageChoiceDialog<>("", this, TvSerieUtils.getSeasonName(this.season), images, imageSize);
		for (TvSerieSeasonImage image : images) {
			TvSerieCacheRemoteImageAsyncJob job = new TvSerieCacheRemoteImageAsyncJob(image.getId(), image.getProvider(), image.getPath(), imageSize);
			this.imageChoiceJobHandler.handle(job, dialog);
		}
		
		dialog.setVisible(true);
		MemoryUtils.printMemory("After season choice");
	}

	protected void notifySeasonRightClick() {
		ImageFullWorker worker = new ImageFullWorker(this.currentSeasonImage.getParent(), this.currentSeasonImage.getName());
		UIUtils.showFullImage(worker, "Loading season image", TvSerieUtils.getSeasonName(this.season));
	}

	protected void notifyVideoFileRightClick(TvSerieEpisode episode, TvSerieEpisodeTile tile) {
		String filename = this.videoEpisodeMap.remove(episode);
		this.videoFileListModel.addElement(filename);

		tile.clearVideoFile();
	}

	protected void notifyLanguageRightClick(TvSerieEpisode episode, TvSerieEpisodeTile tile, String filename) {
		this.subtitleEpisodeMap.get(episode).remove(filename);
		this.subtitleFileListModel.addElement(filename);

		tile.removeLanguage(filename);
	}

	protected EpisodeTileDropTransferHandler makeEpisodeTileDropTransferHandler(TvSerieEpisode episode, TvSerieEpisodeTile tile) {
		return new EpisodeTileDropTransferHandler(this, episode, tile);
	}

	protected VideoSubtitleTransferHandler makeVideoDragTransferHandler() {
		return new VideoSubtitleTransferHandler(VIDEO_FILE_DATA_FLAVOR);
	}

	protected VideoSubtitleTransferHandler makeSubtitleDragTransferHandler() {
		return new VideoSubtitleTransferHandler(SUBTITLE_FILE_DATA_FLAVOR);
	}
	
	private void destroy() {
		this.videoEpisodeMap.clear();
		this.subtitleEpisodeMap.clear();
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
			if (action == MOVE) {
				TvSerieSeasonListModel model = (TvSerieSeasonListModel)((WebList)source).getModel();
				try {
					model.removeElement((String)data.getTransferData(this.dataFlavor));
				}
				catch (UnsupportedFlavorException | IOException e) {
				}
			}
		}

	}

	protected static class EpisodeTileDropTransferHandler extends TransferHandler {

		private static final long serialVersionUID = 9063205345756195612L;

		private final TvSerieSeasonController controller;
		private final TvSerieEpisode episode;
		private final TvSerieEpisodeTile tile;

		private EpisodeTileDropTransferHandler(TvSerieSeasonController controller, TvSerieEpisode episode, TvSerieEpisodeTile tile) {
			this.controller = controller;
			this.episode = episode;
			this.tile = tile;
		}

		@Override
		public boolean importData(TransferSupport support) {
			boolean result = true;

			try {
				String filename = (String)support.getTransferable().getTransferData(support.getDataFlavors()[0]);
				if (support.getDataFlavors()[0].getHumanPresentableName().equals(VIDEO_FILE_DATA_FLAVOR.getHumanPresentableName())) {
					this.controller.videoEpisodeMap.put(this.episode, filename);
					this.tile.setVideoFile(filename, true);
				}
				else if (support.getDataFlavors()[0].getHumanPresentableName().equals(SUBTITLE_FILE_DATA_FLAVOR.getHumanPresentableName())) {
					TvSerieEpisodeSubtitleDialog dialog = TvSerieEpisodeSubtitleDialog.make(this.episode, filename);
					result = dialog.isConfirmed();
					if (result) {
						EnhancedLocale language = dialog.getLanguage();
						this.tile.addLanguage(language, filename, true);
						Map<String, EnhancedLocale> languages = this.controller.subtitleEpisodeMap.get(this.episode);
						if (languages == null) {
							languages = new HashMap<>();
							this.controller.subtitleEpisodeMap.put(this.episode, languages);
						}
						languages.put(filename, language);
					}
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
				result = !this.controller.videoEpisodeMap.containsKey(this.episode);
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
