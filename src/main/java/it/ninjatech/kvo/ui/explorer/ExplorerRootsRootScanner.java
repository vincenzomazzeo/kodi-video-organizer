package it.ninjatech.kvo.ui.explorer;

import it.ninjatech.kvo.model.Type;

import java.io.File;

import com.alee.extended.window.WebProgressDialog;
import com.alee.utils.ThreadUtils;
import com.alee.utils.filefilter.DirectoriesFilter;

public class ExplorerRootsRootScanner implements Runnable {

	private final File root;
	private final Type type;
	private final WebProgressDialog progress;
	private final ExplorerRootsItemTreeNode rootItem;

	protected ExplorerRootsRootScanner(File root, Type type, WebProgressDialog progress) {
		this.root = root;
		this.type = type;
		this.progress = progress;
		this.rootItem = new ExplorerRootsItemTreeNode(root, 1, type);
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		
		File[] files = this.root.listFiles(new DirectoriesFilter());
		this.progress.setMaximum(files.length == 0 ? 1 : files.length);

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			this.progress.setText(file.getName());
			this.rootItem.add(makeItem(file, 2));
			this.progress.setProgress(i + 1);
		}
		this.rootItem.sortChildren();
		this.progress.setProgress(files.length == 0 ? 1 : files.length);
		
		long end = System.currentTimeMillis();
		long wait = 500;
		if ((end - start) < wait) {
			ThreadUtils.sleepSafely(wait - (end - start));
		}

		this.progress.setVisible(false);
	}

	protected ExplorerRootsItemTreeNode getRootItem() {
		return this.rootItem;
	}

	private ExplorerRootsItemTreeNode makeItem(File file, Integer level) {
		ExplorerRootsItemTreeNode result = new ExplorerRootsItemTreeNode(file, level, this.type);

		if (file.isDirectory()) {
			File[] children = file.listFiles();
			if (children != null) {
				for (File child : children) {
					if (!child.isHidden()) {
						result.add(makeItem(child, level + 1));
					}
				}
				result.sortChildren();
			}
		}

		return result;
	}

}
