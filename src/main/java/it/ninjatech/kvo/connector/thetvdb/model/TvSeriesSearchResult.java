package it.ninjatech.kvo.connector.thetvdb.model;

import it.ninjatech.kvo.connector.thetvdb.adapter.DateAdapter;
import it.ninjatech.kvo.connector.thetvdb.adapter.PipeAdapter;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Data")
public class TvSeriesSearchResult {

	@XmlElement(name = "Series")
	private List<TvSerie> tvSeries;
	
	protected TvSeriesSearchResult() {}
	
	public List<TvSerie> getTvSeries() {
		return this.tvSeries;
	}

	public void setTvSeries(List<TvSerie> tvSeries) {
		this.tvSeries = tvSeries;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class TvSerie {
		
		@XmlElement(name = "seriesid")
		private Integer id;
		@XmlElement(name = "language")
		private String language;
		@XmlElement(name = "SeriesName")
		private String name;
		@XmlElement(name = "AliasNames")
		@XmlJavaTypeAdapter(PipeAdapter.class)
		private List<String> aliases;
		@XmlElement(name = "banner")
		private String banner;
		@XmlElement(name = "Overview")
		private String overview;
		@XmlElement(name = "FirstAired")
		@XmlJavaTypeAdapter(DateAdapter.class)
		private Date firstAired;
		@XmlElement(name = "IMDB_ID")
		private String imdbId;
		@XmlElement(name = "Network")
		private String network;
		
		protected TvSerie() {}

		public Integer getId() {
			return this.id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLanguage() {
			return this.language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<String> getAliases() {
			return this.aliases;
		}

		public void setAliases(List<String> aliases) {
			this.aliases = aliases;
		}

		public String getBanner() {
			return this.banner;
		}

		public void setBanner(String banner) {
			this.banner = banner;
		}

		public String getOverview() {
			return this.overview;
		}

		public void setOverview(String overview) {
			this.overview = overview;
		}

		public Date getFirstAired() {
			return this.firstAired;
		}

		public void setFirstAired(Date firstAired) {
			this.firstAired = firstAired;
		}

		public String getImdbId() {
			return this.imdbId;
		}

		public void setImdbId(String imdbId) {
			this.imdbId = imdbId;
		}

		public String getNetwork() {
			return this.network;
		}

		public void setNetwork(String network) {
			this.network = network;
		}
		
	}
	
}
