package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.configuration.Settings;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.dbmapper.TvSerieDbMapper;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.component.MessageDialog;
import it.ninjatech.kvo.ui.explorer.ExplorerController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.AbstractRootExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.explorer.roots.treenode.TvSerieExplorerRootsTreeNode;
import it.ninjatech.kvo.ui.progressdialogworker.DeterminateProgressDialogWorker;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchController;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchListener;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchMultiResultController;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchMultiResultListener;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.worker.DbSaveWorker;
import it.ninjatech.kvo.worker.SettingsStorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.tree.TreePath;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.managers.notification.NotificationManager;
import com.alee.utils.swing.DialogOptions;

public class ExplorerRootsController {

	private final ExplorerRootsModel model;

	private final ExplorerController parent;
	private final ExplorerRootsView view;
	private final WebDirectoryChooser rootChooser;

	public ExplorerRootsController(ExplorerRootsModel model, ExplorerController parent, List<TvSeriesPathEntity> tvSeriesPathEntities) {
		this.model = model;
		this.parent = parent;
		this.view = new ExplorerRootsView(this, this.model);

		this.rootChooser = new WebDirectoryChooser(UI.get());

		if (!tvSeriesPathEntities.isEmpty()) {
			for (TvSeriesPathEntity tvSeriesPathEntity : tvSeriesPathEntities) {
				this.model.addRoot(tvSeriesPathEntity);
			}
			this.view.removeTooltip();
		}
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
		TvSeriesPathEntity tvSeriesPathEntity = TvSerieManager.getInstance().addTvSeriesPathEntity(root);
		if (tvSeriesPathEntity != null) {
			if (root.getParentFile() != null) {
				settings.setLastTvSeriesRootParent(root.getParentFile());
				storeSettings();
			}
			this.model.addRoot(tvSeriesPathEntity);
			NotificationManager.showNotification(Labels.notificationRootAdded(Type.TvSerie.getPlural(), root.getName())).setDisplayTime(TimeUnit.SECONDS.toMillis(3));
			this.parent.addTvSerieTab();
			this.view.removeTooltip();
		}
	}

	protected void notifyAddMoviesRoot() {
//		Settings settings = SettingsHandler.getInstance().getSettings();
//
//		File root = showRootChooser(Type.Movie, settings.getLastMoviesRootParent());
//		if (root != null && !this.model.containtsRoot(root)) {
//			if (root.getParentFile() != null) {
//				settings.setLastMoviesRootParent(root.getParentFile());
//				storeSettings();
//			}
//			addRoot(root, Type.Movie);
//			this.view.removeTooltip();
//		}
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

		this.rootChooser.setTitle(String.format(Labels.getChooseRoot(type.getPlural())));
		this.rootChooser.setSelectedDirectory(lastRootParent);
		this.rootChooser.setVisible(true);

		if (this.rootChooser.getResult() == DialogOptions.OK_OPTION) {
			result = this.rootChooser.getSelectedDirectory();
		}

		return result;
	}

	private void storeSettings() {
		SettingsStorer storer = new SettingsStorer();
		IndeterminateProgressDialogWorker.show(storer, Labels.STORING_SETTINGS);
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

			List<TvSerie> tvSeries = TvSerieHelper.searchFor(search, language);

			if (tvSeries != null) {
				if (tvSeries.isEmpty()) {
					if (askOnEmpty) {
						MessageDialog messageDialog = MessageDialog.getInstance(Labels.CONFIRM, Labels.NO_TV_SERIE_FOUND_ASK_FOR_SEARCH, MessageDialog.Type.Question);
						messageDialog.setVisible(true);
						if (messageDialog.isConfirmed()) {
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
					// Search for match
					List<TvSerie> candidates = new ArrayList<>();
					for (TvSerie tvSerie : tvSeries) {
						if (search.equalsIgnoreCase(tvSerie.getName()) && language.equals(tvSerie.getLanguage())) {
							candidates.add(tvSerie);
						}
					}
					if (candidates.size() == 1) {
						TvSerie tvSerie = candidates.get(0);
						fetch(tvSerie);
					}
					else {
						TvSerieSearchMultiResultController controller = new TvSerieSearchMultiResultController(tvSeries, this);
						controller.getView().setVisible(true);
					}
				}
			}
			else {
				// If tvSeries is null is because there was an error. The program will continue without search for more.
				result = true;
			}

			return result;
		}

		private void fetch(TvSerie tvSerie) {
			this.node.getValue().setTvSerie(tvSerie);
			tvSerie = TvSerieHelper.fetch(tvSerie);
			TvSerieDbMapper mapper = new TvSerieDbMapper();
			DbSaveWorker<TvSerie> worker = new DbSaveWorker<>(tvSerie, mapper, tvSerie.getName());
			DeterminateProgressDialogWorker.show(worker, tvSerie.getName());

			NotificationManager.showNotification(UI.get(), Labels.notificationTvSerieFetched(tvSerie.getName())).setDisplayTime(TimeUnit.SECONDS.toMillis(3));
			this.parent.model.reload(this.node);
			this.parent.parent.addTvSerieTile(this.node.getValue());
		}

	}

}
