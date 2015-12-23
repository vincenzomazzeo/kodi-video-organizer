package it.ninjatech.kvo.ui.explorer.roots.contextmenu;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.PathOpenExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSerieAdaptPathToNameExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSerieFetchExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSerieRemoveExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSerieScanExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.action.TvSerieSetVisibleExplorerRootsAction;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSerieExplorerRootsTreeNode;
import it.ninjatech.kvo.util.Labels;

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
            add(new WebMenuItem(new PathOpenExplorerRootsAction(this.controller, this.node, Labels.OPEN_IN_SYSYEM_EXPLORER, null, this.node.getValue().getPath())));
        }

        // Show in TV Serie Navigator
        add(new WebMenuItem(new TvSerieSetVisibleExplorerRootsAction(this.controller, this.node, Labels.SHOW_IN_TV_SERIE_NAVIGATOR, null)));
        
        if (TheTvDbManager.getInstance().isActive()) {
            // Fetch
            add(new WebMenuItem(new TvSerieFetchExplorerRootsAction(this.controller, this.node, Labels.FETCH, null)));
        }
        
        if (!TvSerieHelper.isNameEqualsPath(this.node.getValue())) {
            add(new WebMenuItem(new TvSerieAdaptPathToNameExplorerRootsAction(this.controller, this.node, Labels.ADAPT_PATH_TO_NAME, null)));
        }
        
        // Scan
        add(new WebMenuItem(new TvSerieScanExplorerRootsAction(this.controller, this.node, Labels.SCAN, null)));
        
        // Remove
        add(new WebMenuItem(new TvSerieRemoveExplorerRootsAction(this.controller, this.node, Labels.REMOVE, null)));
    }

}
