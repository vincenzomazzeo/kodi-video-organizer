package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

public abstract class AbstractExplorerRootsTreeNode extends DefaultMutableTreeNode implements Comparable<AbstractExplorerRootsTreeNode> {

	private static final long serialVersionUID = -8612679603689487952L;
	
	protected final boolean allowsChildren;
	protected final Vector<AbstractExplorerRootsTreeNode> children;
	private AbstractExplorerRootsTreeNode parent;
	
	protected AbstractExplorerRootsTreeNode(boolean allowsChildren, AbstractExplorerRootsTreeNode parent) {
		this.allowsChildren = allowsChildren;
		this.children = allowsChildren ? new Vector<AbstractExplorerRootsTreeNode>() : new Vector<AbstractExplorerRootsTreeNode>(0);
		this.parent = parent;
	}
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract boolean equals(Object other);
	
	public abstract boolean hasCustomIcon();
	
	public abstract Icon getCustomIcon();
	
	public abstract boolean hasContextMenu();
	
	public abstract AbstractExplorerRootsContextMenu<?> getContextMenu(ExplorerRootsController controller);
	
	@Override
	public TreeNode getChildAt(int childIndex) {
		return this.children.get(childIndex);
	}
	
	@Override
	public int getChildCount() {
		return this.children.size();
	}
	
	@Override
	public int getIndex(TreeNode node) {
		return this.children.indexOf((AbstractExplorerRootsTreeNode)node);
	}
	
	@Override
	public TreeNode getParent() {
		return this.parent;
	}
	
	@Override
	public boolean getAllowsChildren() {
		return this.allowsChildren;
	}
	
	@Override
	public Enumeration<?> children() {
		return this.children.elements();
	}
	
	public void addChild(AbstractExplorerRootsTreeNode child) {
		this.children.add(child);
	}
	
	public void sortChildren() {
		Collections.sort(this.children);
	}
	
}
