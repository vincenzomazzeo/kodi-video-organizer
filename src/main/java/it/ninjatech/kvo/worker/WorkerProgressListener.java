package it.ninjatech.kvo.worker;

public interface WorkerProgressListener {

	public void workerInit(String message, String submessage, Integer value);
	
	public void workerUpdate(String message, String submessage, Integer value);
	
}
