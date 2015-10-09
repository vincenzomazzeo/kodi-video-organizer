package it.ninjatech.kvo.tvserie.worker;

import java.io.File;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;

public class TvSerieScanWorker extends AbstractTvSerieWorker<TvSeriePathEntity, Boolean> {

	public TvSerieScanWorker(TvSeriePathEntity input) {
		super(input);
	}

	@Override
	public Boolean work() throws Exception {
		Boolean result = false;
		
		// TODO message
		this.progressNotifier.notifyWorkerMessage(this.input.getLabel());
		
		if (TvSerieWorkerTasks.check(new File(this.input.getPath()), this.progressNotifier)) {
			TvSerieWorkerTasks.scan(this.input, this.progressNotifier);
			if (this.input.getTvSerie() != null) {
				TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
				TvSerieWorkerTasks.save(this.input, this.progressNotifier);
			}
			result = true;
		}
		
		return result;
	}

}
