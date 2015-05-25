package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.model.MoviesPathEntity;
import it.ninjatech.kvo.ui.IconRetriever;

import javax.swing.Icon;

public class MoviesExplorerRootsTreeNode extends AbstractRootExplorerRootsTreeNode<MoviesPathEntity> {

	protected MoviesExplorerRootsTreeNode(boolean allowsChildren, MoviesPathEntity value) {
		super(true, value);
	}
	
	@Override
	public boolean equals(Object other) {
		boolean result = false;

		if (other.getClass().equals(MoviesExplorerRootsTreeNode.class)) {
			result = this.value.equals(((MoviesExplorerRootsTreeNode)other).value);
		}
		
		return result;
	}
	
	@Override
	public Icon getCustomIcon() {
		return IconRetriever.retrieveExplorerTreeMoviesRootIcon();
	}
	
}
