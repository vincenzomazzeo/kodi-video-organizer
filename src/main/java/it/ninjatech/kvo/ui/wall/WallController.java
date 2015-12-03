package it.ninjatech.kvo.ui.wall;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.explorer.ExplorerController;
import it.ninjatech.kvo.ui.tvserie.TvSerieController;

public class WallController {

    private final WallView view;
    private final TvSerieController tvSerieController;
    private ExplorerController explorerController;
    
    public WallController() {
        this.view = new WallView(this);
        this.tvSerieController = new TvSerieController(this);
    }
    
    public WallView getView() {
        return this.view;
    }

    public void setExplorerController(ExplorerController explorerController) {
        this.explorerController = explorerController;
    }
    
    public void showTvSerie(TvSeriePathEntity tvSeriePathEntity) {
        this.view.showTvSerie(this.tvSerieController.getView());
        this.tvSerieController.showTvSerie(tvSeriePathEntity.getTvSerie());
    }
    
    public void notifyTvSerieNodeStructureChanged(TvSeriePathEntity tvSeriePathEntity) {
        this.explorerController.notifyTvSerieNodeStructureChanged(tvSeriePathEntity);
    }
    
}
