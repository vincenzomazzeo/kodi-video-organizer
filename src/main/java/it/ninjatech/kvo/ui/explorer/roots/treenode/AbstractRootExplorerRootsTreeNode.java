package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.model.Root;

public abstract class AbstractRootExplorerRootsTreeNode<R extends Root> extends AbstractExplorerRootsTreeNode {

	protected final R value;
	
	protected AbstractRootExplorerRootsTreeNode(boolean allowsChildren, R value) {
		super(allowsChildren);
		
		this.value = value;
	}
	
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

}
