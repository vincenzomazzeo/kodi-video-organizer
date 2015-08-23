package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;

public class TvSerieFanarttvFetchWorkerTask implements TvSerieWorkerTask {

	@Override
	public boolean doTask(TvSeriePathEntity tvSeriePathEntity) throws Exception {
		FanarttvManager.getInstance().getData(tvSeriePathEntity.getTvSerie());
		
		return true;
	}
	
}
