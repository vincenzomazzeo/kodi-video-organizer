package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.model.AbstractPathEntity;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TvSeriesPathEntity extends AbstractPathEntity {

	private final Set<TvSeriePathEntity> tvSeries;
	
	protected TvSeriesPathEntity(String id, String path, String label) {
		super(id, path, label);
		
		this.tvSeries = new HashSet<>();
	}
	
	protected TvSeriesPathEntity(File file) {
		super(file);
		
		this.tvSeries = new HashSet<>();
	}
	
	protected TvSeriePathEntity addTvSerie(File path) {
		TvSeriePathEntity result = new TvSeriePathEntity(path, this);
		
		this.tvSeries.add(result);
		
		return result;
	}

	// TODO capire se si può rendere protected
	public Set<TvSeriePathEntity> getTvSeries() {
		return Collections.unmodifiableSet(this.tvSeries);
	}

}
