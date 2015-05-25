package it.ninjatech.kvo.model;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class TvSeriesPathEntity extends AbstractPathEntity {

	private final Set<TvSeriePathEntity> tvSeries;
	
	public TvSeriesPathEntity(String id, String path, String label) {
		super(id, path, label);
		
		this.tvSeries = new HashSet<>();
	}
	
	public TvSeriesPathEntity(File file) {
		super(file);
		
		this.tvSeries = new HashSet<>();
	}
	
	public TvSeriePathEntity addTvSerie(File path) {
		TvSeriePathEntity result = new TvSeriePathEntity(path, this);
		
		this.tvSeries.add(result);
		
		return result;
	}

}
