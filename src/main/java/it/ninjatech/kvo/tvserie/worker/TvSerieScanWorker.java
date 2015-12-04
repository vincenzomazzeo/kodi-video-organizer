package it.ninjatech.kvo.tvserie.worker;

import java.io.File;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.util.Labels;

public class TvSerieScanWorker extends AbstractTvSerieWorker<TvSeriePathEntity, Boolean> {

	public TvSerieScanWorker(TvSeriePathEntity input) {
		super(input);
	}

	@Override
	public Boolean work() throws Exception {
		Boolean result = false;
		
		this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerScan(this.input.getLabel()));
		
		this.progressNotifier.notifyTaskInit(null, 100);
		if (TvSerieWorkerTasks.check(new File(this.input.getPath()), this.progressNotifier)) {
		    this.progressNotifier.notifyTaskUpdate(null, 10);
			TvSerieWorkerTasks.scan(this.input, this.progressNotifier);
			this.progressNotifier.notifyTaskUpdate(null, 80);
			if (this.input.getTvSerie() != null) {
				TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
				this.progressNotifier.notifyTaskUpdate(null, 90);
				TvSerieWorkerTasks.save(this.input, this.progressNotifier);
			}
			result = true;
		}
		else {
		    this.progressNotifier.notifyTaskUpdate(null, 10);
		    TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
            this.progressNotifier.notifyTaskUpdate(null, 90);
		    this.input.getTvSeriesPathEntity().removeTvSerie(this.input);
		}
		this.progressNotifier.notifyTaskUpdate(null, 100);
		
		return result;
	}

}
