package it.ninjatech.kvo.ui.explorer.tvserie;

public class ExplorerTvSerieTileController {

	private final ExplorerTvSerieTileView view;
	
	public ExplorerTvSerieTileController(int uiWidth) {
		this.view = new ExplorerTvSerieTileView(this, uiWidth);
	}

	public ExplorerTvSerieTileView getView() {
		return this.view;
	}
	
}
