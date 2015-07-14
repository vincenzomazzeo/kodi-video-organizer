package it.ninjatech.kvo.async;

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
	private final AsyncHandler<TvSerieTileImagesAsyncJob> tvSerieTileHandler;
	
	private AsyncManager() {
		this.executor = Executors.newFixedThreadPool(1);
		
		this.tvSerieTileHandler = new AsyncHandler<>();
		
		this.executor.submit(this.tvSerieTileHandler);
	}
	
	public void submit(String id, TvSerieTileImagesAsyncJob job, AsyncJobListener<TvSerieTileImagesAsyncJob> listener) {
		System.out.printf("-> submit %s\n", id);
		this.tvSerieTileHandler.submitJob(id, job, listener);
	}
	
	public void cancelTvSerieTileImagesAsyncJob(String id) {
		System.out.printf("-> cancel %s\n", id);
		this.tvSerieTileHandler.removeJob(id);
	}
	
}
