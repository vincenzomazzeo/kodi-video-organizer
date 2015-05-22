package it.ninjatech.kvo.utils;

import java.text.Normalizer;

public final class Utils {

	// Normalize to "Normalization Form Canonical Decomposition" (NFD)
	public static String normalizeUnicode(String str) {
		String result = str;
		
	    Normalizer.Form form = Normalizer.Form.NFD;
	    if (!Normalizer.isNormalized(str, form)) {
	        result = Normalizer.normalize(str, form);
	    }
	    
	    return result;
	}
	
	private Utils() {}
	
}
