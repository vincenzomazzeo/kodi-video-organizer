package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.configuration.Settings;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.utils.LongTaskExecutor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.swing.WindowConstants;

import com.alee.extended.filechooser.WebDirectoryChooser;
import com.alee.extended.window.WebProgressDialog;
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

	protected void notifyAddRoot(int x, int y) {
		this.view.showAddRootMenu(x, y);
	}

	protected void notifyAddTvSeriesRoot() {
		Settings settings = SettingsHandler.getInstance().getSettings();
		
		File root = showRootChooser(Type.TvSerie, settings.getLastTvSeriesRootParent());
		if (root != null) {
			if (root.getParentFile() != null) {
				settings.setLastTvSeriesRootParent(root.getParentFile());
				SettingsHandler.getInstance().store();
			}
			addRoot(root, Type.TvSerie);
		}
	}

	protected void notifyAddMoviesRoot() {
		Settings settings = SettingsHandler.getInstance().getSettings();
		
		File root = showRootChooser(Type.Movie, settings.getLastMoviesRootParent());
		if (root != null) {
			if (root.getParentFile() != null) {
				settings.setLastMoviesRootParent(root.getParentFile());
				SettingsHandler.getInstance().store();
			}
//			addRoot(root, Type.Movie);
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
		WebProgressDialog progress = new WebProgressDialog(String.format("Scanning %s root %s", type.getPlural(), root.getName()));
		progress.setMinimum(0);
		progress.setModal(true);
		progress.setAlwaysOnTop(true);
		progress.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		ExplorerRootsRootScanner scanner = new ExplorerRootsRootScanner(root, type, progress);
		LongTaskExecutor.getInstance().execute(scanner);

		progress.setVisible(scanner.getShowProgress().get());
		progress.dispose();

		this.model.addRoot(scanner.getRoot());

		NotificationManager.showNotification(String.format("<html>%s root <b>%s</b> added</html>", type.getPlural(), root.getName())).setDisplayTime(TimeUnit.SECONDS.toMillis(3));
	}

}
