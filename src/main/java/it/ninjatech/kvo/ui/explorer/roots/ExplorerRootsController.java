package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.configuration.Settings;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractRootExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSerieExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchController;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchListener;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchMultiResultController;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchMultiResultListener;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.tree.TreePath;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.managers.notification.NotificationManager;
import com.alee.utils.swing.DialogOptions;

public class ExplorerRootsController {

	private final ExplorerRootsModel model;

	private final ExplorerRootsView view;
	private final WebDirectoryChooser rootChooser;

	public ExplorerRootsController(ExplorerRootsModel model) {
		this.model = model;
		this.view = new ExplorerRootsView(this, this.model);

		this.rootChooser = new WebDirectoryChooser(UI.get());
	}

	public ExplorerRootsView getView() {
		return this.view;
	}

	public void searchForTvSerie(TvSerieExplorerRootsTreeNode node) {
		TvSerieSearchHandler tvSerieHandler = new TvSerieSearchHandler(this, node);
		tvSerieHandler.handle();
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

	private static class TvSerieSearchHandler implements TvSerieSearchListener, TvSerieSearchMultiResultListener {

		private final ExplorerRootsController parent;
		private final TvSerieExplorerRootsTreeNode node;

		private TvSerieSearchHandler(ExplorerRootsController parent, TvSerieExplorerRootsTreeNode node) {
			this.parent = parent;
			this.node = node;
		}

		@Override
		public boolean notifyTvSerieSearch(String search, EnhancedLocale language) {
			return search(search, language, false);
		}

		@Override
		public void notifyTvSerie(TvSerie tvSerie) {
			fetch(tvSerie);
		}

		private void handle() {
			String search = this.node.getValue().getLabel();
			EnhancedLocale language = EnhancedLocaleMap.getByLanguage(SettingsHandler.getInstance().getSettings().getTheTvDbPreferredLanguage());
			
			search(search, language, true);
		}

		private boolean search(String search, EnhancedLocale language, boolean askOnEmpty) {
			boolean result = false;

			List<TvSerie> tvSeries = TvSerieUtils.searchFor(search, language);

			if (tvSeries != null) {
				if (tvSeries.isEmpty()) {
					if (askOnEmpty) {
						if (WebOptionPane.showConfirmDialog(UI.get(), "No TV Serie found. Do you want to search again changing name or languange?",
															"Confirm", WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == WebOptionPane.YES_OPTION) {
							TvSerieSearchController controller = new TvSerieSearchController(this);
							controller.getView().setVisible(true);
						}
					}
				}
				else if (tvSeries.size() == 1) {
					result = true;
					TvSerie tvSerie = tvSeries.get(0);
					fetch(tvSerie);
				}
				else {
					result = true;
					TvSerieSearchMultiResultController controller = new TvSerieSearchMultiResultController(tvSeries, this);
					controller.getView().setVisible(true);
				}
			}
			else {
				// If tvSeries is null is because there was an error. The program will continue without search more.
				result = true;
			}

			return result;
		}
		
		private void fetch(TvSerie tvSerie) {
			tvSerie = TvSerieUtils.fetch(tvSerie);
			if (tvSerie != null) {
				this.node.getValue().setTvSerie(tvSerie);
				NotificationManager.showNotification(UI.get(), String.format("<html>TV Serie <b>%s</b> fetched</html>", tvSerie.getName())).setDisplayTime(TimeUnit.SECONDS.toMillis(3));
				this.parent.model.reload(this.node);
			}
		}

	}

}
