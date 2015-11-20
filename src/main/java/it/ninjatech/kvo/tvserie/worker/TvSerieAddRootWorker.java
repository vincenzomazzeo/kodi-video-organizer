package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.util.Labels;

import java.io.File;

public class TvSerieAddRootWorker extends AbstractTvSerieWorker<File, TvSeriesPathEntity> {

	public TvSerieAddRootWorker(File input) {
		super(input);
	}

	@Override
	public TvSeriesPathEntity work() throws Exception {
		TvSeriesPathEntity result = null;
		
		this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerAddRoot(Labels.TV_SERIE_WORKER_ADD_ROOT_1, this.input.getName()));
		
		if (TvSerieWorkerTasks.check(this.input, this.progressNotifier)) {
		    this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerAddRoot(Labels.TV_SERIE_WORKER_ADD_ROOT_2, this.input.getName()));
			result = new TvSeriesPathEntity(this.input);
			
			this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerAddRoot(Labels.TV_SERIE_WORKER_ADD_ROOT_3, this.input.getName()));
			TvSerieWorkerTasks.scan(result, this.progressNotifier);
			
			this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerAddRoot(Labels.TV_SERIE_WORKER_ADD_ROOT_4, this.input.getName()));
			TvSerieWorkerTasks.save(result, this.progressNotifier);
		}
		
		return result;
	}

}
