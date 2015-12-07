package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.util.Labels;

public class TvSerieSeasonScanWorker extends AbstractTvSerieWorker<TvSerieSeason, Boolean> {

    public TvSerieSeasonScanWorker(TvSerieSeason input) {
        super(input);
    }

    @Override
    public Boolean work() throws Exception {
        Boolean result = true;

        this.progressNotifier.notifyWorkerMessage(Labels.tvSerieSeasonWorkerScan(this.input.getTvSerie().getName(), this.input.getNumber()));

        this.progressNotifier.notifyTaskInit(null, 100);
        TvSerieWorkerTasks.scan(this.input, this.progressNotifier);
        this.progressNotifier.notifyTaskUpdate(null, 60);
        TvSerieWorkerTasks.delete(this.input.getTvSerie().getTvSeriePathEntity(), this.progressNotifier);
        this.progressNotifier.notifyTaskUpdate(null, 80);
        TvSerieWorkerTasks.save(this.input.getTvSerie().getTvSeriePathEntity(), this.progressNotifier);
        this.progressNotifier.notifyTaskUpdate(null, 100);

        return result;
    }

}
