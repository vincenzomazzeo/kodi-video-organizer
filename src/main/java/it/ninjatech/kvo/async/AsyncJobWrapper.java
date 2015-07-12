package it.ninjatech.kvo.async;

import java.io.Serializable;

public class AsyncJobWrapper<Job extends AsyncJob> implements Serializable {

	private static final long serialVersionUID = 5334631972867088301L;

	private final String id;
	private final Job job;
	private final AsyncJobListener<Job> listener;
	
	public AsyncJobWrapper(String id, Job job, AsyncJobListener<Job> listener) {
		this.id = id;
		this.job = job;
		this.listener = listener;
	}

	protected String getId() {
		return this.id;
	}

	protected Job getJob() {
		return this.job;
	}

	protected AsyncJobListener<Job> getListener() {
		return this.listener;
	}
	
}
