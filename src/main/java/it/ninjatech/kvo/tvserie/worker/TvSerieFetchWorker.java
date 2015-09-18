package it.ninjatech.kvo.tvserie.worker;

import java.io.File;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;

public class TvSerieFetchWorker extends AbstractTvSerieWorker<TvSeriePathEntity, Boolean> {

	public TvSerieFetchWorker(TvSeriePathEntity input) {
		super(input);
	}
	
	@Override
	public Boolean work() throws Exception {
		Boolean result = false;
		
		// TODO message
		this.progressNotifier.notifyWorkerMessage(this.input.getLabel());
		
		if (TvSerieWorkerTasks.check(new File(this.input.getPath()), this.progressNotifier)) {
			TvSerieWorkerTasks.scan(this.input, this.progressNotifier);
			TvSerieWorkerTasks.fetch(this.input, this.progressNotifier);
			TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
			TvSerieWorkerTasks.save(this.input, this.progressNotifier);
			result = true;
		}
		
		return result;
	}

}
