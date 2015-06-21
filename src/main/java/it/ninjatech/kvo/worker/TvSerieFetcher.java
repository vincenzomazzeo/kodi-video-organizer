package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbConnector;
import it.ninjatech.kvo.model.TvSerie;

public class TvSerieFetcher extends AbstractWorker<TvSerie> {

	private final TvSerie tvSerie;
	
	public TvSerieFetcher(TvSerie tvSerie) {
		this.tvSerie = tvSerie;
	}

	@Override
	public TvSerie work() throws Exception {
		notifyUpdate(this.tvSerie.getName(), null);
		
		TheTvDbConnector.getInstance().getData(this.tvSerie);
		
		return this.tvSerie;
	}
	
}
