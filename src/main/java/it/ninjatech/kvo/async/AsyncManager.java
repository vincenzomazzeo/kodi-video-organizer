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
				self.executors.shutdownNow();
			}
			catch (Exception e) {}
			self = null;
		}
	}

	private final ExecutorService executors;
	private final AsyncHandler<TvSerieTileImagesAsyncJob> tvSerieTileHandler;
	
	private AsyncManager() {
		this.executors = Executors.newFixedThreadPool(1);
		
		this.tvSerieTileHandler = new AsyncHandler<>();
		
		this.executors.submit(this.tvSerieTileHandler);
	}
	
	public void submit(String id, TvSerieTileImagesAsyncJob job, AsyncJobListener<TvSerieTileImagesAsyncJob> listener) {
		this.tvSerieTileHandler.submitJob(id, job, listener);
	}
	
	public void cancelTvSerieTileImagesAsyncJob(String id) {
		this.tvSerieTileHandler.removeJob(id);
	}
	
}
