package it.ninjatech.kvo.ui.explorer.roots.contextmenu.action;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

public class TvSeriesRemoveExplorerRootsAction extends AbstractExplorerRootsAction<TvSeriesExplorerRootsTreeNode> {

    private static final long serialVersionUID = -2960675886283354245L;

    public TvSeriesRemoveExplorerRootsAction(ExplorerRootsController controller, TvSeriesExplorerRootsTreeNode node, String text, Icon icon) {
		super(controller, node, text, icon);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.controller.removeTvSeriesRoot(this.node);
	}

}
