package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.model.TvSeriesRoot;
import it.ninjatech.kvo.ui.IconRetriever;

import javax.swing.Icon;

public class TvSeriesExplorerRootsTreeNode extends AbstractRootExplorerRootsTreeNode<TvSeriesRoot> {

	protected TvSeriesExplorerRootsTreeNode(boolean allowsChildren, TvSeriesRoot value) {
		super(true, value);
	}
	
	@Override
	public boolean equals(Object other) {
		boolean result = false;

		if (other.getClass().equals(TvSeriesExplorerRootsTreeNode.class)) {
			result = this.value.equals(((TvSeriesExplorerRootsTreeNode)other).value);
		}
		
		return result;
	}
	
	@Override
	public Icon getCustomIcon() {
		return IconRetriever.retrieveExplorerTreeTvSeriesRootIcon();
	}

}
