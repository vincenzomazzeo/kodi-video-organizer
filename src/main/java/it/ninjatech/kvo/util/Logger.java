package it.ninjatech.kvo.util;

public final class Logger {

	public static void log(String log, Object... values) {
		System.out.printf(log, values);
	}
	
	private Logger() {}
	
}
