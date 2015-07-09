package it.ninjatech.kvo.async;

public abstract class AsyncJob implements Runnable {

	protected abstract void work();
	
	@Override
	public void run() {
		work();
	}

}
