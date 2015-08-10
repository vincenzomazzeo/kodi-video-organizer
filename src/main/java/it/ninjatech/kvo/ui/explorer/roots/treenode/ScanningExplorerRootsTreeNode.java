package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;
import it.ninjatech.kvo.util.Labels;

import javax.swing.Icon;

public class ScanningExplorerRootsTreeNode extends AbstractExplorerRootsTreeNode {

	private static final long serialVersionUID = -3385244472469046758L;

	protected ScanningExplorerRootsTreeNode(AbstractExplorerRootsTreeNode parent) {
		super(false, parent);
	}
	
	@Override
	public String toString() {
		return Labels.SCANNING.toLowerCase();
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
		return null;
	}
	
	@Override
	public boolean hasContextMenu() {
		return false;
	}
	
	@Override
	public AbstractExplorerRootsContextMenu<ScanningExplorerRootsTreeNode> getContextMenu(ExplorerRootsController controller) {
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
