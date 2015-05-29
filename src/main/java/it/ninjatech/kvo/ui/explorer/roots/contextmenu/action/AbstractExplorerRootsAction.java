package it.ninjatech.kvo.ui.explorer.roots.contextmenu.action;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

public abstract class AbstractExplorerRootsAction<N extends AbstractExplorerRootsTreeNode> extends AbstractAction {

	private static final long serialVersionUID = -1973796999874227140L;
	
	protected final ExplorerRootsController controller;
	protected final N node;
	
	protected AbstractExplorerRootsAction(ExplorerRootsController controller, N node, String text, Icon icon) {
		super(text, icon);
		
		this.controller = controller;
		this.node = node;
	}
	
	@Override
	public abstract void actionPerformed(ActionEvent event);

}
