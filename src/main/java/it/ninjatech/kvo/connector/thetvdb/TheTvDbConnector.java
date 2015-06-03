package it.ninjatech.kvo.connector.thetvdb;

import it.ninjatech.kvo.configuration.SettingsHandler;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class TheTvDbConnector {

	private static TheTvDbConnector self;

	public static TheTvDbConnector getInstance() {
		return self == null ? self = new TheTvDbConnector() : self;
	}

	private final WebResource webResource;

	private TheTvDbConnector() {
		this.webResource = Client.create().resource("http://thetvdb.com/api");
	}

	public Languages getLanguages() {
		Languages result = this.webResource.
				path(String.format("/%s", SettingsHandler.getInstance().getSettings().getTheTvDbApiKey())).
				path("/languages.xml").
				type(MediaType.TEXT_XML).
				get(Languages.class);

		return result;
	}
	

}
