package it.ninjatech.kvo.ui.progressdialogworker;

import it.ninjatech.kvo.ui.component.ProgressDialog;
import it.ninjatech.kvo.worker.AbstractWorker;
import it.ninjatech.kvo.worker.WorkerProgressListener;

import java.util.List;

import javax.swing.SwingWorker;

public abstract class AbstractProgressDialogWorker<T> extends SwingWorker<T, Progress> implements WorkerProgressListener {

	protected ProgressDialog progress;
	private final AbstractWorker<T> worker;
	
	protected AbstractProgressDialogWorker(AbstractWorker<T> worker, String title, boolean showTextSouth) {
		super();
		
		this.worker = worker;
		this.progress = ProgressDialog.getInstance(title, showTextSouth);
		
		this.worker.addWorkerProgressListener(this);
	}
	
	@Override
	protected abstract void process(List<Progress> chunks);
	
	@Override
	public void workerInit(String message, String submessage, Integer value) {
		publish(new Progress(Progress.Type.Init, message, submessage, value));
	}

	@Override
	public void workerUpdate(String message, String submessage, Integer value) {
		publish(new Progress(Progress.Type.Update, message, submessage, value));
	}
	
	@Override
	protected void done() {
		super.done();
		
		this.progress.setVisible(false);
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
