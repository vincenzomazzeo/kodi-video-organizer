package it.ninjatech.kvo.ui.explorer.roots.contextmenu;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSerieExplorerRootsTreeNode;

import java.awt.Desktop;

import com.alee.laf.menu.WebMenuItem;

public class TvSerieExplorerRootsContextMenu extends AbstractExplorerRootsContextMenu<TvSerieExplorerRootsTreeNode> {

	private static final long serialVersionUID = 5985142688588885888L;

	public TvSerieExplorerRootsContextMenu(ExplorerRootsController controller, TvSerieExplorerRootsTreeNode node) {
		super(controller, node);
	}

	@Override
	protected void build() {
		WebMenuItem menuItem = null;

		if (Desktop.isDesktopSupported()) {
			// Open in System Explorer
			//menuItem = new WebMenuItem(new TvSeriesOpenExplorerRootsAction(this.controller, this.node, "Open in System Explorer", null));
			//add(menuItem);
		}
		
		if (this.node.getValue().getTvSerie() != null) {
			// Refresh
		}
		else {
			// Fetch
		}
		
		// Rescan ???
	}

}
