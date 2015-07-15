package it.ninjatech.kvo.async;

import java.io.Serializable;

public abstract class AsyncJob implements Serializable {

	private static final long serialVersionUID = 749327021228311380L;

	protected Exception exception;
	
	protected abstract void execute();
	
	public boolean hasException() {
		return this.exception != null;
	}
	
	public Exception getException() {
		return this.exception;
	}
	
}
