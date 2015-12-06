package it.ninjatech.kvo.tvserie.model;

import it.ninjatech.kvo.model.AbstractPathEntity;

import java.io.File;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class TvSeriesPathEntity extends AbstractPathEntity {

	private final SortedSet<TvSeriePathEntity> tvSeries;
	
	public TvSeriesPathEntity(String id, String path, String label) {
		super(id, path, label);
		
		this.tvSeries = new TreeSet<>();
	}
	
	public TvSeriesPathEntity(File file) {
		super(file);
		
		this.tvSeries = new TreeSet<>();
	}
	
	@Override
    public String toString() {
        return String.format("[%s] %s - %s", getId(), getPath(), getLabel());
    }

    public TvSeriePathEntity addTvSerie(File path) {
		TvSeriePathEntity result = new TvSeriePathEntity(path, this);
		
		if (!this.tvSeries.contains(result)) {
			this.tvSeries.add(result);
		}
		else {
			result = null;
		}
		
		return result;
	}
    
    public TvSeriePathEntity getTemporaryTvSerie(String tvSerie) {
        return new TvSeriePathEntity(new File(this.getPath(), tvSerie), this);
    }
    
    public boolean addTvSerie(TvSeriePathEntity tvSeriePathEntity) {
        boolean result = false;
        
        if (!this.tvSeries.contains(tvSeriePathEntity)) {
            this.tvSeries.add(tvSeriePathEntity);
            result = true;
        }
        
        return result;
    }

	public Set<TvSeriePathEntity> getTvSeries() {
		return Collections.unmodifiableSortedSet(this.tvSeries);
	}
	
	public void removeTvSerie(TvSeriePathEntity tvSerie) {
		this.tvSeries.remove(tvSerie);
	}
	
	public void removeTvSeries(Set<TvSeriePathEntity> tvSeries) {
	    this.tvSeries.removeAll(tvSeries);
	}

}
