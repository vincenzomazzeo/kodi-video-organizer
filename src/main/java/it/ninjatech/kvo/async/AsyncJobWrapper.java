package it.ninjatech.kvo.async;

import java.io.Serializable;

public class AsyncJobWrapper<Job extends AsyncJob> implements Serializable {

	private static final long serialVersionUID = 5334631972867088301L;

	private String id;
	private Job job;
	private AsyncJobListener listener;
	
	public AsyncJobWrapper(String id, Job job, AsyncJobListener listener) {
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

	protected AsyncJobListener getListener() {
		return this.listener;
	}
	
	protected void clear() {
		this.id = null;
		this.job = null;
		this.listener = null;
	}
	
}
