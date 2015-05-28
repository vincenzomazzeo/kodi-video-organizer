package it.ninjatech.kvo.ui.explorer.roots.treenode;

import javax.swing.Icon;

public abstract class AbstractFsExplorerRootsTreeNode extends AbstractExplorerRootsTreeNode {

	private static final long serialVersionUID = 286038544551010962L;
	
	private final String label;
	
	protected AbstractFsExplorerRootsTreeNode(boolean allowsChildren, AbstractExplorerRootsTreeNode parent, String label) {
		super(allowsChildren, parent);
		
		this.label = label;
	}
	
	@Override
	public String toString() {
		return this.label;
	}
	
	@Override
	public boolean equals(Object other) {
		return other.getClass().equals(getClass()) && this.label.equals(((AbstractFsExplorerRootsTreeNode)other).label);
	}

	@Override
	public boolean hasCustomIcon() {
		return false;
	}

	@Override
	public Icon getCustomIcon() {
		return null;
	}

	@Override
	public int compareTo(AbstractExplorerRootsTreeNode other) {
		int result = 0;
		
		AbstractFsExplorerRootsTreeNode otherNode = (AbstractFsExplorerRootsTreeNode)other;
		if (this.allowsChildren == otherNode.allowsChildren) {
			result = this.label.compareTo(otherNode.label);
		}
		else if (this.allowsChildren && !otherNode.allowsChildren) {
			result = -1;
		}
		else {
			result = 1;
		}
		
		return result;
	}
	
}
