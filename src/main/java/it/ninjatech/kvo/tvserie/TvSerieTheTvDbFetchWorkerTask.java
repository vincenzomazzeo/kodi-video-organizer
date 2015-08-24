package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;

public class TvSerieTheTvDbFetchWorkerTask implements TvSerieWorkerTask<TvSeriePathEntity> {

	protected TvSerieTheTvDbFetchWorkerTask() {}
	
	@Override
	public boolean doTask(TvSeriePathEntity tvSeriePathEntity) throws Exception {
		TheTvDbManager.getInstance().getData(tvSeriePathEntity.getTvSerie());
		
		return true;
	}
	
}
