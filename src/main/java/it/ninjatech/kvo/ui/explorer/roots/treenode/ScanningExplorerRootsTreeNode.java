package it.ninjatech.kvo.ui.explorer.roots.treenode;

import javax.swing.Icon;

public class ScanningExplorerRootsTreeNode extends AbstractExplorerRootsTreeNode {

	public ScanningExplorerRootsTreeNode() {
		super(false);
	}
	
	@Override
	public String toString() {
		return "scanning...";
	}
	
	@Override
	public boolean equals(Object other) {
		return other == this;
	}
	
	@Override
	public boolean hasCustomIcon() {
		return true;
	}

	@Override
	public Icon getCustomIcon() {
		// TODO
		return null;
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public int compareTo(AbstractExplorerRootsTreeNode other) {
		return 0;
	}

	

	

	

}
