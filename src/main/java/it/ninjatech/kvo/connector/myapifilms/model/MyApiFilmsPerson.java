package it.ninjatech.kvo.connector.myapifilms.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyApiFilmsPerson {

	private static final String URL_PHOTO = "urlPhoto";
	
	@JsonProperty(URL_PHOTO)
	private final String urlPhoto;
	
	@JsonCreator
	private MyApiFilmsPerson(@JsonProperty(URL_PHOTO) String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}

	public String getUrlPhoto() {
		return this.urlPhoto;
	}
	
}
