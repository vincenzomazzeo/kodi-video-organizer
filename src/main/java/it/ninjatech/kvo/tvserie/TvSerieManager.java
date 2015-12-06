package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.tvserie.worker.TvSerieAdaptPathToNameWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieAddRootWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieCheckRootWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieFetchWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieRemoveRootWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieRemoveWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieScanRootWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieScanWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieSearchWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieSeasonCreateWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieSeasonWorker;
import it.ninjatech.kvo.ui.progressdialogworker.DeterminateProgressDialogWorker;
import it.ninjatech.kvo.util.Labels;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TvSerieManager {

	private static TvSerieManager self;
	
	public static void init() {
		if (self == null) {
			self = new TvSerieManager();
		}
	}
	
	public static TvSerieManager getInstance() {
		return self;
	}
	
	private final Set<File> tvSeriesPathEntityRoots;
	
	private TvSerieManager() {
		this.tvSeriesPathEntityRoots = new HashSet<>();
	}
	
	public boolean hasTvSeriesPathEntityRoots() {
	    return !this.tvSeriesPathEntityRoots.isEmpty();
	}
	
	public void notifyTvSeriesPathEntities(Set<TvSeriesPathEntity> tvSeriesPathEntities) {
	    for (TvSeriesPathEntity tvSeriesPathEntity : tvSeriesPathEntities) {
	        this.tvSeriesPathEntityRoots.add(new File(tvSeriesPathEntity.getPath()));
	    }
	}
	
	public Map<String, List<TvSerie>> search(Map<String, EnhancedLocale> data) {
		Map<String, List<TvSerie>> result = null;
		
		TvSerieSearchWorker worker = new TvSerieSearchWorker(data);
		result = DeterminateProgressDialogWorker.show(worker, Labels.SEARCHING_FOR_TV_SERIES, true);
		
		return result;
	}
	
	public TvSeriesPathEntity addTvSeriesPathEntity(File root) {
		TvSeriesPathEntity result = null;
		
		if (root != null && !this.tvSeriesPathEntityRoots.contains(root)) {
			TvSerieAddRootWorker worker = new TvSerieAddRootWorker(root);
			
			result = DeterminateProgressDialogWorker.show(worker, Labels.getScanningRoot(Type.TvSerie.getPlural(), root.getName()), true);			
			
			if (result != null) {
				this.tvSeriesPathEntityRoots.add(root);
			}
		}
		
		return result;
	}
	
	public Boolean check(TvSeriesPathEntity tvSeriesPathEntity) {
	    Boolean result = null;
	    
	    TvSerieCheckRootWorker worker = new TvSerieCheckRootWorker(tvSeriesPathEntity);
	    result = DeterminateProgressDialogWorker.show(worker, "", true);
	    
	    return result;
	}
	
	public Map<Boolean, Set<TvSeriePathEntity>> scan(TvSeriesPathEntity tvSeriesPathEntity) {
	    Map<Boolean, Set<TvSeriePathEntity>> result = null;
	    
		TvSerieScanRootWorker worker = new TvSerieScanRootWorker(TvSerieScanRootWorker.makeInputData(tvSeriesPathEntity, false));
		result = DeterminateProgressDialogWorker.show(worker, "", true);
		
		return result;
	}
	
	public Map<Boolean, Set<TvSeriePathEntity>> scanRecursive(TvSeriesPathEntity tvSeriesPathEntity) {
		Map<Boolean, Set<TvSeriePathEntity>> result = null;
		
		TvSerieScanRootWorker worker = new TvSerieScanRootWorker(TvSerieScanRootWorker.makeInputData(tvSeriesPathEntity, true));
		result = DeterminateProgressDialogWorker.show(worker, "", true);
		
		return result;
	}
	
	public void remove(TvSeriesPathEntity tvSeriesPathEntity, Boolean removeFromDisk) {
		TvSerieRemoveRootWorker worker = new TvSerieRemoveRootWorker(TvSerieRemoveRootWorker.makeInputData(tvSeriesPathEntity, removeFromDisk));
		this.tvSeriesPathEntityRoots.remove(new File(tvSeriesPathEntity.getPath()));
		DeterminateProgressDialogWorker.show(worker, "", true);
	}
	
	public Boolean scan(TvSeriePathEntity tvSeriePathEntity) {
		Boolean result = null;
		
		TvSerieScanWorker worker = new TvSerieScanWorker(tvSeriePathEntity); 
		result = DeterminateProgressDialogWorker.show(worker, "", true);
		
		return result;
	}
	
	public List<Boolean> fetch(List<TvSeriePathEntity> tvSeriePathEntities) {
		List<Boolean> result = null;
		
		TvSerieFetchWorker worker = new TvSerieFetchWorker(tvSeriePathEntities);
		result = DeterminateProgressDialogWorker.show(worker, "", true);
		
		return result;
	}
	
	public Boolean adaptPathToName(TvSeriePathEntity tvSeriePathEntity) {
	    Boolean result = null;
	    
	    TvSerieAdaptPathToNameWorker worker = new TvSerieAdaptPathToNameWorker(tvSeriePathEntity);
	    result = DeterminateProgressDialogWorker.show(worker, "", true);
	    
	    return result;
	}
	 
	public void remove(TvSeriePathEntity tvSeriePathEntity, Boolean removeFromDisk) {
		TvSerieRemoveWorker worker = new TvSerieRemoveWorker(TvSerieRemoveWorker.makeInputData(tvSeriePathEntity, removeFromDisk));
		DeterminateProgressDialogWorker.show(worker, "", true);
	}
	
	public Boolean handleSeason(TvSerieSeason season,
                                Map<TvSerieEpisode, String> videoEpisodeMap,
                                Map<TvSerieEpisode, Map<String, EnhancedLocale>> subtitleEpisodeMap,
                                File seasonImage) {
	    Boolean result = null;
	    
	    TvSerieSeasonWorker worker = new TvSerieSeasonWorker(TvSerieSeasonWorker.makeInputData(season, 
	                                                                                           videoEpisodeMap, 
	                                                                                           subtitleEpisodeMap, 
	                                                                                           seasonImage));
	    result = DeterminateProgressDialogWorker.show(worker, "", true);
	    
	    return result;
	}
	
	public Boolean createSeason(TvSerieSeason season) {
	    Boolean result = null;
	    
	    TvSerieSeasonCreateWorker worker = new TvSerieSeasonCreateWorker(season);
	    result = DeterminateProgressDialogWorker.show(worker, "", true);
	    
	    return result;
	}
	
}
