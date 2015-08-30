package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.worker.AbstractWorker;

public abstract class AbstractTvSerieWorker<I, O> extends AbstractWorker<O> {

	protected final I input;
	protected final ProgressNotifier progressNotifier;
	
	protected AbstractTvSerieWorker(I input) {
		this.input = input;
		this.progressNotifier = new ProgressNotifier(this);
	}
	
	protected static class ProgressNotifier {
		
		private final AbstractTvSerieWorker<?, ?> worker;
		
		private ProgressNotifier(AbstractTvSerieWorker<?, ?> worker) {
			this.worker = worker;
		}
		
		protected void notifyWorkerMessage(String workerMessage) {
			this.worker.notifyUpdate(workerMessage, null);
		}
		
		protected void notifyTaskInit(String taskMessage, Integer maxProgress) {
			this.worker.notifyInit(null, taskMessage, maxProgress);
		}
		
		protected void notifyTaskUpdate(String taskMessage, Integer progress) {
			this.worker.notifyUpdate(null, taskMessage, progress);
		}
		
	}

}
