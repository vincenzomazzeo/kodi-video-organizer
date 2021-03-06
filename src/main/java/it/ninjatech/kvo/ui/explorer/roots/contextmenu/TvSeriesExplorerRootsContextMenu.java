package it.ninjatech.kvo.ui.explorer.roots.contextmenu;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.PathOpenExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSeriesAddExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSeriesFetchExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSeriesRemoveExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSeriesScanExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;
import it.ninjatech.kvo.util.Labels;

import java.awt.Desktop;

import com.alee.laf.menu.WebMenuItem;

public class TvSeriesExplorerRootsContextMenu extends AbstractExplorerRootsContextMenu<TvSeriesExplorerRootsTreeNode> {

    private static final long serialVersionUID = 7794006663096012610L;

    public TvSeriesExplorerRootsContextMenu(ExplorerRootsController controller, TvSeriesExplorerRootsTreeNode node) {
        super(controller, node);
    }

    @Override
    protected void build() {
        if (Desktop.isDesktopSupported()) {
            // Open in System Explorer
            add(new WebMenuItem(new PathOpenExplorerRootsAction(this.controller, this.node, Labels.OPEN_IN_SYSYEM_EXPLORER, null, this.node.getValue().getPath())));
        }
        
        if (TheTvDbManager.getInstance().isActive()) {
            // Fetch
            add(new WebMenuItem(new TvSeriesFetchExplorerRootsAction(this.controller, this.node, Labels.FETCH, null)));
        }

        // Add new Tv Serie
        add(new WebMenuItem(new TvSeriesAddExplorerRootsAction(this.controller, this.node, Labels.ADD_TV_SERIE, null)));
        
        // Scan
        add(new WebMenuItem(new TvSeriesScanExplorerRootsAction(this.controller, this.node, Labels.SCAN, null, false)));
        
        // Scan Recursive
        add(new WebMenuItem(new TvSeriesScanExplorerRootsAction(this.controller, this.node, Labels.SCAN_RECURSIVE, null, true)));

        // Remove
        add(new WebMenuItem(new TvSeriesRemoveExplorerRootsAction(this.controller, this.node, Labels.REMOVE, null)));
    }

}
