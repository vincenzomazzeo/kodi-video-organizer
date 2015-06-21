package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.model.AbstractPathEntity;

public abstract class AbstractRootExplorerRootsTreeNode<R extends AbstractPathEntity> extends AbstractExplorerRootsTreeNode {

	private static final long serialVersionUID = -867875031782703820L;
	
	protected final R value;
	
	protected AbstractRootExplorerRootsTreeNode(R value, AbstractRootsExplorerRootsTreeNode<?> parent) {
		super(true, parent);
		
		this.value = value;
	}
	
	@Override
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	public boolean hasContextMenu() {
		return true;
	}
	
	@Override
	public int compareTo(AbstractExplorerRootsTreeNode other) {
		return this.value.getLabel().compareTo(((AbstractRootExplorerRootsTreeNode<?>)other).value.getLabel());
	}

	@Override
	public boolean equals(Object other) {
		return this.value.getLabel().equals(((AbstractRootExplorerRootsTreeNode<?>)other).value.getLabel());
	}
	
	public boolean isFsScanningRequired() {
		return this.children.size() == 1 && this.children.get(0).getClass().equals(ScanningExplorerRootsTreeNode.class);
	}
	
	public void removeChildren() {
		this.children.clear();
	}

	public R getValue() {
		return this.value;
	}
	
}
