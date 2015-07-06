package it.ninjatech.kvo.ui.explorer.tvserie;

import java.awt.Dimension;

import com.alee.laf.panel.WebPanel;

public class ExplorerTvSerieTileView extends WebPanel {

	private final ExplorerTvSerieTileController controller;
	
	protected ExplorerTvSerieTileView(ExplorerTvSerieTileController controller, int uiWidth) {
		super();
		
		this.controller = controller;
		
		init(uiWidth);
	}
	
	private void init(int uiWidth) {
		int width = uiWidth / 5;
		int height = (int)((double)(width / 5 * 16) / 9d);
		
		setPreferredSize(new Dimension(width, height));
	}
	
}
