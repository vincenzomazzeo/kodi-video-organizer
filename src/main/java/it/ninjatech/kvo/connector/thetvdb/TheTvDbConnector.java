package it.ninjatech.kvo.connector.thetvdb;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.model.Languages;
import it.ninjatech.kvo.connector.thetvdb.model.TvSerie;
import it.ninjatech.kvo.connector.thetvdb.model.TvSeriesSearchResult;

import javax.ws.rs.core.MediaType;

import org.h2.util.StringUtils;

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

	public TvSeriesSearchResult search(String name, String language) {
		TvSeriesSearchResult result = null;

		WebResource webResource = this.webResource.
				path("/GetSeries.php").
				queryParam("seriesname", name);
		if (!StringUtils.isNullOrEmpty(language)) {
			webResource = webResource.queryParam("language", language);
		}
		result = webResource.
				type(MediaType.TEXT_XML).
				get(TvSeriesSearchResult.class);

		return result;
	}
	
	public TvSerie getData(String id, String language) {
		TvSerie result = null;
		
		// TODO
		
		return result;
	}
	
	public Banners get

}
