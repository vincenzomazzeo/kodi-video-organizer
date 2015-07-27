package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieLocalSeasonImageAsyncJob;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import com.alee.laf.list.WebList;

public class TvSerieSeasonController implements AsyncJobListener {

	private static final DataFlavor VIDEO_FILE_DATA_FLAVOR = new DataFlavor(TvSerieSeasonController.class, "VideoFileDND");
	private static final DataFlavor SUBTITLE_FILE_DATA_FLAVOR = new DataFlavor(TvSerieSeasonController.class, "SubtitleFileDND");
	
	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieSeason season;
	private final TvSerieSeasonListModel videoFileListModel;
	private final TvSerieSeasonListModel subtitleFileListModel;
	private final TvSerieSeasonDialog view;
	private final Map<TvSerieEpisode, String> videoEpisodeMap;
	private final Map<TvSerieEpisode, Map<EnhancedLocale, Set<String>>> subtitleEpisodeMap;
	
	public TvSerieSeasonController(TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season) {
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.season = season;
		this.videoFileListModel = new TvSerieSeasonListModel(TvSerieUtils.getVideoFiles(this.tvSeriePathEntity, this.season.getNumber()));
		this.subtitleFileListModel = new TvSerieSeasonListModel(TvSerieUtils.getSubtitleFiles(this.tvSeriePathEntity, this.season.getNumber()));
		this.view = new TvSerieSeasonDialog(this, this.tvSeriePathEntity, this.season, this.videoFileListModel, this.subtitleFileListModel);
		this.videoEpisodeMap = new HashMap<>();
		this.subtitleEpisodeMap = new HashMap<>();
		
		System.out.println(VIDEO_FILE_DATA_FLAVOR.equals(SUBTITLE_FILE_DATA_FLAVOR));
	}
	
	@Override
	public void notify(String id, AsyncJob job) {
		if (job.getException() != null) {
			UI.get().notifyException(job.getException());
		}
		else {
			if (job.getClass().equals(TvSerieLocalSeasonImageAsyncJob.class)) {
				this.view.setSeasonImage(((TvSerieLocalSeasonImageAsyncJob)job).getImage());
			}
		}
	}
	
	public TvSerieSeasonDialog getView() {
		return this.view;
	}
	
	public void start() {
		TvSerieLocalSeasonImageAsyncJob job = new TvSerieLocalSeasonImageAsyncJob(this.tvSeriePathEntity, this.season, this.view.getSeasonImageSize());
		AsyncManager.getInstance().submit(this.season.getId(), job, this);
	}
	
	protected void notifyClosing() {
		
	}
	
	protected void notifyVideoFileRightClick(TvSerieEpisode episode, TvSerieEpisodeTile tile) {
		String filename = this.videoEpisodeMap.remove(episode);
		this.videoFileListModel.addElement(filename);
		
		tile.clearVideoFile();
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
					TvSerieEpisodeSubtitleDialog dialog = new TvSerieEpisodeSubtitleDialog();
					dialog.setVisible(true);
					result = dialog.isConfirmed();
					if (result) {
						EnhancedLocale language = dialog.getLanguage();
						this.tile.addLanguage(language, filename, true);
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
			this.dataFlavor = new DataFlavor[] {dataFlavor};
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
