package it.ninjatech.kvo.ui.explorer.roots.contextmenu.action;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSerieExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

public class TvSerieScanExplorerRootsAction extends AbstractExplorerRootsAction<TvSerieExplorerRootsTreeNode> {

	private static final long serialVersionUID = 3659400507619583599L;
	
	public TvSerieScanExplorerRootsAction(ExplorerRootsController controller, TvSerieExplorerRootsTreeNode node, String text, Icon icon) {
		super(controller, node, text, icon);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.controller.scanTvSerie((TvSeriesExplorerRootsTreeNode)this.node.getParent(), this.node);
	}

}
