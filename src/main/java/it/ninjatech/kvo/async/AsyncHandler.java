package it.ninjatech.kvo.async;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.SwingUtilities;

public class AsyncHandler<Job extends AsyncJob> implements Runnable {

	private final LinkedBlockingDeque<AsyncJobWrapper<Job>> jobs;
	private final Set<String> activeJobs;

	protected AsyncHandler() {
		this.jobs = new LinkedBlockingDeque<>();
		this.activeJobs = Collections.synchronizedSet(new HashSet<String>());
	}

	@Override
	public void run() {
		try {
			final AsyncJobWrapper<Job> jobWrapper = this.jobs.take();
			if (this.activeJobs.contains(jobWrapper.getId())) {
				jobWrapper.getJob().execute();
				if (this.activeJobs.remove(jobWrapper.getId())) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							jobWrapper.getListener().notify(jobWrapper.getId(), jobWrapper.getJob());
						}
					});
				}
			}
		}
		catch (InterruptedException e) {
		}
	}

	protected void submitJob(String id, Job job, AsyncJobListener<Job> listener) {
		this.activeJobs.add(id);
		this.jobs.offer(new AsyncJobWrapper<Job>(id, job, listener));
	}

	protected void removeJob(String id) {
		this.activeJobs.remove(id);
	}

}
