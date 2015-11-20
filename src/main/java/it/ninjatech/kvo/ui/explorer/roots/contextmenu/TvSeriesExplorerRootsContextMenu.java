package it.ninjatech.kvo.ui.explorer.roots.contextmenu;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.PathOpenExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSeriesFetchAllExplorerRootsAction;
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

        // TODO

        // Remove

        // Scan
        // -- cerca nuove cartelle

        // Scan Recursive
        // -- cerca nuove cartelle e propaga ai figli

        // Fetch all
        // -- ricerca la serie tv: solo se TvSerie è nullo

        // Refresh all
        // -- esegue di nuovo la fetch

        // Add new Tv Serie

        if (TheTvDbManager.getInstance().isActive()) {
            // Fetch
            add(new WebMenuItem(new TvSeriesFetchAllExplorerRootsAction(this.controller, this.node, Labels.FETCH, null)));
        }
    }

}
