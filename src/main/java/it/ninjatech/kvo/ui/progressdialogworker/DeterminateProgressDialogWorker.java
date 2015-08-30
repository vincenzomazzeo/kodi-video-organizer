package it.ninjatech.kvo.ui.progressdialogworker;

import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.worker.AbstractWorker;

import java.util.List;

public class DeterminateProgressDialogWorker<T> extends AbstractProgressDialogWorker<T> {

	public static <T> T show(AbstractWorker<T> worker, String title) {
		return show(worker, title, false);
	}
	
	public static <T> T show(AbstractWorker<T> worker, String title, boolean enableSubmessage) {
		T result = null;
		
		DeterminateProgressDialogWorker<T> progressWorker = new DeterminateProgressDialogWorker<>(worker, title, enableSubmessage);
		
		progressWorker.start();
		try {
			result = progressWorker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		return result;
	}
	
	private DeterminateProgressDialogWorker(AbstractWorker<T> worker, String title, boolean enableSubmessage) {
		super(worker, title, enableSubmessage);
		
		this.progress.getProgressBar().setIndeterminate(false);
		this.progress.getProgressBar().setStringPainted(true);
	}
	
	@Override
	protected void process(List<Progress> chunks) {
		for (Progress chunk : chunks) {
			if (chunk.getMessage() != null) {
				this.progress.setTextNorth(chunk.getMessage());
			}
			if (chunk.getSubmessage() != null) {
				this.progress.setTextSouth(chunk.getSubmessage());
			}
			switch (chunk.getType()) {
			case Init:
				this.progress.setMaximum(chunk.getValue() == null || chunk.getValue().equals(0) ? 1 : chunk.getValue());
				this.progress.setProgress(0);
				break;
			case Update:
				if (chunk.getValue() != null) {
					this.progress.setProgress(chunk.getValue());
				}
				break;
			}
		}
	}

}
