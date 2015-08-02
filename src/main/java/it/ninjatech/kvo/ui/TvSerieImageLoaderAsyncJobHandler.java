package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.async.AsyncJobListener;
import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.async.job.CacheRemoteImageAsyncJob;
import it.ninjatech.kvo.async.job.PersonAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalFanartAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalSeasonImageAsyncJob;
import it.ninjatech.kvo.model.TvSerieActor;

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
				else if (job.getClass().equals(CacheRemoteImageAsyncJob.class)) {
					image = ((CacheRemoteImageAsyncJob)job).getImage();
					supportData = jobInfo.supportData;
				}
				else if (job.getClass().equals(TvSerieLocalFanartAsyncJob.class)) {
					image = ((TvSerieLocalFanartAsyncJob)job).getImage();
					supportData = ((TvSerieLocalFanartAsyncJob)job).getFanart();
				}
				else if (job.getClass().equals(PersonAsyncJob.class)) {
					image = ((PersonAsyncJob)job).getImage();
					supportData = jobInfo.supportData;
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
		this.jobMap.put(job.getId(), new JobInfo(job.getId(), listener, job.getClass(), null));
		AsyncManager.getInstance().submit(job.getId(), job, this);
	}
	
	public void handle(CacheRemoteImageAsyncJob job, TvSerieImageLoaderListener listener, Object supportData) {
		this.jobMap.put(job.getId(), new JobInfo(job.getId(), listener, job.getClass(), supportData));
		AsyncManager.getInstance().submit(job.getId(), job, this);
	}
	
	public void handle(CacheRemoteImageAsyncJob job, TvSerieImageLoaderListener listener) {
		handle(job, listener, null);
	}
	
	public void handle(TvSerieLocalFanartAsyncJob job, TvSerieImageLoaderListener listener) {
		this.jobMap.put(job.getId(), new JobInfo(job.getId(), listener, job.getClass(), null));
		AsyncManager.getInstance().submit(job.getId(), job, this);
	}
	
	public void handle(PersonAsyncJob job, TvSerieImageLoaderListener listener, TvSerieActor actor) {
		this.jobMap.put(job.getId(), new JobInfo(job.getId(), listener, job.getClass(), actor));
		AsyncManager.getInstance().submit(job.getId(), job, this);
	}
	
	public void handle(PersonAsyncJob job, TvSerieImageLoaderListener listener, String supportData) {
		this.jobMap.put(job.getId(), new JobInfo(job.getId(), listener, job.getClass(), supportData));
		AsyncManager.getInstance().submit(job.getId(), job, this);
	}
	
	public void cancelAll() {
		List<JobInfo> jobs = new ArrayList<>(this.jobMap.values());
		this.jobMap.clear();
		for (JobInfo job : jobs) {
			if (job.jobClass.equals(TvSerieLocalSeasonImageAsyncJob.class)) {
				AsyncManager.getInstance().cancelTvSerieLocalSeasonImageAsyncJob(job.id);
			}
			else if (job.jobClass.equals(CacheRemoteImageAsyncJob.class)) {
				AsyncManager.getInstance().cancelTvSerieCacheRemoteImageAsyncJob(job.id);
			}
			else if (job.jobClass.equals(PersonAsyncJob.class)) {
				AsyncManager.getInstance().cancelPersonAsyncJob(job.id);
			}
		}
	}
 
	public static interface TvSerieImageLoaderListener {
		
		public void notifyImageLoaded(String id, Image image, Object supportData);
		
	}
	
	private static class JobInfo {
		
		private final String id;
		private final TvSerieImageLoaderListener listener;
		private final Class<? extends AsyncJob> jobClass;
		private final Object supportData;
		
		private JobInfo(String id, TvSerieImageLoaderListener listener, Class<? extends AsyncJob> jobClass, Object supportData) {
			this.id = id;
			this.listener = listener;
			this.jobClass = jobClass;
			this.supportData = supportData;
		}
		
	}
	
}
