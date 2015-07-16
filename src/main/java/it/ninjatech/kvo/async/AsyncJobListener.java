package it.ninjatech.kvo.async;

public interface AsyncJobListener {

	public void notify(String id, AsyncJob job);
	
}
