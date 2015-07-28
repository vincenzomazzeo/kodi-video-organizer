package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.AbstractTvSerieImageLoaderAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieActorImageAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieCacheRemoteImageAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalFanartAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalSeasonImageAsyncJob;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TvSerieImageLoaderAsyncJobHandler implements AsyncJobListener {

	private final Map<String, JobInfo> jobMap;
	
	public TvSerieImageLoaderAsyncJobHandler() {
		this.jobMap = new HashMap<>();
	}
	
	@Override
	public void notify(String id, AsyncJob job) {
		if (job.getException() != null) {
			UI.get().notifyException(job.getException());
		}
		else {
			JobInfo jobInfo = this.jobMap.remove(id);
			if (jobInfo != null) {
				Image image = null;
				Object supportData = null;
				if (job.getClass().equals(TvSerieLocalSeasonImageAsyncJob.class)) {
					image = ((TvSerieLocalSeasonImageAsyncJob)job).getImage();
					supportData = ((TvSerieLocalSeasonImageAsyncJob)job).getSeason();
				}
				else if (job.getClass().equals(TvSerieCacheRemoteImageAsyncJob.class)) {
					image = ((TvSerieCacheRemoteImageAsyncJob)job).getImage();
				}
				else if (job.getClass().equals(TvSerieLocalFanartAsyncJob.class)) {
					image = ((TvSerieLocalFanartAsyncJob)job).getImage();
					supportData = ((TvSerieLocalFanartAsyncJob)job).getFanart();
				}
				else if (job.getClass().equals(TvSerieActorImageAsyncJob.class)) {
					image = ((TvSerieActorImageAsyncJob)job).getImage();
					supportData = ((TvSerieActorImageAsyncJob)job).getActor();
				}
				else {
					throw new RuntimeException(String.format("Unexpected Job type: %s", job.getClass()));
				}
				if (image != null) {
					jobInfo.listener.notifyImageLoaded(id, image, supportData);
				}
			}
		}
	}

	public void handle(TvSerieLocalSeasonImageAsyncJob job, TvSerieImageLoaderListener listener) {
		this.jobMap.put(job.getId(), new JobInfo(job.getId(), listener, job.getClass()));
		AsyncManager.getInstance().submit(job.getId(), job, this);
	}
	
	public void handle(TvSerieCacheRemoteImageAsyncJob job, TvSerieImageLoaderListener listener) {
		this.jobMap.put(job.getId(), new JobInfo(job.getId(), listener, job.getClass()));
		AsyncManager.getInstance().submit(job.getId(), job, this);
	}
	
	public void handle(TvSerieLocalFanartAsyncJob job, TvSerieImageLoaderListener listener) {
		this.jobMap.put(job.getId(), new JobInfo(job.getId(), listener, job.getClass()));
		AsyncManager.getInstance().submit(job.getId(), job, this);
	}
	
	public void handle(TvSerieActorImageAsyncJob job, TvSerieImageLoaderListener listener) {
		this.jobMap.put(job.getId(), new JobInfo(job.getId(), listener, job.getClass()));
		AsyncManager.getInstance().submit(job.getId(), job, this);
	}
	
	public void cancelAll() {
		List<JobInfo> jobs = new ArrayList<>(this.jobMap.values());
		this.jobMap.clear();
		for (JobInfo job : jobs) {
			if (job.jobClass.equals(TvSerieLocalSeasonImageAsyncJob.class)) {
				AsyncManager.getInstance().cancelTvSerieLocalSeasonImageAsyncJob(job.id);
			}
			else if (job.jobClass.equals(TvSerieCacheRemoteImageAsyncJob.class)) {
				AsyncManager.getInstance().cancelTvSerieCacheRemoteImageAsyncJob(job.id);
			}
		}
	}
 
	public static interface TvSerieImageLoaderListener {
		
		public void notifyImageLoaded(String id, Image image, Object supportData);
		
	}
	
	private static class JobInfo {
		
		private final String id;
		private final TvSerieImageLoaderListener listener;
		private final Class<? extends AbstractTvSerieImageLoaderAsyncJob> jobClass;
		
		private JobInfo(String id, TvSerieImageLoaderListener listener, Class<? extends AbstractTvSerieImageLoaderAsyncJob> jobClass) {
			this.id = id;
			this.listener = listener;
			this.jobClass = jobClass;
		}
		
	}
	
}
