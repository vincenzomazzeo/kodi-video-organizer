package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.ui.progressdialogworker.DeterminateProgressDialogWorker;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.worker.AbstractPathBuilder;
import it.ninjatech.kvo.worker.TvSeriesFilePathBuilder;

import java.io.File;

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

		result = DeterminateProgressDialogWorker.show(builder, Labels.getScanningRoot(this.type.getPlural(), this.root.getName()));

		return result;
	}

}
