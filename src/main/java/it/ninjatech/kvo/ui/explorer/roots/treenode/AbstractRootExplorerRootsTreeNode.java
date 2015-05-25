package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.model.AbstractPathEntity;

public abstract class AbstractRootExplorerRootsTreeNode<R extends AbstractPathEntity> extends AbstractExplorerRootsTreeNode {

	protected final R value;
	
	protected AbstractRootExplorerRootsTreeNode(boolean allowsChildren, R value, RootsExplorerRootsTreeNode parent) {
		super(allowsChildren, parent);
		
		this.value = value;
	}
	
	public abstract void merge(AbstractPathEntity value);
	
	@Override
	public String toString() {
		return this.value.getLabel();
	}
	
	@Override
	public boolean hasCustomIcon() {
		return true;
	}
	
	@Override
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	public int compareTo(AbstractExplorerRootsTreeNode other) {
		AbstractRootExplorerRootsTreeNode<?> otherRoot = (AbstractRootExplorerRootsTreeNode<?>)other;
		
		return this.value.getLabel().compareTo(otherRoot.value.getLabel());
	}
	
	protected boolean hasValueEquals(AbstractPathEntity value) {
		return this.value.getClass().equals(value.getClass()) && this.value.getPath().equals(value.getPath());
	}

}
