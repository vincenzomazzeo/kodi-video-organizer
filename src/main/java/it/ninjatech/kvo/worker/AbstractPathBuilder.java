package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.model.AbstractPathEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPathBuilder {

	private List<WorkerProgressListener> listeners;
	
	protected AbstractPathBuilder() {
		this.listeners = new ArrayList<>();
	}
	
	public abstract AbstractPathEntity build();
	
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
