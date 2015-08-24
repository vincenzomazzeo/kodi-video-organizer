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
		
		private static final String MESSAGE = "<html><div align='center'>%s<br />%s</div></html>";
		
		private final AbstractTvSerieWorker<?, ?> worker;
		
		private String workerMessage;
		private String taskMessage;
		
		private ProgressNotifier(AbstractTvSerieWorker<?, ?> worker) {
			this.worker = worker;
			this.workerMessage = "";
			this.taskMessage = "";
		}
		
		protected void notifyWorkerMessage(String workerMessage) {
			this.workerMessage = workerMessage;
			this.worker.notifyUpdate(String.format(MESSAGE, this.workerMessage, this.taskMessage), null);
		}
		
		protected void notifyTaskInit(String taskMessage, Integer maxProgress) {
			this.taskMessage = taskMessage == null ? "" : taskMessage;
			this.worker.notifyInit(String.format(MESSAGE, this.workerMessage, this.taskMessage), maxProgress); 
		}
		
		protected void notifyTaskUpdate(String taskMessage, Integer progress) {
			this.taskMessage = taskMessage == null ? "" : taskMessage;
			this.worker.notifyUpdate(String.format(MESSAGE, this.workerMessage, this.taskMessage), progress);
		}
		
	}

}
