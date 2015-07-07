package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;

import java.awt.Rectangle;

public class ExplorerTvSerieController {

	private final int tileWidth;
	private final int tileHeight;
	private final ExplorerTvSerieView view;
	
	public ExplorerTvSerieController(int explorerWidth) {
		this.tileWidth = explorerWidth;
		this.tileHeight = (int)((double)(this.tileWidth * 9) / 16d);
		this.view = new ExplorerTvSerieView(this);
	}

	public ExplorerTvSerieView getView() {
		return this.view;
	}
	
	public void addTile(TvSeriePathEntity tvSeriePathEntity) {
		ExplorerTvSerieTileController controller = new ExplorerTvSerieTileController(tvSeriePathEntity, this.tileWidth, this.tileHeight);
		this.view.addTile(controller.getView());
	}
	
	public void ciccio() {
		this.view.ciccio();
	}
	
	protected void handleStateChanged() {
		Rectangle viewRect = this.view.getViewport().getViewRect();
		System.out.printf("%d-%d\n", viewRect.x, viewRect.y);
	}
	
}
