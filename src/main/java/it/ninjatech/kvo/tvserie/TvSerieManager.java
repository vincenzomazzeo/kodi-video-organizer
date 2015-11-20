package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.tvserie.worker.TvSerieAddRootWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieFetchAllWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieFetchWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieScanWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieSearchWorker;
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
	
//	public Boolean scan(TvSeriesPathEntity tvSeriesPathEntity) {
//		Boolean result = null;
//		
//		TvSerieScanOnlyRootWorker worker = new TvSerieScanOnlyRootWorker(tvSeriesPathEntity);
//		result = DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
//		
//		return result;
//	}
	
//	public Boolean scanRecursive(TvSeriesPathEntity tvSeriesPathEntity) {
//		Boolean result = null;
//		
//		TvSerieScanAllWorker worker = new TvSerieScanAllWorker(tvSeriesPathEntity);
//		result = DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
//		
//		return result;
//	}
	
	public Map<TvSeriePathEntity, Boolean> fetch(TvSeriesPathEntity tvSeriesPathEntity) {
	    Map<TvSeriePathEntity, Boolean> result = null;
		
		TvSerieFetchAllWorker worker = new TvSerieFetchAllWorker(tvSeriesPathEntity);
		result = DeterminateProgressDialogWorker.show(worker, "", true);
		
		return result;
	}
	
//	public void remove(TvSeriesPathEntity tvSeriesPathEntity) {
//		TvSerieRemoveRootWorker worker = new TvSerieRemoveRootWorker(tvSeriesPathEntity);
//		DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
//	}
	
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
	
//	public void remove(TvSeriePathEntity tvSeriePathEntity) {
//		TvSerieRemoveWorker worker = new TvSerieRemoveWorker(tvSeriePathEntity);
//		DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
//	}
	
}
