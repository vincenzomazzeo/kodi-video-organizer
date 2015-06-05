package it.ninjatech.kvo.connector.thetvdb;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbLanguages;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbTvSerie;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbTvSeriesSearchResult;
import it.ninjatech.kvo.model.TvSerie;

import java.util.List;
import java.util.Locale;

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

	public List<Locale> getLanguages() {
		TheTvDbLanguages languages = this.webResource.
				path(String.format("/%s", SettingsHandler.getInstance().getSettings().getTheTvDbApiKey())).
				path("/languages.xml").
				type(MediaType.TEXT_XML).
				get(TheTvDbLanguages.class);

		return languages.toLanguages();
	}

	public List<TvSerie> search(String name, Locale language) {
		List<TvSerie> result = null;

		WebResource webResource = this.webResource.
				path("/GetSeries.php").
				queryParam("seriesname", name);
		if (language != null) {
			webResource = webResource.queryParam("language", language.getLanguage());
		}
		TheTvDbTvSeriesSearchResult searchResult = webResource.
				type(MediaType.TEXT_XML).
				get(TheTvDbTvSeriesSearchResult.class);

		result = searchResult.toTvSeries();

		return result;
	}

	public void getData(TvSerie tvSerie) {
		TheTvDbTvSerie theTvDbTvSerie = this.webResource.
				path(String.format("/%s", SettingsHandler.getInstance().getSettings().getTheTvDbApiKey())).
				path("/series").
				path(String.format("/%s", tvSerie.getProviderId())).
				path("/all").
				path(String.format("/%s.xml", tvSerie.getLanguage().getLanguage())).
				type(MediaType.TEXT_XML).
				get(TheTvDbTvSerie.class);
		
		theTvDbTvSerie.fill(tvSerie);
	}
	
	public void getBanners(TvSerie tvSerie) {
		// TODO
	}
	
	public void getActors(TvSerie tvSerie) {
		// TODO
	}

}
