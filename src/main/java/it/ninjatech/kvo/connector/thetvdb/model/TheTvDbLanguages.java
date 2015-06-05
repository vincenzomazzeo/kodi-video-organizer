package it.ninjatech.kvo.connector.thetvdb.model;

import it.ninjatech.kvo.utils.LanguageMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Languages")
public class TheTvDbLanguages {

	@XmlElement(name = "Language")
	private List<TheTvDbLanguage> languages;

	protected TheTvDbLanguages() {}
	
	public List<Locale> toLanguages() {
		List<Locale> result = new ArrayList<>();
		
		for (TheTvDbLanguage language : this.languages) {
			result.add(language.toLanguage());
		}
		
		return result;
	}
	
	protected List<TheTvDbLanguage> getLanguages() {
		return this.languages;
	}

	protected void setLanguages(List<TheTvDbLanguage> languages) {
		this.languages = languages;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	protected static class TheTvDbLanguage {

		@XmlElement(name = "abbreviation")
		private String abbreviation;
		
		protected TheTvDbLanguage() {}

		protected Locale toLanguage() {
			return LanguageMap.getInstance().getLanguage(this.abbreviation);
		}
		
		protected String getAbbreviation() {
			return this.abbreviation;
		}

		protected void setAbbreviation(String abbreviation) {
			this.abbreviation = abbreviation;
		}

	}
	
}
