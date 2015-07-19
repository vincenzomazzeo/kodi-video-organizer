package it.ninjatech.kvo.async;

import it.ninjatech.kvo.async.job.AbstractTvSerieImageLoaderAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieCacheRemoteImageAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieLocalFanartAsyncJob;
import it.ninjatech.kvo.async.job.TvSerieTileImagesAsyncJob;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncManager {

	private static AsyncManager self;
	
	public static void init() {
		if (self == null) {
			self = new AsyncManager();
		}
	}
	
	public static AsyncManager getInstance() {
		return self;
	}
	
	public static void shutdown() {
		if (self != null) {
			try {
				self.executor.shutdownNow();
			}
			catch (Exception e) {}
			self = null;
		}
	}

	private final ExecutorService executor;
	private final AsyncHandler<AbstractTvSerieImageLoaderAsyncJob> tvSerieImageLoaderHandler;
	
	private AsyncManager() {
		this.executor = Executors.newFixedThreadPool(1);
		
		this.tvSerieImageLoaderHandler = new AsyncHandler<>();
		
		this.executor.submit(this.tvSerieImageLoaderHandler);
	}
	
	public void submit(String id, TvSerieTileImagesAsyncJob job, AsyncJobListener listener) {
		System.out.printf("-> submit tile images %s\n", id);
		this.tvSerieImageLoaderHandler.submitJob(id, job, listener);
	}
	
	public void cancelTvSerieTileImagesAsyncJob(String id) {
		System.out.printf("-> cancel tile images %s\n", id);
		this.tvSerieImageLoaderHandler.removeJob(id);
	}
	
	public void submit(String id, TvSerieLocalFanartAsyncJob job, AsyncJobListener listener) {
		System.out.printf("-> submit local fanart %s\n", id);
		this.tvSerieImageLoaderHandler.submitJob(id, job, listener);
	}
	
	public void cancelTvSerieLocalFanartAsyncJob(String id) {
		System.out.printf("-> cancel local fanart %s\n", id);
		this.tvSerieImageLoaderHandler.removeJob(id);
	}
	
	public void submit(String id, TvSerieCacheRemoteImageAsyncJob job, AsyncJobListener listener) {
		System.out.printf("-> submit actors %s\n", id);
		this.tvSerieImageLoaderHandler.submitJob(id, job, listener);
	}
	
	public void cancelTvSerieActorsAsyncJob(String id) {
		System.out.printf("-> cancel actors %s\n", id);
		this.tvSerieImageLoaderHandler.removeJob(id);
	}
	
}
