package it.ninjatech.kvo.ui.explorer.roots.contextmenu.action;

import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSeriesExplorerRootsTreeNode;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Icon;

public class TvSeriesOpenExplorerRootsAction extends AbstractExplorerRootsAction<TvSeriesExplorerRootsTreeNode> {

	private static final long serialVersionUID = 3585009354898788886L;

	public TvSeriesOpenExplorerRootsAction(ExplorerRootsController controller, TvSeriesExplorerRootsTreeNode node, String text, Icon icon) {
		super(controller, node, text, icon);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if (Desktop.isDesktopSupported()) {
				TvSeriesPathEntity pathEntity = this.node.getValue();
				File path = new File(pathEntity.getPath());
				Desktop.getDesktop().open(path);
			}
			else {
				// TODO gestire
			}
		}
		catch (Exception e) {
			// TODO gestire
			e.printStackTrace();
		}
	}

}
