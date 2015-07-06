package it.ninjatech.kvo.ui.exceptionconsole;

import it.ninjatech.kvo.ui.UI;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ExceptionConsoleController {

	private final ExceptionConsoleView view;
	private final Set<String> toRead;
	private boolean expandingFirstInAction;

	public ExceptionConsoleController() {
		this.view = new ExceptionConsoleView(this);
		this.toRead = new HashSet<>();
		this.expandingFirstInAction = false;
	}

	public ExceptionConsoleView getView() {
		return this.view;
	}

	public int notifyException(Throwable exception) {
		String id = UUID.randomUUID().toString();
		this.toRead.add(id);
		this.view.addException(id, exception);
		this.view.setToRead(this.toRead);

		return this.toRead.size();
	}

	public int getToRead() {
		return this.toRead.size();
	}
	
	protected void notifyRead(String id) {
		if (!this.expandingFirstInAction) {
			if (this.toRead.remove(id)) {
				UI.get().refreshExceptionsToRead();
				this.view.setToRead(this.toRead);
			}
		}
	}

}
