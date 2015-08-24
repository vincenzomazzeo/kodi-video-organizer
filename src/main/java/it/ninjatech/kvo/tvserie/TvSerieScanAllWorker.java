package it.ninjatech.kvo.tvserie;

import java.io.File;

public class TvSerieScanAllWorker extends AbstractTvSerieWorker<TvSeriesPathEntity, TvSeriesPathEntity> {

	protected TvSerieScanAllWorker(TvSeriesPathEntity input) {
		super(input);
	}

	@Override
	public TvSeriesPathEntity work() throws Exception {
		TvSeriesPathEntity result = null;

		this.progressNotifier.notifyWorkerMessage("Scanning " + this.input.getLabel());

		if (TvSerieWorkerTasks.check(new File(this.input.getPath()), this.progressNotifier)) {
			TvSerieWorkerTasks.scan(this.input, this.progressNotifier);
			// TODO for each TvSeriePathEntity scan -- Update worker message with (x/y) TvSeriePathEntity name
			result = this.input;
		}
		else {
			// TODO delete
		}

		return result;
	}

}
