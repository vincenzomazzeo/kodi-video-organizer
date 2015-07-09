package it.ninjatech.kvo.async;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncManager {

	private static AsyncManager self;
	
	public static void init() {
		if (self != null) {
			self = new AsyncManager();
		}
	}
	
	public static AsyncManager getInstance() {
		return self;
	}
	
	public static void shutdown() {
		if (self != null) {
			try {
				self.tvSerieTileExecutorService.shutdownNow();
			}
			catch (Exception e) {}
			self = null;
		}
	}
	
	private final ExecutorService tvSerieTileExecutorService;
	private final Set<String> tvSerieTileJobIds;
	private final Deque<TvSerieTileImagesAsyncJob> tvSerieTileJobs;
	
	private AsyncManager() {
		this.tvSerieTileExecutorService = Executors.newSingleThreadExecutor();
		this.tvSerieTileJobIds = new HashSet<>();
		this.tvSerieTileJobs = new ArrayDeque<>();
	}
	
	public void submit(String id, TvSerieTileImagesAsyncJob job) {
		this.tvSerieTileJobIds.add(id);
		this.tvSerieTileJobs.offer(job);
	}
	
	public void cancelTvSerieTileImagesAsyncJob(String id) {
		this.tvSerieTileJobIds.remove(id);
	}
	
}
