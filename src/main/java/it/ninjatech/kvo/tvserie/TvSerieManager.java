package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.model.Type;
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
	
	// Worker da fare:
	// 1. fetch di una lista di tvserie
	// 2. scan di una lista di tvserie
	// 3. update di una lista di tvserie
	// 4. salvataggio nel db di una lista di tvserie
	
	public void fetch(TvSeriesPathEntity tvSeriesPathEntity) {
		// Fa la fetch di tutti i tvSeriePathEntity che hanno un tvSerie
	}
	
	public void scan(TvSeriesPathEntity tvSeriesPathEntity) {
		// Fa lo scan di tutti i tvSeriePathEntity che hanno un tvSerie
	}
	
	public void fetch(TvSeriePathEntity tvSeriePathEntity) {
		// Controlla che il path esista
		// L'operazione di fetcher legge i dati dai provider usando un nuovo oggetto tv serie
		// Scan
		// Aggiorna l'oggetto presente in tvSeriePathEntity
		// Aggiorna i dati nel db
	}
	
	public void scan(TvSeriePathEntity tvSeriePathEntity) {
		// Controlla che il path esista
		// L'operazione di scan legge i file dal FS
		// Aggiorna l'oggetto presente in tvSeriePathEntity
		// Aggiorna i dati nel db
	}
	
}
