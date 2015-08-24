package it.ninjatech.kvo.tvserie;

public abstract class TvSerieWorkerTask<T> {

	private TvSerieWorker<T> worker;
	
	protected TvSerieWorkerTask() {}
	
	public abstract boolean doTask(T pathEntity) throws Exception;
	
	protected void setWorker(TvSerieWorker<T> worker) {
		this.worker = worker;
	}
	
	
	
}
