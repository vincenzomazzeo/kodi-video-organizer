package it.ninjatech.kvo.worker;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorker {

private List<WorkerProgressListener> listeners;
	
	protected AbstractWorker() {
		this.listeners = new ArrayList<>();
	}
	
	public void addWorkerProgressListener(WorkerProgressListener listener) {
		this.listeners.add(listener);
	}
	
	protected void notifyInit(String message, Integer value) {
		for (WorkerProgressListener listener : this.listeners) {
			listener.workerInit(message, value);
		}
	}
	
	protected void notifyUpdate(String message, Integer value) {
		for (WorkerProgressListener listener : this.listeners) {
			listener.workerUpdate(message, value);
		}
	}
	
}
