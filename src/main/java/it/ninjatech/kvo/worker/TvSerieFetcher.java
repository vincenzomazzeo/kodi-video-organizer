package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;

public class TvSerieFetcher extends AbstractWorker<TvSerie> {

	private final TvSerie tvSerie;
	
	public TvSerieFetcher(TvSerie tvSerie) {
		this.tvSerie = tvSerie;
	}

	@Override
	public TvSerie work() throws Exception {
		notifyUpdate(this.tvSerie.getName(), null);
		
		TheTvDbManager.getInstance().getData(this.tvSerie);
		
		return this.tvSerie;
	}
	
}
