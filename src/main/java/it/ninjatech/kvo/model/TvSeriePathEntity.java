package it.ninjatech.kvo.model;

import java.io.File;

public class TvSeriePathEntity extends AbstractPathEntity {

	private final TvSeriesPathEntity tvSeriesPathEntity;
	private TvSerie tvSerie;
	
	protected TvSeriePathEntity(String id, String path, String label, TvSeriesPathEntity tvSeriesPathEntity) {
		super(id, path, label);
		
		this.tvSeriesPathEntity = tvSeriesPathEntity;
		this.tvSerie = null;
	}

	protected TvSeriePathEntity(File file, TvSeriesPathEntity tvSeriesPathEntity) {
		super(file);
		
		this.tvSeriesPathEntity = tvSeriesPathEntity;
		this.tvSerie = null;
	}

	public TvSeriesPathEntity getTvSeriesPathEntity() {
		return this.tvSeriesPathEntity;
	}

	public TvSerie getTvSerie() {
		return this.tvSerie;
	}

	public void setTvSerie(TvSerie tvSerie) {
		this.tvSerie = tvSerie;
	}

}
