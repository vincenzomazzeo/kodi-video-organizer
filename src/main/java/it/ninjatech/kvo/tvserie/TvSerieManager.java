package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.tvserie.worker.TvSerieAddRootWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieFetchAllWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieFetchWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieRemoveRootWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieRemoveWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieScanAllWorker;
import it.ninjatech.kvo.tvserie.worker.TvSerieScanOnlyRootWorker;
import it.ninjatech.kvo.ui.progressdialogworker.DeterminateProgressDialogWorker;
import it.ninjatech.kvo.util.Labels;

import java.io.File;
import java.util.HashSet;
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
	
	private final Set<File> tvSeriesPathEntitieRoots;
	
	private TvSerieManager() {
		this.tvSeriesPathEntitieRoots = new HashSet<>();
	}
	
	public TvSeriesPathEntity addTvSeriesPathEntity(File root) {
		TvSeriesPathEntity result = null;
		
		if (root != null && !this.tvSeriesPathEntitieRoots.contains(root)) {
			TvSerieAddRootWorker worker = new TvSerieAddRootWorker(root);
			
			result = DeterminateProgressDialogWorker.show(worker, Labels.getScanningRoot(Type.TvSerie.getPlural(), root.getName()), true);			
			
			if (result != null) {
				this.tvSeriesPathEntitieRoots.add(root);
			}
		}
		
		return result;
	}
	
	public Boolean scan(TvSeriesPathEntity tvSeriesPathEntity) {
		Boolean result = null;
		
		TvSerieScanOnlyRootWorker worker = new TvSerieScanOnlyRootWorker(tvSeriesPathEntity);
		result = DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
		
		return result;
	}
	
	public Boolean scanRecursive(TvSeriesPathEntity tvSeriesPathEntity) {
		Boolean result = null;
		
		TvSerieScanAllWorker worker = new TvSerieScanAllWorker(tvSeriesPathEntity);
		result = DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
		
		return result;
	}
	
	public Boolean fetch(TvSeriesPathEntity tvSeriesPathEntity) {
		Boolean result = null;
		
		TvSerieFetchAllWorker worker = new TvSerieFetchAllWorker(tvSeriesPathEntity);
		result = DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
		
		return result;
	}
	
	public void remove(TvSeriesPathEntity tvSeriesPathEntity) {
		TvSerieRemoveRootWorker worker = new TvSerieRemoveRootWorker(tvSeriesPathEntity);
		DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
	}
	
	public Boolean scan(TvSeriePathEntity tvSeriePathEntity) {
		Boolean result = null;
		
		TvSerieFetchWorker worker = new TvSerieFetchWorker(tvSeriePathEntity);
		result = DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
		
		return result;
	}
	
	public Boolean fetch(TvSeriePathEntity tvSeriePathEntity) {
		Boolean result = null;
		
		TvSerieFetchWorker worker = new TvSerieFetchWorker(tvSeriePathEntity);
		result = DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
		
		return result;
	}
	
	public void remove(TvSeriePathEntity tvSeriePathEntity) {
		TvSerieRemoveWorker worker = new TvSerieRemoveWorker(tvSeriePathEntity);
		DeterminateProgressDialogWorker.show(worker, ""/* TODO message */, true);
	}
	
}
