package it.ninjatech.kvo.util;

import it.ninjatech.kvo.model.EnhancedLocale;

import java.io.File;
import java.text.Normalizer;

import org.apache.commons.lang3.StringUtils;

import com.alee.utils.FileUtils;

public final class Utils {

	public static File getWorkingDirectory() {
		File result = null;
		
		result = new File(FileUtils.getUserHome(), ".kvo");
		
		return result;
	}
	
	public static File getCacheDirectory() {
		File result = null;
		
		result = new File(getWorkingDirectory(), "cache");
		
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
	
	public static EnhancedLocale getLanguageFromSubtitleFile(String subtitleFile) {
		EnhancedLocale result = EnhancedLocaleMap.getEmptyLocale();
		
		if (StringUtils.isNotBlank(subtitleFile)) {
			int lastIndex = subtitleFile.lastIndexOf(".srt");
			if (lastIndex != -1) {
				subtitleFile = subtitleFile.substring(0, lastIndex);
				lastIndex = subtitleFile.lastIndexOf('.');
				if (lastIndex != -1 && lastIndex < (subtitleFile.length() - 2)) {
					String language = subtitleFile.substring(lastIndex + 1);
					result = EnhancedLocaleMap.getByLanguage(language);
					if (result == null) {
						result = EnhancedLocaleMap.getEmptyLocale();
					}
				}
			}
		}
		
		return result;
	}
	
	private Utils() {}
	
}
