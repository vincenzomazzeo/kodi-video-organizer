package it.ninjatech.kvo.ui.progressdialogworker;

import java.util.List;

import it.ninjatech.kvo.worker.AbstractWorker;
import it.ninjatech.kvo.worker.WorkerProgressListener;

import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import com.alee.extended.window.WebProgressDialog;
//TODO UIUtils - new style
public abstract class AbstractProgressDialogWorker<T> extends SwingWorker<T, Progress> implements WorkerProgressListener {

	protected static WebProgressDialog progress;
	
	private final AbstractWorker<T> worker;
	
	protected AbstractProgressDialogWorker(AbstractWorker<T> worker, String title) {
		super();
		
		this.worker = worker;
		if (progress == null) {
			progress = new WebProgressDialog(title);
		}
		else {
			progress.setTitle(title);
		}
		
		this.worker.addWorkerProgressListener(this);
		
		progress.setModal(true);
		progress.setAlwaysOnTop(true);
		progress.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		progress.setShowCloseButton(false);
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
		
		progress.setVisible(false);
		progress.dispose();
	}

	@Override
	protected T doInBackground() throws Exception {
		return this.worker.work();
	}
	
	public void start() {
		super.execute();
		
		progress.setVisible(true);
	}
	
}
