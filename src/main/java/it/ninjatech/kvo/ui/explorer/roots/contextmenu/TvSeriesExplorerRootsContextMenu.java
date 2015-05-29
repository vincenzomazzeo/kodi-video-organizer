package it.ninjatech.kvo.ui.explorer.roots.contextmenu;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSeriesOpenExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;

import com.alee.laf.menu.WebMenuItem;

public class TvSeriesExplorerRootsContextMenu extends AbstractExplorerRootsContextMenu<TvSeriesExplorerRootsTreeNode> {

	private static final long serialVersionUID = 7794006663096012610L;

	public TvSeriesExplorerRootsContextMenu(ExplorerRootsController controller, TvSeriesExplorerRootsTreeNode node) {
		super(controller, node);
	}

	@Override
	protected void build() {
		WebMenuItem menuItem = null;
		
		// Open in System Explorer
		menuItem = new WebMenuItem(new TvSeriesOpenExplorerRootsAction(this.controller, this.node, "Open in System Explorer", null));
		add(menuItem);
	}
	
}
