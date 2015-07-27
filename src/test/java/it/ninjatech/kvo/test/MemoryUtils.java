package it.ninjatech.kvo.test;

import java.text.DecimalFormat;

public class MemoryUtils {

	private static long lastFree = 0l;
	
	public static void printMemory(String message) {
		printMemory(message, true);
	}
	
	public static void printMemory(String message, boolean gc) {
		Runtime runtime = Runtime.getRuntime();
		if (gc) {
			runtime.gc();
		}
		DecimalFormat format = new DecimalFormat("###,###,###");
		long total = runtime.totalMemory();
		long max = runtime.totalMemory();
		long free = runtime.freeMemory();
		System.out.printf("[Total: %s - Max: %s - Occupied: %s - Delta: %s] - %s\n", 
		                  format.format(total), 
		                  format.format(max), 
		                  format.format(total - free), 
		                  format.format(lastFree - free), 
		                  message);
		lastFree = free;
	}
	
}
