package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;

public class TvSerieRemoveWorker extends AbstractTvSerieWorker<TvSeriePathEntity, Void> {

	public TvSerieRemoveWorker(TvSeriePathEntity input) {
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
