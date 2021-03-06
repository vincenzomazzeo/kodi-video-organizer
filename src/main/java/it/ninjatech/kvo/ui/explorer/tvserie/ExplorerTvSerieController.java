package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieTileImagesAsyncJob;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.explorer.ExplorerController;
import it.ninjatech.kvo.ui.wall.WallController;
import it.ninjatech.kvo.util.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExplorerTvSerieController implements AsyncJobListener {

    private final ExplorerController explorerController;
    private final WallController wallController;
	private final ExplorerTvSerieModel model;
	private final ExplorerTvSerieView view;
	private final Map<String, TileStatus> tiles;

	public ExplorerTvSerieController(ExplorerController explorerController, WallController wallController) {
	    this.explorerController = explorerController;
	    this.wallController = wallController;
		this.model = new ExplorerTvSerieModel();
		this.view = new ExplorerTvSerieView(this, this.model);
		this.tiles = new HashMap<>();
	}

	@Override
	public void notify(String id, AsyncJob job) {
	    Logger.log("-> notify %s\n", id);
		TileStatus tileStatus = this.tiles.get(id);
		if (tileStatus != null) {
			tileStatus.alreadyLoaded = true;
			if (job.getException() != null) {
				UI.get().notifyException(job.getException());
			}
			else {
				tileStatus.tile.setImages(((TvSerieTileImagesAsyncJob)job).getFanart(), ((TvSerieTileImagesAsyncJob)job).getPoster());
			}
		}
	}

	public ExplorerTvSerieView getView() {
		return this.view;
	}

	public void addTile(TvSeriePathEntity tvSeriePathEntity) {
		this.model.addTile(tvSeriePathEntity);
	}
	
	public void addTiles(Set<TvSeriePathEntity> tvSeriePathEntities) {
        this.model.addTiles(tvSeriePathEntities);
    }
	
	public void removeTile(TvSeriePathEntity tvSeriePathEntity) {
	    this.model.removeTile(tvSeriePathEntity);
	}
	
	public void removeTiles(Set<TvSeriePathEntity> tvSeriePathEntities) {
	    this.model.removeTiles(tvSeriePathEntities);
	}
	
	public void setVisible(TvSeriePathEntity tvSeriePathEntity) {
	    this.view.scrollTo(tvSeriePathEntity);
	}

	protected void notifyLeftClick(TvSeriePathEntity tvSeriePathEntity) {
	    this.explorerController.notifyTvSerieClick(tvSeriePathEntity);
	    this.wallController.showTvSerie(tvSeriePathEntity);
	}
	
	protected void selectInRoots(TvSeriePathEntity tvSeriePathEntity) {
	    this.explorerController.selectOnRootsTab(tvSeriePathEntity);
	}
	
	protected void handleStateChanged() {
		Set<String> tilesToRemove = new HashSet<>(this.tiles.keySet());

		List<ExplorerTvSerieTileView> tiles = this.view.getVisibleTiles();
		for (ExplorerTvSerieTileView tile : tiles) {
			TvSeriePathEntity tvSeriePathEntity = tile.getValue();
			String jobId = tvSeriePathEntity.getId();
			TileStatus tileStatus = this.tiles.get(jobId);
			if (tileStatus == null) {
				// Tile not visible
				this.tiles.put(jobId, new TileStatus(tile));
				TvSerieTileImagesAsyncJob job = new TvSerieTileImagesAsyncJob(tvSeriePathEntity.getTvSerie(), Dimensions.getExplorerTileSize(), Dimensions.getExplorerTilePosterSize());
				AsyncManager.getInstance().submit(jobId, job, this);
			}
			else {
				tilesToRemove.remove(jobId);
			}
		}

		for (String tileToRemove : tilesToRemove) {
			TileStatus tileStatus = this.tiles.remove(tileToRemove);
			if (tileStatus.alreadyLoaded) {
				tileStatus.tile.clear();
			}
			else {
				AsyncManager.getInstance().cancelTvSerieTileImagesAsyncJob(tileToRemove);
			}
		}
	}

	private static class TileStatus {

		private final ExplorerTvSerieTileView tile;
		private boolean alreadyLoaded;

		private TileStatus(ExplorerTvSerieTileView tile) {
			this.tile = tile;
			this.alreadyLoaded = false;
		}

	}

}
