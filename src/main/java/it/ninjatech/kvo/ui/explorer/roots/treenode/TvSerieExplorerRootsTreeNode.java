package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.model.TvSeriePathEntity;

import javax.swing.Icon;

public class TvSerieExplorerRootsTreeNode extends AbstractExplorerRootsTreeNode {

	private final TvSeriePathEntity value;
	
	protected TvSerieExplorerRootsTreeNode(TvSeriePathEntity value, TvSeriesExplorerRootsTreeNode parent) {
		super(true, parent);
		
		this.value = value;
		
		// add scanning node
		this.children.add(new ScanningExplorerRootsTreeNode(this));
	}
	
	@Override
	public String toString() {
		return this.value.getLabel();
	}

	@Override
	public boolean isLeaf() {
		return false;
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
		return this.value.getLabel().compareTo(((TvSerieExplorerRootsTreeNode)other).value.getLabel());
	}

	@Override
	public boolean equals(Object other) {
		return this.value.getLabel().equals(((TvSerieExplorerRootsTreeNode)other).value.getLabel());
	}

}
