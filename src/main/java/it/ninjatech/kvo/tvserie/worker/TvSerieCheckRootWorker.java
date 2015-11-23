package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;

import java.io.File;

public class TvSerieCheckRootWorker extends AbstractTvSerieWorker<TvSeriesPathEntity, Boolean> {

	public TvSerieCheckRootWorker(TvSeriesPathEntity input) {
		super(input);
	}

	@Override
	public Boolean work() throws Exception {
	    Boolean result = false;
	    
	    result = TvSerieWorkerTasks.check(new File(this.input.getPath()), this.progressNotifier);
	    if (!result) {
	        TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
	    }
	    
	    return result;
	}

}
