package it.ninjatech.kvo.ui.explorer;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsModel;
import it.ninjatech.kvo.ui.explorer.tvserie.ExplorerTvSerieController;
import it.ninjatech.kvo.ui.wall.WallController;

import java.util.List;


public class ExplorerController {

	private final ExplorerRootsController rootsController;
	private final ExplorerTvSerieController tvSerieController;
	private final ExplorerView view;
	
	public ExplorerController(List<TvSeriesPathEntity> tvSeriesPathEntities, WallController wallController) {
		this.rootsController = new ExplorerRootsController(new ExplorerRootsModel(), this, tvSeriesPathEntities);
		this.tvSerieController = new ExplorerTvSerieController(wallController);
		this.view = new ExplorerView(this);
		
		this.view.addRootsViewTab(this.rootsController.getView());
	}
	
	public ExplorerView getView() {
		return this.view;
	}
	
	public void addTvSerieTab() {
		this.view.addTvSerieTab(this.tvSerieController.getView());
	}
	
	public void removeTvSerieTab() {
		this.view.removeTvSerieTab();
	}
	
	public void addTvSerieTile(TvSeriePathEntity tvSeriePathEntity) {
		this.tvSerieController.addTile(tvSeriePathEntity);
	}
	
}
