package it.ninjatech.kvo.utils;

import java.io.File;
import java.text.Normalizer;

import com.alee.utils.FileUtils;

public final class Utils {

	public static File getWorkingDirectory() {
		File result = null;
		
		result = new File(FileUtils.getUserHome(), ".kvo");
		
		return result;
	}
	
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
