package it.ninjatech.kvo.ui.explorer.roots.contextmenu;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.laf.menu.WebPopupMenu;

public abstract class AbstractExplorerRootsContextMenu extends WebPopupMenu implements ActionListener {

	private static final long serialVersionUID = 1699211695851984234L;

	protected final ExplorerRootsController controller;
	
	protected AbstractExplorerRootsContextMenu(ExplorerRootsController controller) {
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
	}
	
}
