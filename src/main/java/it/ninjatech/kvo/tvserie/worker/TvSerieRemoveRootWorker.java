package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.tvserie.worker.TvSerieRemoveRootWorker.TvSerieRemoveRootWorkerInputData;
import it.ninjatech.kvo.util.Labels;

import java.io.File;

import com.alee.utils.FileUtils;

public class TvSerieRemoveRootWorker extends AbstractTvSerieWorker<TvSerieRemoveRootWorkerInputData, Void> {

    public static TvSerieRemoveRootWorkerInputData makeInputData(TvSeriesPathEntity tvSeriesPathEntity, Boolean removeFromDisk) {
        return new TvSerieRemoveRootWorkerInputData(tvSeriesPathEntity, removeFromDisk);
    }

    public TvSerieRemoveRootWorker(TvSerieRemoveRootWorkerInputData input) {
        super(input);
    }

    @Override
    public Void work() throws Exception {
        this.progressNotifier.notifyWorkerMessage(Labels.tvSeriesWorkerRemove(this.input.tvSeriesPathEntity.getLabel()));

        this.progressNotifier.notifyTaskInit(null, 100);
        TvSerieWorkerTasks.delete(this.input.tvSeriesPathEntity, this.progressNotifier);
        this.progressNotifier.notifyTaskUpdate(null, 80);
        if (this.input.removeFromDisk) {
            FileUtils.deleteFile(new File(this.input.tvSeriesPathEntity.getPath()));
        }
        this.progressNotifier.notifyTaskUpdate(null, 100);

        return null;
    }

    public static class TvSerieRemoveRootWorkerInputData {

        private final TvSeriesPathEntity tvSeriesPathEntity;
        private final Boolean removeFromDisk;

        private TvSerieRemoveRootWorkerInputData(TvSeriesPathEntity tvSeriesPathEntity, Boolean removeFromDisk) {
            this.tvSeriesPathEntity = tvSeriesPathEntity;
            this.removeFromDisk = removeFromDisk;
        }

    }

}
