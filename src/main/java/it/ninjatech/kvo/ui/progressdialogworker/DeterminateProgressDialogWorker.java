package it.ninjatech.kvo.ui.progressdialogworker;

import it.ninjatech.kvo.worker.AbstractWorker;

import java.util.List;

public class DeterminateProgressDialogWorker<T> extends AbstractProgressDialogWorker<T> {

	public DeterminateProgressDialogWorker(AbstractWorker<T> worker, String title) {
		super(worker, title);
	}
	
	@Override
	protected void process(List<Progress> chunks) {
		for (Progress chunk : chunks) {
			if (chunk.getMessage() != null) {
				this.progress.setText(chunk.getMessage());
			}
			switch (chunk.getType()) {
			case Init:
				this.progress.setMaximum(chunk.getValue() == null || chunk.getValue().equals(0) ? 1 : chunk.getValue());
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
