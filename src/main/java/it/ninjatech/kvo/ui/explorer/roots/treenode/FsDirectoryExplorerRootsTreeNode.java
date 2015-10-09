package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;

public class FsDirectoryExplorerRootsTreeNode extends AbstractFsExplorerRootsTreeNode {

	private static final long serialVersionUID = -1101982841588522847L;

	protected FsDirectoryExplorerRootsTreeNode(AbstractExplorerRootsTreeNode parent, String label) {
		super(true, parent, label);
	}

	@Override
	public boolean isLeaf() {
		return !this.children.isEmpty();
	}

	@Override
	public boolean hasContextMenu() {
		return false;
	}

	@Override
	public AbstractExplorerRootsContextMenu<FsDirectoryExplorerRootsTreeNode> getContextMenu(ExplorerRootsController controller) {
		return null;
	}

}
