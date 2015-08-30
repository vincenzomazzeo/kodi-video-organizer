package it.ninjatech.kvo.worker;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorker<T> {

	private List<WorkerProgressListener> listeners;

	protected AbstractWorker() {
		this.listeners = new ArrayList<>();
	}
	
	public abstract T work() throws Exception;

	public void addWorkerProgressListener(WorkerProgressListener listener) {
		this.listeners.add(listener);
	}

	protected void notifyInit(String message, Integer value) {
		notifyInit(message, null, value);
	}
	
	protected void notifyInit(String message, String submessage, Integer value) {
		for (WorkerProgressListener listener : this.listeners) {
			listener.workerInit(message, submessage, value);
		}
	}

	protected void notifyUpdate(String message, Integer value) {
		notifyUpdate(message, null, value);
	}
	
	protected void notifyUpdate(String message, String submessage, Integer value) {
		for (WorkerProgressListener listener : this.listeners) {
			listener.workerUpdate(message, submessage, value);
		}
	}

}
