package it.ninjatech.kvo.ui.explorer.roots.treenode;

public class FsFileExplorerRootsTreeNode extends AbstractFsExplorerRootsTreeNode {

	public FsFileExplorerRootsTreeNode(AbstractExplorerRootsTreeNode parent, String label) {
		super(false, parent, label);
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

}
