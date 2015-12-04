package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.util.Labels;

public class TvSerieRemoveWorker extends AbstractTvSerieWorker<TvSeriePathEntity, Void> {

	public TvSerieRemoveWorker(TvSeriePathEntity input) {
		super(input);
	}
	
	@Override
	public Void work() throws Exception {
	    this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerDelete(this.input.getLabel()));
        
        this.progressNotifier.notifyTaskInit(null, 100);
	    if (this.input.getTvSerie() != null) {
            TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
            this.progressNotifier.notifyTaskUpdate(null, 90);
        }
	    this.input.getTvSeriesPathEntity().removeTvSerie(this.input);
	    this.progressNotifier.notifyTaskUpdate(null, 100);
		
		return null;
	}
	
}
