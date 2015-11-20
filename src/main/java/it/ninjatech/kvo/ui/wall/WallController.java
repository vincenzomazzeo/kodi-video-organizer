package it.ninjatech.kvo.ui.wall;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.tvserie.TvSerieController;

public class WallController {

    private final WallView view;
    private final TvSerieController tvSerieController;
    
    public WallController() {
        this.view = new WallView(this);
        this.tvSerieController = new TvSerieController();
    }
    
    public WallView getView() {
        return this.view;
    }

    public void showTvSerie(TvSeriePathEntity tvSeriePathEntity) {
        this.view.showTvSerie(this.tvSerieController.getView());
        this.tvSerieController.showTvSerie(tvSeriePathEntity.getTvSerie());
    }
    
}
