package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.model.TvSerieActor;

import java.awt.Dimension;

public class TvSerieActorImageAsyncJob extends TvSerieCacheRemoteImageAsyncJob {

	private static final long serialVersionUID = -4778866702647242373L;

	private final TvSerieActor actor;
	
	public TvSerieActorImageAsyncJob(TvSerieActor actor, Dimension size) {
		super(actor.getId(), actor.getPath(), size);
		
		this.actor = actor;
	}
	
	public TvSerieActor getActor() {
		return this.actor;
	}
	
}
