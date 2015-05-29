package it.ninjatech.kvo.ui.progressdialogworker;

public final class Progress {

	protected enum Type {
		Init, Update;
	}
	
	private final Type type;
	private final String message;
	private final Integer value;
	
	protected Progress(Type type, String message, Integer value) {
		this.type = type;
		this.message = message;
		this.value = value;
	}

	protected Type getType() {
		return this.type;
	}
	
	protected String getMessage() {
		return this.message;
	}

	protected Integer getValue() {
		return this.value;
	}
	
}
