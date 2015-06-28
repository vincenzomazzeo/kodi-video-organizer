package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.TvSeriesExplorerRootsContextMenu;

import java.util.Collections;

import javax.swing.Icon;

public class TvSeriesExplorerRootsTreeNode extends AbstractRootsExplorerRootsTreeNode<TvSeriesPathEntity> {

	private static final long serialVersionUID = 6332400580163063140L;

	protected TvSeriesExplorerRootsTreeNode(TvSeriesPathEntity value, RootsExplorerRootsTreeNode parent) {
		super(value, parent);
		
		for (TvSeriePathEntity tvSerie : value.getTvSeries()) {
			this.children.add(new TvSerieExplorerRootsTreeNode(tvSerie, this));
		}
		Collections.sort(this.children);
	}
	
	@Override
	public boolean equals(Object other) {
		boolean result = false;

		result = other.getClass().equals(TvSeriesExplorerRootsTreeNode.class) && this.value.getLabel().equals(((TvSeriesExplorerRootsTreeNode)other).value.getLabel());
		
		return result;
	}
	
	@Override
	public Icon getCustomIcon() {
		return ImageRetriever.retrieveExplorerTreeFolderTvSeries();
	}
	
	@Override
	public AbstractExplorerRootsContextMenu<TvSeriesExplorerRootsTreeNode> getContextMenu(ExplorerRootsController controller) {
		return new TvSeriesExplorerRootsContextMenu(controller, this);
	}

}
