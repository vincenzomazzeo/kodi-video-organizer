package it.ninjatech.kvo.ui.progressdialogworker;

import java.util.List;

import it.ninjatech.kvo.worker.AbstractWorker;
import it.ninjatech.kvo.worker.WorkerProgressListener;

import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import com.alee.extended.window.WebProgressDialog;
//TODO UIUtils - new style
public abstract class AbstractProgressDialogWorker<T> extends SwingWorker<T, Progress> implements WorkerProgressListener {

	private final AbstractWorker<T> worker;
	protected final WebProgressDialog progress;
	
	protected AbstractProgressDialogWorker(AbstractWorker<T> worker, String title) {
		super();
		
		this.worker = worker;
		this.progress = new WebProgressDialog(title);
		
		this.worker.addWorkerProgressListener(this);
		
		this.progress.setModal(true);
		this.progress.setAlwaysOnTop(true);
		this.progress.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
	@Override
	protected abstract void process(List<Progress> chunks);
	
	@Override
	public void workerInit(String message, Integer value) {
		publish(new Progress(Progress.Type.Init, message, value));
	}

	@Override
	public void workerUpdate(String message, Integer value) {
		publish(new Progress(Progress.Type.Update, message, value));
	}
	
	@Override
	protected void done() {
		super.done();
		
		this.progress.setVisible(false);
		this.progress.dispose();
	}

	@Override
	protected T doInBackground() throws Exception {
		return this.worker.work();
	}
	
	public void start() {
		super.execute();
		
		this.progress.setVisible(true);
	}
	
}
