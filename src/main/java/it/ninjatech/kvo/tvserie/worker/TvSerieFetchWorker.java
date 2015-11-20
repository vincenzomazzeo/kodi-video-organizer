package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.util.Labels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TvSerieFetchWorker extends AbstractTvSerieWorker<List<TvSeriePathEntity>, List<Boolean>> {

	public TvSerieFetchWorker(List<TvSeriePathEntity> input) {
		super(input);
	}
	
	@Override
	public List<Boolean> work() throws Exception {
		List<Boolean> result = new ArrayList<>();
		
		for (int i = 0, n = this.input.size(); i < n; i++) {
		    TvSeriePathEntity entity = this.input.get(i);
		    this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerFetch(i + 1, n, entity.getLabel()));
		    this.progressNotifier.notifyTaskInit(null, 100);
		    
		    if (TvSerieWorkerTasks.check(new File(entity.getPath()), this.progressNotifier)) {
		        this.progressNotifier.notifyTaskUpdate(null, 10);
		        TvSerieWorkerTasks.scan(entity, this.progressNotifier);
		        this.progressNotifier.notifyTaskUpdate(null, 50);
		        TvSerieWorkerTasks.fetch(entity, this.progressNotifier);
		        this.progressNotifier.notifyTaskUpdate(null, 85);
		        TvSerieWorkerTasks.delete(entity, this.progressNotifier);
		        this.progressNotifier.notifyTaskUpdate(null, 90);
		        TvSerieWorkerTasks.save(entity, this.progressNotifier);
		        result.add(true);
		    }
		    else {
		        this.progressNotifier.notifyTaskUpdate(null, 10);
		        TvSerieWorkerTasks.delete(entity, this.progressNotifier);
		        result.add(false);
		    }
		    this.progressNotifier.notifyTaskUpdate(null, 100);
		}
		
		return result;
	}

}
