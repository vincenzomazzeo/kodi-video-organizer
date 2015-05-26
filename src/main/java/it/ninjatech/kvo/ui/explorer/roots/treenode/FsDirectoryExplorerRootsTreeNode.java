package it.ninjatech.kvo.ui.explorer.roots.treenode;

public class FsDirectoryExplorerRootsTreeNode extends AbstractFsExplorerRootsTreeNode {

	public FsDirectoryExplorerRootsTreeNode(AbstractExplorerRootsTreeNode parent, String label) {
		super(true, parent, label);
	}

	@Override
	public boolean isLeaf() {
		return !this.children.isEmpty();
	}

}
