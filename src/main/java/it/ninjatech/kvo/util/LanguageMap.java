package it.ninjatech.kvo.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class LanguageMap {

	private static LanguageMap self;

	public static LanguageMap getInstance() {
		return self == null ? self = new LanguageMap() : self;
	}

	private final Map<String, Locale> languages;

	private LanguageMap() {
		this.languages = new HashMap<>();

		for (Locale locale : Locale.getAvailableLocales()) {
			this.languages.put(locale.getLanguage(), locale);
		}
	}

	public Locale getLanguage(String code) {
		Locale result = null;

		if (StringUtils.isNotBlank(code)) {
			result = this.languages.get(code);
			if (result == null) {
				result = new Locale(code);
			}
		}

		return result;
	}

}
