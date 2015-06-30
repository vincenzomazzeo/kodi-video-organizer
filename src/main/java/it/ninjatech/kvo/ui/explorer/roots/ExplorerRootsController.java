package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.configuration.Settings;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractRootExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSerieExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.worker.TvSerieFetcher;
import it.ninjatech.kvo.worker.TvSerieFinder;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.tree.TreePath;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.managers.notification.NotificationManager;
import com.alee.utils.swing.DialogOptions;

public class ExplorerRootsController {

	private final ExplorerRootsModel model;

	private final ExplorerRootsView view;
	private final WebDirectoryChooser rootChooser;

	public ExplorerRootsController(ExplorerRootsModel model, ExplorerRootsView view) {
		this.model = model;
		this.view = view;

		this.rootChooser = new WebDirectoryChooser(UI.get());

		this.view.setController(this);
	}

	public void searchForTvSerie(TvSerieExplorerRootsTreeNode node) {
		TvSerieFinder tvSerieFinder = new TvSerieFinder(node.getValue().getLabel());

		IndeterminateProgressDialogWorker<List<TvSerie>> finder = new IndeterminateProgressDialogWorker<>(tvSerieFinder, "Searching for TV Serie");

		List<TvSerie> tvSeries = null;
		finder.start();
		try {
			tvSeries = finder.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}

		if (tvSeries != null) {
			if (tvSeries.size() == 1) {
				TvSerieFetcher tvSerieFetcher = new TvSerieFetcher(tvSeries.get(0));

				IndeterminateProgressDialogWorker<TvSerie> fetcher = new IndeterminateProgressDialogWorker<>(tvSerieFetcher, "Fetching data");

				TvSerie tvSerie = null;
				fetcher.start();
				try {
					tvSerie = fetcher.get();
					node.getValue().setTvSerie(tvSerie);
					NotificationManager.showNotification(String.format("<html>TV Serie <b>%s</b> fetched</html>", tvSerie.getName())).setDisplayTime(TimeUnit.SECONDS.toMillis(3));
					this.model.reload(node);
				}
				catch (Exception e) {
					UI.get().notifyException(e);
				}
			}
			else {
				// TODO gestire
			}
		}
	}

	protected void notifyAddRoot(int x, int y) {
		this.view.showAddRootMenu(x, y);
	}

	protected void notifyAddTvSeriesRoot() {
		Settings settings = SettingsHandler.getInstance().getSettings();

		File root = showRootChooser(Type.TvSerie, settings.getLastTvSeriesRootParent());
		if (root != null && !this.model.containtsRoot(root)) {
			if (root.getParentFile() != null) {
				settings.setLastTvSeriesRootParent(root.getParentFile());
				SettingsHandler.getInstance().store();
			}
			addRoot(root, Type.TvSerie);
			this.view.removeTooltip();
		}
	}

	protected void notifyAddMoviesRoot() {
		Settings settings = SettingsHandler.getInstance().getSettings();

		File root = showRootChooser(Type.Movie, settings.getLastMoviesRootParent());
		if (root != null && !this.model.containtsRoot(root)) {
			if (root.getParentFile() != null) {
				settings.setLastMoviesRootParent(root.getParentFile());
				SettingsHandler.getInstance().store();
			}
			addRoot(root, Type.Movie);
			this.view.removeTooltip();
		}
	}

	protected void notifyPossibleFsScanning(TreePath path) {
		if (path.getLastPathComponent() instanceof AbstractRootExplorerRootsTreeNode) {
			AbstractRootExplorerRootsTreeNode<?> node = (AbstractRootExplorerRootsTreeNode<?>)path.getLastPathComponent();

			if (node.isFsScanningRequired()) {
				node.removeChildren();

				AbstractPathEntity value = (AbstractPathEntity)node.getValue();

				File root = new File(value.getPath());

				ExplorerRootsFsScanner scanner = new ExplorerRootsFsScanner(root, node, value.getLabel());
				scanner.scan();

				this.model.reload(node);
			}
		}
	}

	protected void notifyShowContextMenu(TreePath path, int x, int y) {
		if (path != null && path.getLastPathComponent() instanceof AbstractExplorerRootsTreeNode) {
			AbstractExplorerRootsTreeNode node = (AbstractExplorerRootsTreeNode)path.getLastPathComponent();

			if (node.hasContextMenu()) {
				AbstractExplorerRootsContextMenu<?> menu = node.getContextMenu(this);
				menu.show(this.view.getTree(), x, y);
			}
		}
	}

	private File showRootChooser(Type type, File lastRootParent) {
		File result = null;

		this.rootChooser.setTitle(String.format("Choose %s root", type.getPlural()));
		this.rootChooser.setSelectedDirectory(lastRootParent);
		this.rootChooser.setVisible(true);

		if (this.rootChooser.getResult() == DialogOptions.OK_OPTION) {
			result = this.rootChooser.getSelectedDirectory();
		}

		return result;
	}

	private void addRoot(File root, Type type) {
		ExplorerRootsRootScanner scanner = new ExplorerRootsRootScanner(root, type);

		this.model.addRoot(scanner.scan());

		NotificationManager.showNotification(String.format("<html>%s root <b>%s</b> added</html>", type.getPlural(), root.getName())).setDisplayTime(TimeUnit.SECONDS.toMillis(3));
	}

}
