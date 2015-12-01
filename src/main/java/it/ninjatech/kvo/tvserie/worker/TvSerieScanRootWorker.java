package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.tvserie.worker.TvSerieScanRootWorker.TvSerieScanRootWorkerInputData;
import it.ninjatech.kvo.util.Labels;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TvSerieScanRootWorker extends AbstractTvSerieWorker<TvSerieScanRootWorkerInputData, Map<TvSeriePathEntity, Boolean>> {

    public TvSerieScanRootWorker(TvSerieScanRootWorkerInputData input) {
        super(input);
    }

    @Override
    public Map<TvSeriePathEntity, Boolean> work() throws Exception {
        Map<TvSeriePathEntity, Boolean> result = new HashMap<>();

        this.progressNotifier.notifyWorkerMessage(Labels.tvSerieScanRootWorker(Labels.TV_SERIE_SCAN_ROOT_WORKER_1, this.input.entity.getLabel(), 0, 0));

        TvSerieWorkerTasks.scan(this.input.entity, this.progressNotifier);
        if (this.input.recursive) {
            Set<TvSeriePathEntity> tvSeriePathEntities = this.input.entity.getTvSeries();
            int current = 0;
            for (TvSeriePathEntity tvSeriePathEntity : tvSeriePathEntities) {
                this.progressNotifier.notifyWorkerMessage(Labels.tvSerieScanRootWorker(Labels.TV_SERIE_SCAN_ROOT_WORKER_2,
                                                                                       tvSeriePathEntity.getLabel(), ++current, tvSeriePathEntities.size()));
                this.progressNotifier.notifyTaskInit(null, 100);
                if (TvSerieWorkerTasks.check(new File(tvSeriePathEntity.getPath()), this.progressNotifier)) {
                    this.progressNotifier.notifyTaskUpdate(null, 10);
                    TvSerieWorkerTasks.scan(tvSeriePathEntity, this.progressNotifier);
                    this.progressNotifier.notifyTaskUpdate(null, 50);
                    if (tvSeriePathEntity.getTvSerie() != null) {
                        TvSerieWorkerTasks.delete(tvSeriePathEntity, this.progressNotifier);
                        this.progressNotifier.notifyTaskUpdate(null, 75);
                        TvSerieWorkerTasks.save(tvSeriePathEntity, this.progressNotifier);
                    }
                    result.put(tvSeriePathEntity, true);
                }
                else {
                    this.progressNotifier.notifyTaskUpdate(null, 10);
                    this.input.entity.removeTvSerie(tvSeriePathEntity);
                    this.progressNotifier.notifyTaskUpdate(null, 30);
                    if (tvSeriePathEntity.getTvSerie() != null) {
                        TvSerieWorkerTasks.delete(tvSeriePathEntity, this.progressNotifier);
                    }
                    result.put(tvSeriePathEntity, false);
                }
                this.progressNotifier.notifyTaskUpdate(null, 100);
            }
        }

        return result;
    }

    public static class TvSerieScanRootWorkerInputData {

        private final TvSeriesPathEntity entity;
        private final Boolean recursive;

        private TvSerieScanRootWorkerInputData(TvSeriesPathEntity entity, Boolean recursive) {
            this.entity = entity;
            this.recursive = recursive;
        }

    }

}