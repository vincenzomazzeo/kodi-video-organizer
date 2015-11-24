package it.ninjatech.kvo.ui.explorer.roots.contextmenu.action;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;

public class TvSeriesScanExplorerRootsAction extends AbstractExplorerRootsAction<TvSeriesExplorerRootsTreeNode> {

    private static final long serialVersionUID = 6744878807457130360L;

    public TvSeriesScanExplorerRootsAction(ExplorerRootsController controller, TvSeriesExplorerRootsTreeNode node, String text, Icon icon) {
        super(controller, node, text, icon);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        this.controller.scanTvSeries(this.node);
    }
    
}
