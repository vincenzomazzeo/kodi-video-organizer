package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.ui.explorer.ExplorerRootsItemTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.RootsExplorerRootsTreeNode;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;

public class ExplorerRootsModel extends DefaultTreeModel {

	private static final long serialVersionUID = 7863198242737416085L;

	private final Set<String> roots;
	private final RootsExplorerRootsTreeNode root;
//	private final ExplorerRootsItemTreeNode root;

	public ExplorerRootsModel() {
		super(new RootsExplorerRootsTreeNode(), true);
//		super(new ExplorerRootsItemTreeNode("Roots"), true);

		this.roots = new HashSet<>();
		this.root = (RootsExplorerRootsTreeNode)getRoot();
//		this.root = (ExplorerRootsItemTreeNode)getRoot();
	}

	protected boolean containsRoot(File root) {
		return this.roots.contains(root.getAbsolutePath());
	}
	
//	protected void addRoot(ExplorerRootsItemTreeNode root) {
//		this.roots.add(root.getValue());
//		this.root.add(root);
//		this.root.sortChildren();
//		reload();
//	}

}
