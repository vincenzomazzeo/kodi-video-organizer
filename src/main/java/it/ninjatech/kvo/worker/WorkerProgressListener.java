package it.ninjatech.kvo.worker;

public interface WorkerProgressListener {

	public void workerInit(String message, Integer value);
	
	public void workerUpdate(String message, Integer value);
	
}
