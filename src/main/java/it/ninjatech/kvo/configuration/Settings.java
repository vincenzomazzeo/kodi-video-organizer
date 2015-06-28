package it.ninjatech.kvo.configuration;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Settings {

	private static final String LAST_MOVIES_ROOT_PARENT = "lastMoviesRootParent";
	private static final String LAST_TV_SERIES_ROOT_PARENT = "lastTvSeriesRootParent";
	private static final String THE_TV_DB_API_KEY = "theTvDbApikey";
	private static final String THE_TV_DB_PREFERRED_LANGUAGE = "theTvDbPreferredLanguage";
	
	@JsonProperty(LAST_MOVIES_ROOT_PARENT)
	private File lastMoviesRootParent;
	@JsonProperty(LAST_TV_SERIES_ROOT_PARENT)
	private File lastTvSeriesRootParent;
	@JsonProperty(THE_TV_DB_API_KEY)
	private String theTvDbApikey;
	@JsonProperty(THE_TV_DB_PREFERRED_LANGUAGE)
	private String theTvDbPreferredLanguage;

	@JsonCreator
	protected Settings() {}

	public File getLastMoviesRootParent() {
		return this.lastMoviesRootParent;
	}

	public void setLastMoviesRootParent(File lastMoviesRootParent) {
		this.lastMoviesRootParent = lastMoviesRootParent;
	}

	public File getLastTvSeriesRootParent() {
		return this.lastTvSeriesRootParent;
	}

	public void setLastTvSeriesRootParent(File lastTvSeriesRootParent) {
		this.lastTvSeriesRootParent = lastTvSeriesRootParent;
	}

	public String getTheTvDbApikey() {
		return this.theTvDbApikey;
	}

	public void setTheTvDbApiKey(String theTvDbApiKey) {
		this.theTvDbApikey = theTvDbApiKey;
	}

	public String getTheTvDbPreferredLanguage() {
		return this.theTvDbPreferredLanguage;
	}

	public void setTheTvDbPreferredLanguage(String theTvDbPreferredLanguage) {
		this.theTvDbPreferredLanguage = theTvDbPreferredLanguage;
	}

}
