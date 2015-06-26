package it.ninjatech.kvo.model;

import java.util.Comparator;

import javax.swing.ImageIcon;

public class EnhancedLocale {

	public static Comparator<EnhancedLocale> countryComparator() {
		return new Comparator<EnhancedLocale>() {

			@Override
			public int compare(EnhancedLocale o1, EnhancedLocale o2) {
				return o1.country.compareTo(o2.country);
			}
			
		};
	}
	
	public static Comparator<EnhancedLocale> languageComparator() {
		return new Comparator<EnhancedLocale>() {

			@Override
			public int compare(EnhancedLocale o1, EnhancedLocale o2) {
				return o1.language.compareTo(o2.language);
			}
			
		};
	}
	
	private final String countryCode;
	private final String country;
	private final ImageIcon countryFlag;
	private final String languageCode;
	private final String language;
	private final ImageIcon languageFlag;
	
	public EnhancedLocale(String countryCode, String country, ImageIcon countryFlag,
	                      String languageCode, String language, ImageIcon languageFlag) {
		this.countryCode = countryCode;
		this.country = country;
		this.countryFlag = countryFlag;
		this.languageCode = languageCode;
		this.language = language;
		this.languageFlag = languageFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((languageCode == null) ? 0 : languageCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnhancedLocale other = (EnhancedLocale)obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		}
		else if (!countryCode.equals(other.countryCode))
			return false;
		if (languageCode == null) {
			if (other.languageCode != null)
				return false;
		}
		else if (!languageCode.equals(other.languageCode))
			return false;
		return true;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public String getCountry() {
		return this.country;
	}

	public ImageIcon getCountryFlag() {
		return this.countryFlag;
	}

	public String getLanguageCode() {
		return this.languageCode;
	}

	public String getLanguage() {
		return this.language;
	}

	public ImageIcon getLanguageFlag() {
		return this.languageFlag;
	}

}
