package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;

public class ExplorerTvSerieTileController {

	private final TvSeriePathEntity tvSeriePathEntity;
	private final ExplorerTvSerieTileView view;
	
	protected ExplorerTvSerieTileController(TvSeriePathEntity tvSeriePathEntity, int tileWidth, int tileHeight) {
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.view = new ExplorerTvSerieTileView(this, tileWidth, tileHeight);
	}

	protected ExplorerTvSerieTileView getView() {
		return this.view;
	}
	
}
