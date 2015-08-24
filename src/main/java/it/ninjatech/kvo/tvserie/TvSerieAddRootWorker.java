package it.ninjatech.kvo.tvserie;

import java.io.File;

public class TvSerieAddRootWorker extends AbstractTvSerieWorker<File, TvSeriesPathEntity> {

	public TvSerieAddRootWorker(File input) {
		super(input);
	}

	@Override
	public TvSeriesPathEntity work() throws Exception {
		TvSeriesPathEntity result = null;
		
		this.progressNotifier.notifyWorkerMessage("Scanning " + this.input.getName());
		
		if (TvSerieWorkerTasks.check(this.input, this.progressNotifier)) {
			result = new TvSeriesPathEntity(this.input);
			
			TvSerieWorkerTasks.scan(result, this.progressNotifier);
			
			TvSerieWorkerTasks.save(result, this.progressNotifier);
		}
		
		return result;
	}

}
