package it.ninjatech.kvo.connector.thetvdb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Languages")
public class Languages {

	@XmlElement(name = "Language")
	private List<Language> languages;

	protected Languages() {}
	
	public List<Language> getLanguages() {
		return this.languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Language {

		@XmlElement(name = "name")
		private String name;
		@XmlElement(name = "abbreviation")
		private String abbreviation;
		@XmlElement(name = "id")
		private String id;
		
		protected Language() {}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAbbreviation() {
			return this.abbreviation;
		}

		public void setAbbreviation(String abbreviation) {
			this.abbreviation = abbreviation;
		}

		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			this.id = id;
		}

	}
	
}
