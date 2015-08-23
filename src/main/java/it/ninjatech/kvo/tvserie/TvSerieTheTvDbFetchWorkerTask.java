package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;

public class TvSerieTheTvDbFetchWorkerTask implements TvSerieWorkerTask<TvSerie, Void> {

	@Override
	public Void doTask(TvSerie tvSerie) throws Exception {
		TheTvDbManager.getInstance().getData(tvSerie);
		
		return null;
	}
	
}
