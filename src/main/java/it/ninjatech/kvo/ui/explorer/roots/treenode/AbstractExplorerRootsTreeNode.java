package it.ninjatech.kvo.ui.explorer.roots.treenode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.Icon;
import javax.swing.tree.TreeNode;

public abstract class AbstractExplorerRootsTreeNode implements TreeNode, Comparable<AbstractExplorerRootsTreeNode> {

	protected final boolean allowsChildren;
	protected final List<AbstractExplorerRootsTreeNode> children;
	private AbstractExplorerRootsTreeNode parent;
	
	@SuppressWarnings("unchecked")
	protected AbstractExplorerRootsTreeNode(boolean allowsChildren, AbstractExplorerRootsTreeNode parent) {
		this.allowsChildren = allowsChildren;
		this.children = allowsChildren ? new ArrayList<>() : Collections.EMPTY_LIST;
		this.parent = parent;
	}
	
	@Override
	public abstract String toString();
	
	@Override
	public abstract boolean equals(Object other);
	
	public abstract boolean hasCustomIcon();
	
	public abstract Icon getCustomIcon();
	
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
		return this.children();
	}
	
	public void addChild(AbstractExplorerRootsTreeNode child) {
		this.children.add(child);
	}
	
	public void sortChildren() {
		Collections.sort(this.children);
	}
	
}
