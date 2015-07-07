package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;

import java.util.HashMap;
import java.util.Map;

public class ExplorerTvSerieModel {

	private final Map<TvSeriePathEntity, ExplorerTvSerieTileController> data;
	
	protected ExplorerTvSerieModel() {
		this.data = new HashMap<>();
	}
	
	protected void addTile(TvSeriePathEntity tvSeriePathEntity) {
//		ExplorerTvSerieTileController controller = new ExplorerTvSerieTileController(tvSeriePathEntity, this.tileWidth, this.tileHeight);
	}
	
}
