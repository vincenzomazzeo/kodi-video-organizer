package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;

import java.util.List;

public class ExplorerTvSerieController {

	private final ExplorerTvSerieModel model;
	private final ExplorerTvSerieView view;
	
	public ExplorerTvSerieController(int explorerWidth) {
		this.model = new ExplorerTvSerieModel();
		this.view = new ExplorerTvSerieView(this, this.model, explorerWidth, (int)((double)(explorerWidth * 9) / 16d));
	}

	public ExplorerTvSerieView getView() {
		return this.view;
	}
	
	public void addTile(TvSeriePathEntity tvSeriePathEntity) {
		this.model.addTile(tvSeriePathEntity);
	}
	
	protected void handleStateChanged() {
		List<ExplorerTvSerieTileView> tiles = this.view.getVisibleTiles();
		for (ExplorerTvSerieTileView tile : tiles) {
			System.out.println(tile);
		}
	}
	
}
