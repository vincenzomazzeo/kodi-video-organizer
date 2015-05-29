package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.model.TvSeriePathEntity;
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
	
}
