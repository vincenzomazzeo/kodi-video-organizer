package it.ninjatech.kvo.ui.explorer;

import it.ninjatech.kvo.ui.UI;

import java.io.File;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.utils.FileUtils;
import com.alee.utils.swing.DialogOptions;

public class ExplorerRootsController {

	private final ExplorerRootsModel model;
	private File lastTvShowsRootParent;
	private File lastMoviesRootParent;

	private final ExplorerRootsView view;
	private final WebDirectoryChooser rootChooser;

	protected ExplorerRootsController(ExplorerRootsModel model, ExplorerRootsView view) {
		this.model = model;
		this.view = view;
		this.lastTvShowsRootParent = new File(FileUtils.getUserHomePath());
		this.lastMoviesRootParent = new File(FileUtils.getUserHomePath());

		this.rootChooser = new WebDirectoryChooser(UI.get());

		this.view.setController(this);
	}

	protected void notifyAddRoot(int x, int y) {
		this.view.showAddRootMenu(x, y);
	}

	protected void notifyAddTvShowsRoot() {
		this.rootChooser.setTitle("Choose TV Shows Root");
		this.rootChooser.setSelectedDirectory(this.lastTvShowsRootParent);
		this.rootChooser.setVisible(true);

		if (this.rootChooser.getResult() == DialogOptions.OK_OPTION) {
			File file = this.rootChooser.getSelectedDirectory();

			this.lastTvShowsRootParent = file.getParentFile() != null ? file.getParentFile() : this.lastTvShowsRootParent;
			
			this.model.addTvShowsRoot(file);
		}
	}

	protected void notifyAddMoviesRoot() {

	}

	private File showRootChooser(String title, File lastRootParent) {
		File result = null;
		
		this.rootChooser.setTitle(title);
		this.rootChooser.setSelectedDirectory(lastRootParent);
		this.rootChooser.setVisible(true);
		
		if (this.rootChooser.getResult() == DialogOptions.OK_OPTION) {
			File file = this.rootChooser.getSelectedDirectory();

			result = file.getParentFile() != null ? file.getParentFile() : lastRootParent;
		}
		
		return result;
	}
	
	private void addRoot(File root) {
		if (!this.model.containsRoot(root)) {
			File[] files = root.listFiles();
			
			ExplorerRootsItemTreeNode rootsItem = new ExplorerRootsItemTreeNode((ExplorerRootsItemTreeNode)this.model.getRoot(), root.getName(), root.toPath());
		}
	}
	
}
