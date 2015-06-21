package it.ninjatech.kvo.ui.explorer.roots.contextmenu.action;

import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Icon;

public class PathOpenExplorerRootsAction extends AbstractExplorerRootsAction<AbstractExplorerRootsTreeNode> {

	private static final long serialVersionUID = 3585009354898788886L;

	private final String path;
	
	public PathOpenExplorerRootsAction(ExplorerRootsController controller, AbstractExplorerRootsTreeNode node, String text, Icon icon, String path) {
		super(controller, node, text, icon);
		
		this.path = path;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			File path = new File(this.path);
			Desktop.getDesktop().open(path);
		}
		catch (Exception e) {
			// TODO gestire
			e.printStackTrace();
		}
	}

}
