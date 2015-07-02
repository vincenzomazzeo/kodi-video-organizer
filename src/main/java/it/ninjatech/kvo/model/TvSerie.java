package it.ninjatech.kvo.model;

import java.util.Date;
import java.util.UUID;

public class TvSerie {

	private final String id;
	private final String providerId;
	private String name;
	private EnhancedLocale language;
	private Date firstAired;
	
	public TvSerie(String id, String providerId, String name, EnhancedLocale language) {
		this.id = id;
		this.providerId = providerId;
		this.name = name;
		this.language = language;
	}
	
	public TvSerie(String providerId, String name, EnhancedLocale language) {
		this(UUID.randomUUID().toString(), providerId, name, language);
	}

	public String getId() {
		return this.id;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public EnhancedLocale getLanguage() {
		return this.language;
	}
	
	public void setLanguage(EnhancedLocale language) {
		this.language = language;
	}

	public Date getFirstAired() {
		return this.firstAired;
	}

	public void setFirstAired(Date firstAired) {
		this.firstAired = firstAired;
	}
	
}
