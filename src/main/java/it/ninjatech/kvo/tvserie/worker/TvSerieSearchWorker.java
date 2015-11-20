package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.tvserie.model.TvSerie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TvSerieSearchWorker extends AbstractTvSerieWorker<Map<String, EnhancedLocale>, Map<String, List<TvSerie>>> {

	public TvSerieSearchWorker(Map<String, EnhancedLocale> input) {
		super(input);
	}
	
	@Override
	public Map<String, List<TvSerie>> work() throws Exception {
		Map<String, List<TvSerie>> result = new HashMap<>();
		
		this.progressNotifier.notifyTaskInit(null, this.input.size());
		int counter = 1;
		for (String name : this.input.keySet()) {
		    this.progressNotifier.notifyTaskUpdate(null, counter++);
			result.put(name, TvSerieWorkerTasks.search(name, this.input.get(name), this.progressNotifier));
		}
		
		return result;
	}

}
