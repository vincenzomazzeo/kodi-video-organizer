package it.ninjatech.kvo.ui.explorer.roots;

import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.Type;
import it.ninjatech.kvo.worker.AbstractPathBuilder;
import it.ninjatech.kvo.worker.TvSeriesFilePathBuilder;
import it.ninjatech.kvo.worker.WorkerProgressListener;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import com.alee.extended.window.WebProgressDialog;

public class ExplorerRootsRootScanner implements Runnable, WorkerProgressListener {

	private final AtomicBoolean showProgress;
	private final AbstractPathBuilder builder;
	private final WebProgressDialog progress;
	private AbstractPathEntity root;

	protected ExplorerRootsRootScanner(File root, Type type, WebProgressDialog progress) {
		this.showProgress = new AtomicBoolean(true);
		switch (type) {
		case TvSerie:
			this.builder = new TvSeriesFilePathBuilder(root);
			break;
		default:
			this.builder = null;
		}
		this.builder.addWorkerProgressListener(this);
		this.progress = progress;
	}

	@Override
	public void run() {
		this.root = this.builder.build();
		
		this.progress.setProgress(this.progress.getMaximum());

		this.showProgress.set(false);
		this.progress.setVisible(false);
	}

	@Override
	public void workerInit(String message, Integer value) {
		this.progress.setMaximum(value == 0 ? 1 : value);
	}

	@Override
	public void workerUpdate(String message, Integer value) {
		if (message != null) {
			this.progress.setText(message);
		}
		if (value != null) {
			this.progress.setProgress(value);
		}
	}
	
	protected AtomicBoolean getShowProgress() {
		return this.showProgress;
	}
	
	protected AbstractPathEntity getRoot() {
		return this.root;
	}

}
