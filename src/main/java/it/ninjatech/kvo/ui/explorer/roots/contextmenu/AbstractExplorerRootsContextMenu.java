package it.ninjatech.kvo.ui.explorer.roots.contextmenu;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;

import com.alee.laf.menu.WebPopupMenu;

public abstract class AbstractExplorerRootsContextMenu<N extends AbstractExplorerRootsTreeNode> extends WebPopupMenu {

	private static final long serialVersionUID = 1699211695851984234L;

	protected final ExplorerRootsController controller;
	protected final N node;
	
	protected AbstractExplorerRootsContextMenu(ExplorerRootsController controller, N node) {
		this.controller = controller;
		this.node = node;
		
		build();
	}
	
	protected abstract void build();
	
}
