package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSeriePathEntity;

public class TvSerieFanartChoiceController {

	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieFanart fanart;
	private final TvSerieFanartChoiceView view;
	
	public TvSerieFanartChoiceController(TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart) {
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.fanart = fanart;
		this.view = new TvSerieFanartChoiceView(this, this.tvSeriePathEntity, this.fanart);
	}

	public TvSerieFanartChoiceView getView() {
		return this.view;
	}
	
}
