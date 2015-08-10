package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.component.ExplorerTile;
import it.ninjatech.kvo.util.TvSerieUtils;

import java.awt.BorderLayout;
import java.awt.Image;

import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.panel.WebPanel;

public class ExplorerTvSerieTileView extends WebPanel {

	private static final long serialVersionUID = -4365144207002586456L;

	private final TvSeriePathEntity value;
	private final ExplorerTvSerieController controller;
	private ComponentTransition transition;
	private ExplorerTile defaultTile;
	private ExplorerTile fullTile;

	protected ExplorerTvSerieTileView(TvSeriePathEntity value, ExplorerTvSerieController controller) {
		super();

		this.value = value;
		this.controller = controller;

		init();
	}

	protected TvSeriePathEntity getValue() {
		return this.value;
	}

	protected void setImages(Image fanart, Image poster) {
		if (fanart != null || poster != null) {
			this.fullTile = new ExplorerTile(fanart, poster != null ? poster : ImageRetriever.retrieveExplorerTilePosterTvSerie().getImage(),
											 TvSerieUtils.getTitle(this.value),
											 TvSerieUtils.getYear(this.value.getTvSerie()),
											 TvSerieUtils.getRate(this.value.getTvSerie()),
											 TvSerieUtils.getGenre(this.value.getTvSerie()));
			this.transition.performTransition(this.fullTile);
		}
	}

	protected void clear() {
		this.transition.performTransition(this.defaultTile);
		if (this.fullTile != null) {
			this.fullTile.destroy();
			this.fullTile = null;
		}
	}

	protected void destroy() {
		removeAll();
		this.defaultTile.destroy();
		this.defaultTile = null;
		if (this.fullTile != null) {
			this.fullTile.destroy();
			this.fullTile = null;
		}
	}

	private void init() {
		setLayout(new BorderLayout());

		this.defaultTile = new ExplorerTile(null, ImageRetriever.retrieveExplorerTilePosterTvSerie().getImage(),
											TvSerieUtils.getTitle(this.value),
											TvSerieUtils.getYear(this.value.getTvSerie()),
											TvSerieUtils.getRate(this.value.getTvSerie()),
											TvSerieUtils.getGenre(this.value.getTvSerie()));

		this.transition = new ComponentTransition(this.defaultTile, new FadeTransitionEffect());
		add(this.transition, BorderLayout.CENTER);
	}

}
