package it.ninjatech.kvo.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LongTaskExecutor {

	private static LongTaskExecutor self;
	
	public static LongTaskExecutor getInstance() {
		return self == null ? self = new LongTaskExecutor() : self;
	}
	
	private final Executor executor;
	
	private LongTaskExecutor() {
		this.executor = Executors.newSingleThreadExecutor();
	}
	
	public void execute(Runnable runnable) {
		this.executor.execute(runnable);
	}
	
}
