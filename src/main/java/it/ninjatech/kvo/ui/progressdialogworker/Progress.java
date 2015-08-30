package it.ninjatech.kvo.ui.progressdialogworker;

public final class Progress {

	public enum Type {
		Init, Update;
	}
	
	private final Type type;
	private final String message;
	private final String submessage;
	private final Integer value;
	
	public Progress(Type type, String message, String submessage, Integer value) {
		this.type = type;
		this.message = message;
		this.submessage = submessage;
		this.value = value;
	}

	public Type getType() {
		return this.type;
	}
	
	public String getMessage() {
		return this.message;
	}

	public String getSubmessage() {
		return this.submessage;
	}

	public Integer getValue() {
		return this.value;
	}
	
}
