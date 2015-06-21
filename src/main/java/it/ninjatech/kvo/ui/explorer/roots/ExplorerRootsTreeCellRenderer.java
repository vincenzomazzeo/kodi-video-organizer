package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class ExplorerRootsTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 3769549120815691524L;

	protected ExplorerRootsTreeCellRenderer() {
		super();
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		DefaultTreeCellRenderer result = (DefaultTreeCellRenderer)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		AbstractExplorerRootsTreeNode node = (AbstractExplorerRootsTreeNode)value;
		
		if (node.hasCustomIcon()) {
			result.setIcon(node.getCustomIcon());
		}

		return result;
	}
	
}
