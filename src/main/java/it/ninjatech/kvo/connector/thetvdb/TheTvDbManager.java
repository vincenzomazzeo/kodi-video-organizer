package it.ninjatech.kvo.connector.thetvdb;

import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbActors;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbBanners;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbLanguages;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbTvSerie;
import it.ninjatech.kvo.connector.thetvdb.model.TheTvDbTvSeriesSearchResult;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.io.File;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
// TODO gestire mancanza connessione
public class TheTvDbManager {

	public static final String BASE_URL = "http://thetvdb.com";

	private static TheTvDbManager self;

	public static TheTvDbManager getInstance() {
		return self == null ? self = new TheTvDbManager() : self;
	}

	private final WebResource webResource;
	private boolean enabled;
	private boolean active;
	private String apiKey;
	private List<EnhancedLocale> languages;

	private TheTvDbManager() {
		this.webResource = Client.create().resource(BASE_URL);
		this.enabled = false;
		this.active = false;
		this.languages = null;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isActive() {
		return this.enabled && this.active;
	}

	public List<EnhancedLocale> getLanguages() {
		return this.languages;
	}

	public void deactivate() {
		this.active = false;
		this.apiKey = null;
		this.languages = null;
	}

	public List<EnhancedLocale> checkApiKey(String apiKey) {
		List<EnhancedLocale> result = null;

		ClientResponse response = this.webResource.
				path("/api").
				path(String.format("/%s", apiKey)).
				path("/languages.xml").
				type(MediaType.TEXT_XML).
				get(ClientResponse.class);

		if (response.getStatus() == Status.OK.getStatusCode()) {
			TheTvDbLanguages languages = response.getEntity(TheTvDbLanguages.class);
			result = languages.toLanguages();
		}

		return result;
	}

	public boolean setApiKey(String apiKey) {
		boolean result = false;

		ClientResponse response = this.webResource.
				path("/api").
				path(String.format("/%s", apiKey)).
				path("/languages.xml").
				type(MediaType.TEXT_XML).
				get(ClientResponse.class);

		result = response.getStatus() == Status.OK.getStatusCode();

		if (result) {
			this.active = true;
			this.apiKey = apiKey;
			TheTvDbLanguages languages = response.getEntity(TheTvDbLanguages.class);
			this.languages = languages.toLanguages();
		}

		return result;
	}

	public List<TvSerie> search(String name, EnhancedLocale language) {
		List<TvSerie> result = null;

		WebResource webResource = this.webResource.
				path("/api").
				path("/GetSeries.php").
				queryParam("seriesname", name);
		if (!EnhancedLocaleMap.isEmptyLocale(language)) {
			webResource = webResource.queryParam("language", language.getLanguageCode());
		}
		TheTvDbTvSeriesSearchResult searchResult = webResource.
				type(MediaType.TEXT_XML).
				get(TheTvDbTvSeriesSearchResult.class);

		result = searchResult.toTvSeries();

		return result;
	}

	public void getData(TvSerie tvSerie) {
		TheTvDbTvSerie theTvDbTvSerie = this.webResource.
				path("/api").
				path(String.format("/%s", this.apiKey)).
				path("/series").
				path(String.format("/%s", tvSerie.getProviderId())).
				path("/all").
				path(String.format("/%s.xml", tvSerie.getLanguage().getLanguageCode())).
				type(MediaType.TEXT_XML).
				get(TheTvDbTvSerie.class);

		theTvDbTvSerie.fill(tvSerie);

		TheTvDbActors theTvDbActors = this.webResource.
				path("/api").
				path(String.format("/%s", this.apiKey)).
				path("/series").
				path(String.format("/%s", tvSerie.getProviderId())).
				path("/actors.xml").
				type(MediaType.TEXT_XML).
				get(TheTvDbActors.class);

		theTvDbActors.fill(tvSerie);

		TheTvDbBanners theTvDbBanners = this.webResource.
				path("/api").
				path(String.format("/%s", this.apiKey)).
				path("/series").
				path(String.format("/%s", tvSerie.getProviderId())).
				path("/banners.xml").
				type(MediaType.TEXT_XML).
				get(TheTvDbBanners.class);

		theTvDbBanners.fill(tvSerie);
	}

	public File getImage(String path) {
		return this.webResource.
				path("/banners").
				path(String.format("/%s", path)).
				get(File.class);
	}

}
