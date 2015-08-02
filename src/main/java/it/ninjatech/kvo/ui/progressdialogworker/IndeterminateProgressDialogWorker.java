package it.ninjatech.kvo.ui.progressdialogworker;

import it.ninjatech.kvo.worker.AbstractWorker;

import java.util.List;
//TODO UIUtils - new style
public class IndeterminateProgressDialogWorker<T> extends AbstractProgressDialogWorker<T> {

	public IndeterminateProgressDialogWorker(AbstractWorker<T> worker, String title) {
		super(worker, title);
		
		this.progress.getProgressBar().setIndeterminate(true);
		this.progress.getProgressBar().setStringPainted(false);
	}
	
	@Override
	protected void process(List<Progress> chunks) {
		for (Progress chunk : chunks) {
			if (chunk.getMessage() != null) {
				this.progress.setText(chunk.getMessage());
			}
		}
	}
	
}
