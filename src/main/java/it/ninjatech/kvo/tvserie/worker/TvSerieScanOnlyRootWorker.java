package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;

import java.io.File;

public class TvSerieScanOnlyRootWorker extends AbstractTvSerieWorker<TvSeriesPathEntity, Boolean> {

	public TvSerieScanOnlyRootWorker(TvSeriesPathEntity input) {
		super(input);
	}

	@Override
	public Boolean work() throws Exception {
		Boolean result = false;

		// TODO message
		this.progressNotifier.notifyWorkerMessage("Scanning " + this.input.getLabel());

		if (TvSerieWorkerTasks.check(new File(this.input.getPath()), this.progressNotifier)) {
			TvSerieWorkerTasks.scan(this.input, this.progressNotifier);
			result = true;
		}

		return result;
	}

}
