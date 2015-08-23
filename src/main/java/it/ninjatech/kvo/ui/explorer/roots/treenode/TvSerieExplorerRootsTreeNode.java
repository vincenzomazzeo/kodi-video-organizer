package it.ninjatech.kvo.ui.explorer.roots.treenode;

import javax.swing.Icon;

import it.ninjatech.kvo.tvserie.TvSeriePathEntity;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.TvSerieExplorerRootsContextMenu;

public class TvSerieExplorerRootsTreeNode extends AbstractRootExplorerRootsTreeNode<TvSeriePathEntity> {

	private static final long serialVersionUID = -6166408369206096452L;

	protected TvSerieExplorerRootsTreeNode(TvSeriePathEntity value, TvSeriesExplorerRootsTreeNode parent) {
		super(value, parent);
		
		// add scanning node
		this.children.add(new ScanningExplorerRootsTreeNode(this));
	}

	@Override
	public AbstractExplorerRootsContextMenu<TvSerieExplorerRootsTreeNode> getContextMenu(ExplorerRootsController controller) {
		return new TvSerieExplorerRootsContextMenu(controller, this);
	}
	
	@Override
	public String toString() {
		return this.value.getTvSerie() == null ? this.value.getLabel() : this.value.getTvSerie().getName();
	}
	
	@Override
	public boolean hasCustomIcon() {
		return this.value.getTvSerie() != null;
	}
	
	@Override
	public Icon getCustomIcon() {
		return ImageRetriever.retrieveExplorerTreeTvSerie();
	}
	
}
