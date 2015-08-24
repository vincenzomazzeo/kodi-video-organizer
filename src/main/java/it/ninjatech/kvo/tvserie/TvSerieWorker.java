package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.worker.AbstractWorker;

public class TvSerieWorker<T> extends AbstractWorker<Boolean> {

	private final TvSerieWorkerTask<T>[] tasks;
	
	@SafeVarargs
	protected TvSerieWorker(TvSerieWorkerTask<T>... tasks) {
		this.tasks = tasks;
		
		for (TvSerieWorkerTask<T> task : this.tasks) {
			task.setWorker(this);
		}
	}
	
	@Override
	public Boolean work() throws Exception {
		return null;
	}

}
