package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.model.AbstractPathEntity;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class TvSeriesPathEntity extends AbstractPathEntity {

	private final SortedSet<TvSeriePathEntity> tvSeries;
	
	protected TvSeriesPathEntity(String id, String path, String label) {
		super(id, path, label);
		
		this.tvSeries = new TreeSet<>();
	}
	
	protected TvSeriesPathEntity(File file) {
		super(file);
		
		this.tvSeries = new TreeSet<>();
	}
	
	protected void addTvSerie(File path) {
		TvSeriePathEntity entity = new TvSeriePathEntity(path, this);
		
		if (!this.tvSeries.contains(entity)) {
			this.tvSeries.add(entity);
		}
	}

	// TODO capire se si può rendere protected
	public Set<TvSeriePathEntity> getTvSeries() {
		return Collections.unmodifiableSortedSet(this.tvSeries);
	}

}
