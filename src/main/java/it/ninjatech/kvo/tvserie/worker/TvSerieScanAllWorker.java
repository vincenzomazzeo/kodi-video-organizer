package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;

import java.io.File;

public class TvSerieScanAllWorker extends AbstractTvSerieWorker<TvSeriesPathEntity, Boolean> {

	public TvSerieScanAllWorker(TvSeriesPathEntity input) {
		super(input);
	}

	@Override
	public Boolean work() throws Exception {
		Boolean result = false;

		// TODO
		this.progressNotifier.notifyWorkerMessage("Scanning " + this.input.getLabel());

		if (TvSerieWorkerTasks.check(new File(this.input.getPath()), this.progressNotifier)) {
			TvSerieWorkerTasks.scan(this.input, this.progressNotifier);
			// TODO for each TvSeriePathEntity scan -- Update worker message with (x/y) TvSeriePathEntity name
			for (TvSeriePathEntity tvSeriePathEntity : this.input.getTvSeries()) {
				if (TvSerieWorkerTasks.check(new File(tvSeriePathEntity.getPath()), this.progressNotifier)) {
					TvSerieWorkerTasks.scan(tvSeriePathEntity, this.progressNotifier);
					if (tvSeriePathEntity.getTvSerie() != null) {
						TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
						TvSerieWorkerTasks.save(tvSeriePathEntity, this.progressNotifier);
					}
				}
				else {
					this.input.removeTvSerie(tvSeriePathEntity);
					if (tvSeriePathEntity.getTvSerie() != null) {
						TvSerieWorkerTasks.delete(tvSeriePathEntity, this.progressNotifier);
					}
				}
			}
			
			result = true;
		}

		return result;
	}

}
