package it.ninjatech.kvo.tvserie;

public class TvSerieFetchWorker extends AbstractTvSerieWorker<TvSeriePathEntity, TvSeriePathEntity> {

	protected TvSerieFetchWorker(TvSeriePathEntity input) {
		super(input);
	}

	@Override
	public TvSeriePathEntity work() throws Exception {
		TvSeriePathEntity result = null;
		
		this.progressNotifier.notifyWorkerMessage(this.input.getLabel());
		result = TvSerieWorkerTasks.fetch(this.input, this.progressNotifier);
		
		return result;
	}

}
