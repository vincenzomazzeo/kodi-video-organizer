package it.ninjatech.kvo.tvserie;

public interface TvSerieWorkerTask<I, O> {

	public O doTask(I input) throws Exception;
	
}
