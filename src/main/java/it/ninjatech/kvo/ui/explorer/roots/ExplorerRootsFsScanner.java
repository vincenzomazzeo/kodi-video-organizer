package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractFsExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.FsDirectoryExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.FsFileExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.worker.AbstractWorker;

import java.io.File;

public class ExplorerRootsFsScanner {

	private final File root;
	private final AbstractExplorerRootsTreeNode parent;
	private final String label;
	
	protected ExplorerRootsFsScanner(File root, AbstractExplorerRootsTreeNode parent, String label) {
		this.root = root;
		this.parent = parent;
		this.label = label;
	}
	
	public void scan() {
		String title = String.format("Scanning %s", this.label);
		
		Scanner scanner = new Scanner(this.root, this.parent);
		
		IndeterminateProgressDialogWorker<Void> worker = new IndeterminateProgressDialogWorker<>(scanner, title);
		
		worker.start();
	}
	
	public static final class Scanner extends AbstractWorker<Void> {

		private final File root;
		private final AbstractExplorerRootsTreeNode parent;
		
		protected Scanner(File root, AbstractExplorerRootsTreeNode parent) {
			this.root = root;
			this.parent = parent;
		}

		@Override
		public Void work() throws Exception {
			scan(this.root, this.parent);
			
			return null;
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
	
}
