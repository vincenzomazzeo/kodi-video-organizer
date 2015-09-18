package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;

public class TvSerieRemoveRootWorker extends AbstractTvSerieWorker<TvSeriesPathEntity, Void> {

	public TvSerieRemoveRootWorker(TvSeriesPathEntity input) {
		super(input);
	}
	
	@Override
	public Void work() throws Exception {
		// TODO message
//		this.progressNotifier.notifyWorkerMessage(this.input.getLabel());
		
		TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
		
		return null;
	}
	
}
