package it.ninjatech.kvo.util;

import it.ninjatech.kvo.model.EnhancedLocale;

import java.io.File;

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
	
	public static String normalizeName(String name) {
	    return name.replaceAll("\\\\|/|:|\\*|\\?|\"|<|>|\\|", "");
	}
	
	public static String normalizeForRegExp(String string) {
	    return string.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)");
	}
	
	private Utils() {}
	
}
