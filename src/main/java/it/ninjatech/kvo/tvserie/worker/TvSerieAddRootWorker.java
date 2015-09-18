package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;

import java.io.File;

public class TvSerieAddRootWorker extends AbstractTvSerieWorker<File, TvSeriesPathEntity> {

	public TvSerieAddRootWorker(File input) {
		super(input);
	}

	@Override
	public TvSeriesPathEntity work() throws Exception {
		TvSeriesPathEntity result = null;
		
		// TODO message
		
		this.progressNotifier.notifyWorkerMessage("( /3) Scanning " + this.input.getName());
		
		if (TvSerieWorkerTasks.check(this.input, this.progressNotifier)) {
			this.progressNotifier.notifyWorkerMessage("(1/3) Scanning " + this.input.getName());
			result = new TvSeriesPathEntity(this.input);
			
			this.progressNotifier.notifyWorkerMessage("(2/3) Scanning " + this.input.getName());
			TvSerieWorkerTasks.scan(result, this.progressNotifier);
			
			this.progressNotifier.notifyWorkerMessage("(3/3) Scanning " + this.input.getName());
			TvSerieWorkerTasks.save(result, this.progressNotifier);
		}
		
		return result;
	}

}
