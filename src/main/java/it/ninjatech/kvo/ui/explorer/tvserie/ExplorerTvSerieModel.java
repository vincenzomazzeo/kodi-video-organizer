package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.tvserie.TvSeriePathEntity;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ExplorerTvSerieModel {

	private final Set<TvSeriePathEntity> data;
	private ExplorerTvSerieView view;
	
	protected ExplorerTvSerieModel() {
		this.data = new TreeSet<>(new TvSeriePathEntityComparator());
	}
	
	protected void setView(ExplorerTvSerieView view) {
		this.view = view;
	}
	
	protected void addTile(TvSeriePathEntity tvSeriePathEntity) {
		this.data.add(tvSeriePathEntity);
		
		this.view.fireModelUpdate();
	}
	
	protected void addTile(List<TvSeriePathEntity> tvSeriePathEntities) {
		this.data.addAll(tvSeriePathEntities);
		
		this.view.fireModelUpdate();
	}
	
	protected Set<TvSeriePathEntity> getData() {
		return this.data;
	}
	
	private static class TvSeriePathEntityComparator implements Comparator<TvSeriePathEntity> {

		@Override
		public int compare(TvSeriePathEntity e1, TvSeriePathEntity e2) {
			return e1.getTvSerie().getName().compareTo(e2.getTvSerie().getName());
		}
		
	}
	
}
