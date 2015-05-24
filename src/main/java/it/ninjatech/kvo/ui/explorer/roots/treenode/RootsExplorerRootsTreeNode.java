package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.ui.IconRetriever;

import javax.swing.Icon;

public class RootsExplorerRootsTreeNode extends AbstractExplorerRootsTreeNode {

	public RootsExplorerRootsTreeNode() {
		super(true);
	}
	
	@Override
	public String toString() {
		return "Roots";
	}
	
	@Override
	public boolean equals(Object other) {
		return other.getClass().equals(RootsExplorerRootsTreeNode.class);
	}
	
	@Override
	public boolean hasCustomIcon() {
		return true;
	}

	@Override
	public Icon getCustomIcon() {
		return IconRetriever.retrieveExplorerTreeRootIcon();
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public int compareTo(AbstractExplorerRootsTreeNode other) {
		return 0;
	}

}
