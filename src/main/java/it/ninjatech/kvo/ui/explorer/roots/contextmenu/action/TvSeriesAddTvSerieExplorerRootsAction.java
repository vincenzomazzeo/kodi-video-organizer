package it.ninjatech.kvo.ui.explorer.roots.contextmenu.action;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

public class TvSeriesAddTvSerieExplorerRootsAction extends AbstractExplorerRootsAction<TvSeriesExplorerRootsTreeNode> {

    private static final long serialVersionUID = -434768826990503094L;

    public TvSeriesAddTvSerieExplorerRootsAction(ExplorerRootsController controller, TvSeriesExplorerRootsTreeNode node, String text, Icon icon) {
		super(controller, node, text, icon);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
	    // TODO
	}

}
