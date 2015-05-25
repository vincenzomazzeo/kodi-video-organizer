package it.ninjatech.kvo.model;

import java.io.File;

public class TvSeriePathEntity extends AbstractPathEntity {

	private final TvSeriesPathEntity tvSeriesPathEntity;
	
	protected TvSeriePathEntity(String id, String path, String label, TvSeriesPathEntity tvSeriesPathEntity) {
		super(id, path, label);
		
		this.tvSeriesPathEntity = tvSeriesPathEntity;
	}

	protected TvSeriePathEntity(File file, TvSeriesPathEntity tvSeriesPathEntity) {
		super(file);
		
		this.tvSeriesPathEntity = tvSeriesPathEntity; 
	}

	public TvSeriesPathEntity getTvSeriesPathEntity() {
		return this.tvSeriesPathEntity;
	}

}
