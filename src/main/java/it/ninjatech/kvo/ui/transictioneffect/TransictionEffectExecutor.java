package it.ninjatech.kvo.ui.transictioneffect;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransictionEffectExecutor {

	private static TransictionEffectExecutor self;
	
	public static void init() {
		if (self == null) {
			self = new TransictionEffectExecutor();
		}
	}
	
	public static TransictionEffectExecutor getInstance() {
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
	
	private TransictionEffectExecutor() {
		this.executor = Executors.newSingleThreadExecutor();
	}
	
	public void execute(TransictionEffect transictionEffect) {
		this.executor.execute(transictionEffect);
	}
	
}
