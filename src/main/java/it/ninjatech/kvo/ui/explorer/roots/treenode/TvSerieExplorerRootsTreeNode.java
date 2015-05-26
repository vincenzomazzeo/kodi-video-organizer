package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.model.TvSeriePathEntity;

public class TvSerieExplorerRootsTreeNode extends AbstractRootExplorerRootsTreeNode<TvSeriePathEntity> {

	protected TvSerieExplorerRootsTreeNode(TvSeriePathEntity value, TvSeriesExplorerRootsTreeNode parent) {
		super(value, parent);
		
		// add scanning node
		this.children.add(new ScanningExplorerRootsTreeNode(this));
	}
	
}
