package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.worker.TvSerieRemoveWorker.TvSerieRemoveWorkerInputData;
import it.ninjatech.kvo.util.Labels;

import java.io.File;

import com.alee.utils.FileUtils;

public class TvSerieRemoveWorker extends AbstractTvSerieWorker<TvSerieRemoveWorkerInputData, Void> {

    public static TvSerieRemoveWorkerInputData makeInputData(TvSeriePathEntity tvSeriePathEntity, Boolean removeFromDisk) {
        return new TvSerieRemoveWorkerInputData(tvSeriePathEntity, removeFromDisk);
    }
    
	public TvSerieRemoveWorker(TvSerieRemoveWorkerInputData input) {
		super(input);
	}
	
	@Override
	public Void work() throws Exception {
	    this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerRemove(this.input.tvSeriePathEntity.getLabel()));
        
        this.progressNotifier.notifyTaskInit(null, 100);
	    if (this.input.tvSeriePathEntity.getTvSerie() != null) {
            TvSerieWorkerTasks.delete(this.input.tvSeriePathEntity, this.progressNotifier);
            this.progressNotifier.notifyTaskUpdate(null, 80);
        }
	    if (this.input.removeFromDisk) {
	        FileUtils.deleteFile(new File(this.input.tvSeriePathEntity.getPath()));
	        this.progressNotifier.notifyTaskUpdate(null, 90);
	    }
	    this.input.tvSeriePathEntity.getTvSeriesPathEntity().removeTvSerie(this.input.tvSeriePathEntity);
	    this.progressNotifier.notifyTaskUpdate(null, 100);
		
		return null;
	}
	
	public static class TvSerieRemoveWorkerInputData {
	
	    private final TvSeriePathEntity tvSeriePathEntity;
	    private final Boolean removeFromDisk;
	    
	    private TvSerieRemoveWorkerInputData(TvSeriePathEntity tvSeriePathEntity, Boolean removeFromDisk) {
	        this.tvSeriePathEntity = tvSeriePathEntity;
	        this.removeFromDisk = removeFromDisk;
	    }
	    
	}
	
}
