package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractFsExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.FsDirectoryExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.FsFileExplorerRootsTreeNode;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alee.extended.window.WebProgressDialog;

public class ExplorerRootsFsScanner implements Runnable {

	private final File root;
	private final AbstractExplorerRootsTreeNode parent;
	private final AtomicBoolean showProgress;
	private final WebProgressDialog progress;
	
	protected ExplorerRootsFsScanner(File root, AbstractExplorerRootsTreeNode parent, WebProgressDialog progress) {
		this.root = root;
		this.parent = parent;
		this.showProgress = new AtomicBoolean(true);
		this.progress = progress;
	}
	
	@Override
	public void run() {
		scan(this.root, this.parent);
		
		this.showProgress.set(false);
		this.progress.setVisible(false);
	}

	protected AtomicBoolean getShowProgress() {
		return this.showProgress;
	}
	
	private AbstractFsExplorerRootsTreeNode scan(File directory, AbstractExplorerRootsTreeNode parent) {
		AbstractFsExplorerRootsTreeNode result = null;
		
		for (File file : directory.listFiles()) {
			if (!file.isHidden()) {
				if (file.isDirectory()) {
					result = new FsDirectoryExplorerRootsTreeNode(parent, file.getName());
					parent.addChild(result);
					scan(file, result);
				}
				else {
					result = new FsFileExplorerRootsTreeNode(parent, file.getName());
					parent.addChild(result);
				}
			}
		}
		
		parent.sortChildren();
		
		return result;
	}
	
}
