package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.explorer.roots.treenode.RootsExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSerieExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;

import java.util.Collections;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class ExplorerRootsModel extends DefaultTreeModel {

	private static final long serialVersionUID = 7863198242737416085L;

	private final RootsExplorerRootsTreeNode root;

	public ExplorerRootsModel() {
		super(new RootsExplorerRootsTreeNode(), true);

		this.root = (RootsExplorerRootsTreeNode)getRoot();
	}

	protected void addRoot(AbstractPathEntity root) {
		this.root.addRoot(root);
		nodeStructureChanged(this.root);
	}
	
	protected void removeRoot(TvSeriesExplorerRootsTreeNode root) {
	    this.root.removeRoot(root);
	    nodeStructureChanged(this.root);
	}
	
	protected void refreshRoot(TvSeriesExplorerRootsTreeNode root) {
	    this.root.refreshRoot(root);
	    nodeStructureChanged(root);
	}
	
	protected TvSerieExplorerRootsTreeNode findTvSerieNode(TvSeriePathEntity entity) {
	    TvSerieExplorerRootsTreeNode result = null;
	    
	    for (int i = 0, n = this.root.getChildCount(); i < n; i++) {
	        TreeNode node = this.root.getChildAt(i);
	        if (node.getClass().equals(TvSeriesExplorerRootsTreeNode.class)) {
	            Map<TvSeriePathEntity, TvSerieExplorerRootsTreeNode> children = ((TvSeriesExplorerRootsTreeNode)node).findChildren(Collections.singleton(entity));
	            if (!children.isEmpty()) {
	                result = children.values().iterator().next();
	                break;
	            }
	        }
	    }
	    
	    return result;
	}

}
