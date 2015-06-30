package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.progressdialogworker.DeterminateProgressDialogWorker;
import it.ninjatech.kvo.worker.AbstractPathBuilder;
import it.ninjatech.kvo.worker.TvSeriesFilePathBuilder;

import java.io.File;

import javax.swing.SwingUtilities;

public class ExplorerRootsRootScanner {

	private final File root;
	private final Type type;

	protected ExplorerRootsRootScanner(File root, Type type) {
		this.root = root;
		this.type = type;
	}

	public AbstractPathEntity scan() {
		AbstractPathEntity result = null;

		AbstractPathBuilder builder = null;
		switch (this.type) {
		case TvSerie:
			builder = new TvSeriesFilePathBuilder(this.root);
			break;
		default:
			builder = null;
		}

		String title = String.format("Scanning %s root %s", this.type.getPlural(), this.root.getName());

		DeterminateProgressDialogWorker<AbstractPathEntity> worker = new DeterminateProgressDialogWorker<>(builder, title);

		worker.start();
		try {
			result = worker.get();
		}
		catch (final Exception e) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					UI.get().notifyException(e);

				}
			});
		}

		return result;
	}

}
