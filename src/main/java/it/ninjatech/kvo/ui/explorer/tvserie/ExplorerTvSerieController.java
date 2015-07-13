package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieTileImagesAsyncJob;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.Dimensions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExplorerTvSerieController implements AsyncJobListener<TvSerieTileImagesAsyncJob> {

	private final ExplorerTvSerieModel model;
	private final ExplorerTvSerieView view;
	private final Map<String, TileStatus> tiles;
	
	public ExplorerTvSerieController() {
		this.model = new ExplorerTvSerieModel();
		this.view = new ExplorerTvSerieView(this, this.model);
		this.tiles = new HashMap<>();
	}

	@Override
	public void notify(String id, TvSerieTileImagesAsyncJob job) {
		System.out.printf("-> notify %s\n", id);
		TileStatus tileStatus = this.tiles.get(id);
		tileStatus.alreadyLoaded = true;
		tileStatus.tile.setImages(job.getFanart(), job.getPoster());
	}
	
	public ExplorerTvSerieView getView() {
		return this.view;
	}
	
	public void addTile(TvSeriePathEntity tvSeriePathEntity) {
		this.model.addTile(tvSeriePathEntity);
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
				TvSerieTileImagesAsyncJob job = new TvSerieTileImagesAsyncJob(tvSeriePathEntity, Dimensions.getExplorerTileSize(), Dimensions.getExplorerTilePosterSize());
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