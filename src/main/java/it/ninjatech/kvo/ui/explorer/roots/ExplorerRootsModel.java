package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.ui.explorer.roots.treenode.RootsExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;

import javax.swing.tree.DefaultTreeModel;

public class ExplorerRootsModel extends DefaultTreeModel {

	private static final long serialVersionUID = 7863198242737416085L;

	private final RootsExplorerRootsTreeNode root;

	public ExplorerRootsModel() {
		super(new RootsExplorerRootsTreeNode(), true);

		this.root = (RootsExplorerRootsTreeNode)getRoot();
	}

	protected void addRoot(AbstractPathEntity root) {
		this.root.addRoot(root);
		reload();
	}
	
	protected void removeRoot(TvSeriesExplorerRootsTreeNode root) {
	    this.root.removeRoot(root);
	    reload();
	}

}
