package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.TvSerieTileImagesAsyncJob;
import it.ninjatech.kvo.model.TvSeriePathEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExplorerTvSerieController implements AsyncJobListener<TvSerieTileImagesAsyncJob> {

	private final ExplorerTvSerieModel model;
	private final int tileWidth;
	private final int tileHeight;
	private final ExplorerTvSerieView view;
	private final Map<String, ExplorerTvSerieTileView> asyncTileJobs;
	
	public ExplorerTvSerieController(int explorerWidth) {
		this.model = new ExplorerTvSerieModel();
		this.tileWidth = explorerWidth;
		this.tileHeight = (int)((double)(explorerWidth * 9) / 16d);
		this.view = new ExplorerTvSerieView(this, this.model, this.tileWidth, this.tileHeight);
		this.asyncTileJobs = new HashMap<>();
	}

	@Override
	public void notify(String id, TvSerieTileImagesAsyncJob job) {
		ExplorerTvSerieTileView tile = this.asyncTileJobs.remove(id);
		if (tile != null) {
			tile.setImages(job.getFanart(), job.getPoster());
		}
	}
	
	public ExplorerTvSerieView getView() {
		return this.view;
	}
	
	public void addTile(TvSeriePathEntity tvSeriePathEntity) {
		this.model.addTile(tvSeriePathEntity);
	}
	
	protected void handleStateChanged() {
		Set<String> keys = new HashSet<>(this.asyncTileJobs.keySet());
		
		List<ExplorerTvSerieTileView> tiles = this.view.getVisibleTiles();
		for (ExplorerTvSerieTileView tile : tiles) {
			TvSeriePathEntity tvSeriePathEntity = tile.getValue();
			String jobId = tvSeriePathEntity.getId();
			if (!keys.remove(jobId)) {
				this.asyncTileJobs.put(jobId, tile);
				TvSerieTileImagesAsyncJob job = new TvSerieTileImagesAsyncJob(tvSeriePathEntity, this.tileWidth, this.tileHeight, this.tileWidth, this.tileHeight);
				AsyncManager.getInstance().submit(jobId, job, this);
			}
		}
		
		for (String key : keys) {
			ExplorerTvSerieTileView tile = this.asyncTileJobs.remove(key);
			tile.clear();
			AsyncManager.getInstance().cancelTvSerieTileImagesAsyncJob(key);
		}
	}

}
