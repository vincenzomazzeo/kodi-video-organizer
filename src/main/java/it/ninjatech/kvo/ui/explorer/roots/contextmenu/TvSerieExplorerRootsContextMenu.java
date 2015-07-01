package it.ninjatech.kvo.ui.explorer.roots.contextmenu;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.PathOpenExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSerieFetchExplorerRootsAction;
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
		if (Desktop.isDesktopSupported()) {
			// Open in System Explorer
			add(new WebMenuItem(new PathOpenExplorerRootsAction(this.controller, this.node, "Open in System Explorer", null, this.node.getValue().getPath())));
		}
		
		if (TheTvDbManager.getInstance().isActive()) {
    		if (this.node.getValue().getTvSerie() != null) {
    			// Refresh
    		}
    		else {
    			// Fetch
   				add(new WebMenuItem(new TvSerieFetchExplorerRootsAction(this.controller, node, "Fetch", null)));
    		}
		}
		
		// Rescan ???
	}

}
