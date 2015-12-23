package it.ninjatech.kvo.ui.explorer.roots.contextmenu.action;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSerieExplorerRootsTreeNode;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

public class TvSerieSetVisibleExplorerRootsAction extends AbstractExplorerRootsAction<TvSerieExplorerRootsTreeNode> {

    private static final long serialVersionUID = -8514507191335313372L;

    public TvSerieSetVisibleExplorerRootsAction(ExplorerRootsController controller, TvSerieExplorerRootsTreeNode node, String text, Icon icon) {
        super(controller, node, text, icon);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        this.controller.setVisibile(this.node);
    }

}
