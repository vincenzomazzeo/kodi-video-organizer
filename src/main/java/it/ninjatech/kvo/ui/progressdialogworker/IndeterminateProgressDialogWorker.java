package it.ninjatech.kvo.ui.progressdialogworker;

import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.worker.AbstractWorker;

import java.util.List;

public class IndeterminateProgressDialogWorker<T> extends AbstractProgressDialogWorker<T> {

	public static <T> T show(AbstractWorker<T> worker, String title) {
		T result = null;
		
		IndeterminateProgressDialogWorker<T> progressWorker = new IndeterminateProgressDialogWorker<>(worker, title);
		
		progressWorker.start();
		try {
			result = progressWorker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		return result;
	}
	
	private IndeterminateProgressDialogWorker(AbstractWorker<T> worker, String title) {
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
