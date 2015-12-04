package it.ninjatech.kvo.tvserie.worker;

import java.io.File;

import it.ninjatech.kvo.model.FsElement;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.util.Labels;

public class TvSerieSeasonCreateWorker extends AbstractTvSerieWorker<TvSerieSeason, Boolean> {

    public TvSerieSeasonCreateWorker(TvSerieSeason input) {
        super(input);
    }

    @Override
    public Boolean work() throws Exception {
        Boolean result = false;

        this.progressNotifier.notifyWorkerMessage(Labels.tvSerieSeasonCreateWorker(this.input.getTvSerie().getName(),
                                                                                   this.input.getNumber()));
        this.progressNotifier.notifyTaskInit(null, 100);
        File seasonPath = TvSerieHelper.getLocalSeasonPath(this.input);
        result = seasonPath.mkdir();
        this.input.getTvSerie().getTvSeriePathEntity().addFsElement(new FsElement(seasonPath.getName(), true));
        this.progressNotifier.notifyTaskUpdate(null, 100);

        // prova
        
        return result;
    }

}
