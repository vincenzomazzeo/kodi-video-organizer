package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.ImageRetriever;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

public class ExplorerTvSerieTileView extends WebPanel {

	private static final long serialVersionUID = -4365144207002586456L;
	
	private final TvSeriePathEntity value;
	private final ExplorerTvSerieController controller;
	private ComponentTransition transition;
	
	protected ExplorerTvSerieTileView(TvSeriePathEntity value, ExplorerTvSerieController controller, int width, int height) {
		super();
		
		this.value = value;
		this.controller = controller;
		
		setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout());
		
		init();
	}
	
	protected TvSeriePathEntity getValue() {
		return this.value;
	}
	
	protected void setImages(Image fanart, Image poster) {
		this.transition.performTransition(new WebLabel(new ImageIcon(fanart)));
	}
	
	protected void clear() {
		// TODO
	}
	
	private void init() {
		this.transition = new ComponentTransition(new WebLabel(ImageRetriever.retrieveLoading()), new FadeTransitionEffect());
		add(this.transition, BorderLayout.CENTER);
	}
	
}
