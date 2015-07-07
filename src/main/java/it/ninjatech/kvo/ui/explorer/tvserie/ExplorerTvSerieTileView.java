package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.ui.ImageRetriever;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

public class ExplorerTvSerieTileView extends WebPanel {

	private static final long serialVersionUID = -4365144207002586456L;
	
	private final ExplorerTvSerieTileController controller;
	private ComponentTransition transition;
	
	protected ExplorerTvSerieTileView(ExplorerTvSerieTileController controller, int width, int height) {
		super();
		
		this.controller = controller;
		
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		
		init();
	}
	
	private void init() {
		this.transition = new ComponentTransition(new WebLabel(ImageRetriever.retrieveLoading()), new FadeTransitionEffect());
		add(this.transition, BorderLayout.CENTER);
	}
	
}
