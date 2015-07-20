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
	private static final String THE_TV_DB_ENABLED = "theTvDbEnabled";
	private static final String THE_TV_DB_API_KEY = "theTvDbApiKey";
	private static final String THE_TV_DB_PREFERRED_LANGUAGE = "theTvDbPreferredLanguage";
	private static final String FANARTTV_ENABLED = "fanarttvEnabled";
	private static final String FANARTTV_API_KEY = "fanarttvApiKey";
	private static final String IMDB_ENABLED = "imdbEnabled";
	
	@JsonProperty(LAST_MOVIES_ROOT_PARENT)
	private File lastMoviesRootParent;
	@JsonProperty(LAST_TV_SERIES_ROOT_PARENT)
	private File lastTvSeriesRootParent;
	@JsonProperty(THE_TV_DB_ENABLED)
	private Boolean theTvDbEnabled;
	@JsonProperty(THE_TV_DB_API_KEY)
	private String theTvDbApiKey;
	@JsonProperty(THE_TV_DB_PREFERRED_LANGUAGE)
	private String theTvDbPreferredLanguage;
	@JsonProperty(FANARTTV_ENABLED)
	private Boolean fanarttvEnabled;
	@JsonProperty(FANARTTV_API_KEY)
	private String fanarttvApiKey;
	@JsonProperty(IMDB_ENABLED)
	private Boolean imdbEnabled;

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

	public Boolean getTheTvDbEnabled() {
		return this.theTvDbEnabled;
	}

	public void setTheTvDbEnabled(Boolean theTvDbEnabled) {
		this.theTvDbEnabled = theTvDbEnabled;
	}

	public String getTheTvDbApiKey() {
		return this.theTvDbApiKey;
	}

	public void setTheTvDbApiKey(String theTvDbApiKey) {
		this.theTvDbApiKey = theTvDbApiKey;
	}

	public String getTheTvDbPreferredLanguage() {
		return this.theTvDbPreferredLanguage;
	}

	public void setTheTvDbPreferredLanguage(String theTvDbPreferredLanguage) {
		this.theTvDbPreferredLanguage = theTvDbPreferredLanguage;
	}
	
	public Boolean getFanarttvEnabled() {
		return this.fanarttvEnabled;
	}

	public void setFanarttvEnabled(Boolean fanarttvEnabled) {
		this.fanarttvEnabled = fanarttvEnabled;
	}

	public String getFanarttvApiKey() {
		return this.fanarttvApiKey;
	}
	
	public void setFanarttvApiKey(String fanarttvApiKey) {
		this.fanarttvApiKey = fanarttvApiKey;
	}

	public Boolean getImdbEnabled() {
		return this.imdbEnabled;
	}

	public void setImdbEnabled(Boolean imdbEnabled) {
		this.imdbEnabled = imdbEnabled;
	}
	
}
