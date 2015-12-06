package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.Utils;

import java.io.File;

public class TvSerieAdaptPathToNameWorker extends AbstractTvSerieWorker<TvSeriePathEntity, Boolean> {

	public TvSerieAdaptPathToNameWorker(TvSeriePathEntity input) {
		super(input);
	}

	@Override
	public Boolean work() throws Exception {
		Boolean result = false;
		
		this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerAdaptPathToName(this.input.getLabel()));
		
		if (TvSerieWorkerTasks.check(new File(this.input.getPath()), this.progressNotifier)) {
		    TvSerie tvSerie = this.input.getTvSerie();
		    this.progressNotifier.notifyTaskInit(null, 100);
		    
		    String normalizedName = Utils.normalizeName(tvSerie.getName());
		    
		    File directory = new File(this.input.getPath());
		    File toDirectory = new File(directory.getParentFile(), normalizedName);
		    directory.renameTo(toDirectory);
		    directory = toDirectory;
		    this.input.setPath(toDirectory.getAbsolutePath());
		    this.progressNotifier.notifyTaskUpdate(null, 30);
		    
			TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
			this.progressNotifier.notifyTaskUpdate(null, 60);
			TvSerieWorkerTasks.save(this.input, this.progressNotifier);
			this.progressNotifier.notifyTaskUpdate(null, 100);
			result = true;
		}
		else {
		    this.progressNotifier.notifyTaskInit(null, 100);
		    this.progressNotifier.notifyTaskUpdate(null, 10);
		    TvSerieWorkerTasks.delete(this.input, this.progressNotifier);
            this.progressNotifier.notifyTaskUpdate(null, 90);
		    this.input.getTvSeriesPathEntity().removeTvSerie(this.input);
		    this.progressNotifier.notifyTaskUpdate(null, 100);
		}
		
		return result;
	}

}
