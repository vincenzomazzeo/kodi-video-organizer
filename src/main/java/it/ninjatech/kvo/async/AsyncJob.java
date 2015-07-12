package it.ninjatech.kvo.async;

import java.io.Serializable;

public abstract class AsyncJob implements Serializable {

	private static final long serialVersionUID = 749327021228311380L;

	protected abstract void execute();
	
}
