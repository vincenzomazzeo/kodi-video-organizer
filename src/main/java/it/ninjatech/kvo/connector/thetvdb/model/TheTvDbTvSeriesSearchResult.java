package it.ninjatech.kvo.connector.thetvdb.model;

import it.ninjatech.kvo.connector.thetvdb.adapter.TheTvDbDateAdapter;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Data")
public class TheTvDbTvSeriesSearchResult {

	@XmlElement(name = "Series")
	private List<TheTvDbTvSerie> tvSeries;

	protected TheTvDbTvSeriesSearchResult() {
	}

	public List<TvSerie> toTvSeries() {
		List<TvSerie> result = new ArrayList<>();

		if (this.tvSeries != null) {
			for (TheTvDbTvSerie tvSerie : this.tvSeries) {
				result.add(tvSerie.toTvSerie());
			}
		}

		return result;
	}

	protected List<TheTvDbTvSerie> getTvSeries() {
		return this.tvSeries;
	}

	protected void setTvSeries(List<TheTvDbTvSerie> tvSeries) {
		this.tvSeries = tvSeries;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	protected static class TheTvDbTvSerie {

		@XmlElement(name = "seriesid")
		private Integer id;
		@XmlElement(name = "language")
		private String language;
		@XmlElement(name = "SeriesName")
		private String name;
		@XmlElement(name = "FirstAired")
		@XmlJavaTypeAdapter(TheTvDbDateAdapter.class)
		private Date firstAired;

		protected TheTvDbTvSerie() {
		}

		private TvSerie toTvSerie() {
			TvSerie result = new TvSerie(String.valueOf(this.id), this.name, EnhancedLocaleMap.getByLanguage(this.language));
			result.setFirstAired(this.firstAired);

			return result;
		}

		protected Integer getId() {
			return this.id;
		}

		protected void setId(Integer id) {
			this.id = id;
		}

		protected String getLanguage() {
			return this.language;
		}

		protected void setLanguage(String language) {
			this.language = language;
		}

		protected String getName() {
			return this.name;
		}

		protected void setName(String name) {
			this.name = name;
		}

		protected Date getFirstAired() {
			return this.firstAired;
		}

		protected void setFirstAired(Date firstAired) {
			this.firstAired = firstAired;
		}

	}

}
