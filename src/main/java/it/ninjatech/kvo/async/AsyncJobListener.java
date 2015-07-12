package it.ninjatech.kvo.async;

public interface AsyncJobListener<Job extends AsyncJob> {

	public void notify(String id, Job job);
	
}
