package it.ninjatech.kvo.tvserie;

public class TvSerieScanWorker extends AbstractTvSerieWorker<TvSeriePathEntity, TvSeriePathEntity> {

	protected TvSerieScanWorker(TvSeriePathEntity input) {
		super(input);
	}

	@Override
	public TvSeriePathEntity work() throws Exception {
		TvSeriePathEntity result = null;
		
		this.progressNotifier.notifyWorkerMessage(this.input.getLabel());
		result = TvSerieWorkerTasks.scan(this.input, this.progressNotifier);
		
		return result;
	}

}
