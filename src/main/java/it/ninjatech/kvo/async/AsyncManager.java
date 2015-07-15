package it.ninjatech.kvo.async;

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
	private final AsyncHandler<TvSerieTileImagesAsyncJob> tvSerieTileImagesHandler;
	private final AsyncHandler<TvSerieLocalFanartAsyncJob> tvSerieLocalFanartHandler;
	
	private AsyncManager() {
		this.executor = Executors.newFixedThreadPool(2);
		
		this.tvSerieTileImagesHandler = new AsyncHandler<>();
		this.tvSerieLocalFanartHandler = new AsyncHandler<>();
		
		this.executor.submit(this.tvSerieTileImagesHandler);
		this.executor.submit(this.tvSerieLocalFanartHandler);
	}
	
	public void submit(String id, TvSerieTileImagesAsyncJob job, AsyncJobListener<TvSerieTileImagesAsyncJob> listener) {
		System.out.printf("-> submit %s\n", id);
		this.tvSerieTileImagesHandler.submitJob(id, job, listener);
	}
	
	public void cancelTvSerieTileImagesAsyncJob(String id) {
		System.out.printf("-> cancel %s\n", id);
		this.tvSerieTileImagesHandler.removeJob(id);
	}
	
	public void submit(String id, TvSerieLocalFanartAsyncJob job, AsyncJobListener<TvSerieLocalFanartAsyncJob> listener) {
		System.out.printf("-> submit %s\n", id);
		this.tvSerieLocalFanartHandler.submitJob(id, job, listener);
	}
	
	public void cancelTvSerieLocalFanartAsyncJob(String id) {
		System.out.printf("-> cancel %s\n", id);
		this.tvSerieLocalFanartHandler.removeJob(id);
	}
	
}
