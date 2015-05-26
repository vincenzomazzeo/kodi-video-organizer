package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.model.AbstractPathEntity;

public abstract class AbstractPathBuilder extends AbstractWorker {

	protected AbstractPathBuilder() {
		super();
	}
	
	public abstract AbstractPathEntity build();
	
}
