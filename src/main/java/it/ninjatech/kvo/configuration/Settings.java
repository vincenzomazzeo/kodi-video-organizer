package it.ninjatech.kvo.configuration;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Settings {

	private static final String LAST_MOVIES_ROOT_PARENT = "lastMoviesRootParent";
	private static final String LAST_TV_SERIES_ROOT_PARENT = "lastTvSeriesRootParent";
	private static final String THE_TV_DB_API_KEY = "theTvDbApiKey";
	
	@JsonProperty(LAST_MOVIES_ROOT_PARENT)
	private File lastMoviesRootParent;
	@JsonProperty(LAST_TV_SERIES_ROOT_PARENT)
	private File lastTvSeriesRootParent;
	@JsonProperty(THE_TV_DB_API_KEY)
	private String theTvDbApiKey;

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

	public String getTheTvDbApiKey() {
		return this.theTvDbApiKey;
	}

	public void setTheTvDbApiKey(String theTvDbApiKey) {
		this.theTvDbApiKey = theTvDbApiKey;
	}

}
