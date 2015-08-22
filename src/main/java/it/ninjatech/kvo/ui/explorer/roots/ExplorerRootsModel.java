package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractRootsExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.RootsExplorerRootsTreeNode;

import java.io.File;
import java.util.Enumeration;

import javax.swing.tree.DefaultTreeModel;

public class ExplorerRootsModel extends DefaultTreeModel {

	private static final long serialVersionUID = 7863198242737416085L;

	private final RootsExplorerRootsTreeNode root;

	public ExplorerRootsModel() {
		super(new RootsExplorerRootsTreeNode(), true);

		this.root = (RootsExplorerRootsTreeNode)getRoot();
	}

	protected boolean containtsRoot(File root) {
		boolean result = false;

		Enumeration<?> children = this.root.children();
		while (children.hasMoreElements() && !result) {
			AbstractRootsExplorerRootsTreeNode<?> child = (AbstractRootsExplorerRootsTreeNode<?>)children.nextElement();
			result = child.getValue().getPath().equals(root.getAbsolutePath());
		}

		return result;
	}

	protected boolean containtsRoot(AbstractPathEntity root) {
		boolean result = false;

		Enumeration<?> children = this.root.children();
		while (children.hasMoreElements() && !result) {
			AbstractRootsExplorerRootsTreeNode<?> child = (AbstractRootsExplorerRootsTreeNode<?>)children.nextElement();
			result = child.getValue().getPath().equals(root.getPath());
		}

		return result;
	}

	protected void addRoot(AbstractPathEntity root) {
		if (!containtsRoot(root)) {
			this.root.addRoot(root);
			reload();
		}
	}

}
