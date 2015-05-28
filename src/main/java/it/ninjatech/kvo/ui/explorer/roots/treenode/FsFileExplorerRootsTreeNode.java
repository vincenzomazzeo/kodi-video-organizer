package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;

public class FsFileExplorerRootsTreeNode extends AbstractFsExplorerRootsTreeNode {

	private static final long serialVersionUID = -5632505772231610572L;

	public FsFileExplorerRootsTreeNode(AbstractExplorerRootsTreeNode parent, String label) {
		super(false, parent, label);
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public boolean hasContextMenu() {
		return false;
	}
	
	@Override
	public AbstractExplorerRootsContextMenu getContextMenu(ExplorerRootsController controller) {
		return null;
	}
	
}
