package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.util.Labels;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TvSerieFetchAllWorker extends AbstractTvSerieWorker<TvSeriesPathEntity, Map<TvSeriePathEntity, Boolean>> {

	public TvSerieFetchAllWorker(TvSeriesPathEntity input) {
		super(input);
	}

	@Override
	public Map<TvSeriePathEntity, Boolean> work() throws Exception {
	    Map<TvSeriePathEntity, Boolean> result = null;

		this.progressNotifier.notifyWorkerMessage(Labels.tvSeriesWorkerFetch(Labels.TV_SERIES_WORKER_FETCH_1, this.input.getLabel(), 0, 0, null));

		if (TvSerieWorkerTasks.check(new File(this.input.getPath()), this.progressNotifier)) {
		    result = new HashMap<>();
		    Set<TvSeriePathEntity> tvSeriePathEntities = this.input.getTvSeries();
		    int current = 0;
		    int total = tvSeriePathEntities.size();
			for (TvSeriePathEntity tvSeriePathEntity : tvSeriePathEntities) {
			    current++;
			    this.progressNotifier.notifyWorkerMessage(Labels.tvSeriesWorkerFetch(Labels.TV_SERIES_WORKER_FETCH_2, 
			                                                                         this.input.getLabel(), current, total, 
			                                                                         tvSeriePathEntity.getLabel()));
				if (tvSeriePathEntity.getTvSerie() == null) {
				    this.progressNotifier.notifyTaskInit(null, 100);
					if (TvSerieWorkerTasks.check(new File(tvSeriePathEntity.getPath()), this.progressNotifier)) {
					    this.progressNotifier.notifyTaskUpdate(null, 10);
						TvSerieWorkerTasks.scan(tvSeriePathEntity, this.progressNotifier);
						this.progressNotifier.notifyTaskUpdate(null, 50);
						TvSerieWorkerTasks.fetch(tvSeriePathEntity, this.progressNotifier);
						this.progressNotifier.notifyTaskUpdate(null, 85);
						TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
						this.progressNotifier.notifyTaskUpdate(null, 90);
						TvSerieWorkerTasks.save(tvSeriePathEntity, this.progressNotifier);
						result.put(tvSeriePathEntity, true);
					}
					else {
					    this.progressNotifier.notifyTaskUpdate(null, 10);
						TvSerieWorkerTasks.delete(tvSeriePathEntity, this.progressNotifier);
						result.put(tvSeriePathEntity, false);
					}
					this.progressNotifier.notifyTaskUpdate(null, 100);
				}
			}
		}
		
		return result;
	}

}
