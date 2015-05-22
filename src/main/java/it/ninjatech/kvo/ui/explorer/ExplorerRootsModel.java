package it.ninjatech.kvo.ui.explorer;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;

public class ExplorerRootsModel extends DefaultTreeModel {

	private static final long serialVersionUID = 7863198242737416085L;

	private final Set<Path> roots;
	private final ExplorerRootsItemTreeNode root;

	protected ExplorerRootsModel() {
		super(new ExplorerRootsItemTreeNode(null, "Roots", null), true);

		this.roots = new HashSet<>();
		this.root = (ExplorerRootsItemTreeNode)getRoot();
	}

	protected boolean containsRoot(File root) {
		return this.roots.contains(root.toPath());
	}
	
	protected boolean addTvShowsRoot(File root) {
		boolean result = false;
		
//		root = new File(Utils.normalizeUnicode(root.getAbsolutePath()));
		
		if (!roots.contains(root.toPath())) {
			result = true;

			this.roots.add(root.toPath());

			this.root.add(new ExplorerRootsItemTreeNode(this.root, root.getName(), root.toPath()));
			
			reload();
		}

		return result;
	}

}
