package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;

public class TvSerieFetcher extends AbstractWorker<TvSerie> {

	private final TvSerie tvSerie;
	
	public TvSerieFetcher(TvSerie tvSerie) {
		this.tvSerie = tvSerie;
	}

	@Override
	public TvSerie work() throws Exception {
		TvSerieFileScanner fileScanner = new TvSerieFileScanner(this.tvSerie);
		notifyInit(this.tvSerie.getName(), fileScanner.getFileCount() + 2);
		
		fileScanner.work();
		
		TheTvDbManager.getInstance().getData(this.tvSerie);
		notifyUpdate(null, fileScanner.getFileCount() + 1);
		FanarttvManager.getInstance().getData(this.tvSerie);
		notifyUpdate(null, fileScanner.getFileCount() + 2);
		
		return this.tvSerie;
	}
	
}
