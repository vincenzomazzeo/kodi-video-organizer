package it.ninjatech.kvo.tvserie.worker;

import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.util.Labels;

import java.io.File;

public class TvSerieAddWorker extends AbstractTvSerieWorker<TvSeriePathEntity, Boolean> {

    public TvSerieAddWorker(TvSeriePathEntity input) {
        super(input);
    }

    @Override
    public Boolean work() throws Exception {
        Boolean result = false;

        this.progressNotifier.notifyWorkerMessage(Labels.tvSerieWorkerAdd(TvSerieHelper.getTitle(this.input)));
        this.progressNotifier.notifyTaskInit(null, 100);

        File path = new File(this.input.getTvSeriesPathEntity().getPath(), TvSerieHelper.getFsName(this.input.getTvSerie()));
        if (!path.exists() && path.mkdir()) {
            this.input.setPath(path.getAbsolutePath());
            this.input.setLabel(path.getName());
            if (this.input.getTvSeriesPathEntity().addTvSerie(this.input)) {
                this.progressNotifier.notifyTaskUpdate(null, 10);
                TvSerieWorkerTasks.fetch(this.input, this.progressNotifier);
                this.progressNotifier.notifyTaskUpdate(null, 50);
                TvSerieWorkerTasks.save(this.input, this.progressNotifier);
                result = true;
            }
        }

        this.progressNotifier.notifyTaskUpdate(null, 100);

        return result;
    }

}
