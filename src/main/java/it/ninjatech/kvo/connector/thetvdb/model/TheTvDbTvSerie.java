package it.ninjatech.kvo.connector.thetvdb.model;

import it.ninjatech.kvo.connector.thetvdb.adapter.TheTvDbDateAdapter;
import it.ninjatech.kvo.connector.thetvdb.adapter.TheTvDbPipeAdapter;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Data")
public class TheTvDbTvSerie {

	@XmlElement(name = "Series")
	private TheTvDbBase base;
	@XmlElement(name = "Episode")
	private List<TheTvDbEpisode> episodes;
	
	protected TheTvDbTvSerie() {}
	
	public void fill(TvSerie tvSerie) {
		this.base.fill(tvSerie);
		// TODO gestire data
	}
	
	protected TheTvDbBase getBase() {
		return this.base;
	}

	protected void setBase(TheTvDbBase base) {
		this.base = base;
	}

	protected List<TheTvDbEpisode> getEpisodes() {
		return this.episodes;
	}

	protected void setEpisodes(List<TheTvDbEpisode> episodes) {
		this.episodes = episodes;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	protected static class TheTvDbBase {
		
		@XmlElement(name = "id", required = true)
		private Integer id;
		@XmlElement(name = "Actors", required = true)
		@XmlJavaTypeAdapter(TheTvDbPipeAdapter.class)
		private List<String> actors;
		@XmlElement(name = "FirstAired")
		@XmlJavaTypeAdapter(TheTvDbDateAdapter.class)
		private Date firstAired;
		@XmlElement(name = "ContentRating")
		private String contentRating;
		@XmlElement(name = "Genre")
		@XmlJavaTypeAdapter(TheTvDbPipeAdapter.class)
		private List<String> genres;
		@XmlElement(name = "IMDB_ID")
		private String imdbId;
		@XmlElement(name = "Language", required = true)
		private String language;
		@XmlElement(name = "Network")
		private String network;
		@XmlElement(name = "Overview")
		private String overview;
		@XmlElement(name = "Rating")
		private BigDecimal rating;
		@XmlElement(name = "RatingCount")
		private Integer ratingCount;
		@XmlElement(name = "SeriesName")
		private String name;
		@XmlElement(name = "Status")
		private String status;
		@XmlElement(name = "banner")
		private String banner;
		@XmlElement(name = "fanart")
		private String fanart;
		@XmlElement(name = "poster")
		private String poster;
		
		protected TheTvDbBase() {}

		protected void fill(TvSerie tvSerie) {
			tvSerie.setName(this.name);
			tvSerie.setLanguage(EnhancedLocaleMap.getByLanguage(this.language));
			tvSerie.setFirstAired(this.firstAired);
			tvSerie.setContentRating(this.contentRating);
			tvSerie.setGenres(this.genres);
			tvSerie.setNetwork(this.network);
			tvSerie.setOverview(this.overview);
			tvSerie.setRating(this.rating != null ? this.rating.toString() : null);
			tvSerie.setRatingCount(this.ratingCount != null ? this.ratingCount.toString() : null);
			tvSerie.setStatus(this.status);
			tvSerie.setBanner(this.banner);
			tvSerie.setFanart(this.fanart);
			tvSerie.setPoster(this.poster);
			tvSerie.setImdbId(this.imdbId);
		}
		
		protected Integer getId() {
			return this.id;
		}

		protected void setId(Integer id) {
			this.id = id;
		}

		protected List<String> getActors() {
			return this.actors;
		}

		protected void setActors(List<String> actors) {
			this.actors = actors;
		}

		protected Date getFirstAired() {
			return this.firstAired;
		}

		protected void setFirstAired(Date firstAired) {
			this.firstAired = firstAired;
		}

		protected String getContentRating() {
			return this.contentRating;
		}

		protected void setContentRating(String contentRating) {
			this.contentRating = contentRating;
		}

		protected List<String> getGenres() {
			return this.genres;
		}

		protected void setGenres(List<String> genres) {
			this.genres = genres;
		}

		protected String getImdbId() {
			return this.imdbId;
		}

		protected void setImdbId(String imdbId) {
			this.imdbId = imdbId;
		}

		protected String getLanguage() {
			return this.language;
		}

		protected void setLanguage(String language) {
			this.language = language;
		}

		protected String getNetwork() {
			return this.network;
		}

		protected void setNetwork(String network) {
			this.network = network;
		}

		protected String getOverview() {
			return this.overview;
		}

		protected void setOverview(String overview) {
			this.overview = overview;
		}

		protected BigDecimal getRating() {
			return this.rating;
		}

		protected void setRating(BigDecimal rating) {
			this.rating = rating;
		}

		protected Integer getRatingCount() {
			return this.ratingCount;
		}

		protected void setRatingCount(Integer ratingCount) {
			this.ratingCount = ratingCount;
		}

		protected String getName() {
			return this.name;
		}

		protected void setName(String name) {
			this.name = name;
		}

		protected String getStatus() {
			return this.status;
		}

		protected void setStatus(String status) {
			this.status = status;
		}

		protected String getBanner() {
			return this.banner;
		}

		protected void setBanner(String banner) {
			this.banner = banner;
		}

		protected String getFanart() {
			return this.fanart;
		}

		protected void setFanart(String fanart) {
			this.fanart = fanart;
		}

		protected String getPoster() {
			return this.poster;
		}

		protected void setPoster(String poster) {
			this.poster = poster;
		}
		
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	protected static class TheTvDbEpisode {
		
		@XmlElement(name = "id", required = true)
		private Integer id;
		@XmlElement(name = "DVD_episodenumber")
		private BigDecimal dvdNumber; // A decimal with one decimal and can be used to join episodes together. Can be null, usually used to join episodes that aired as two episodes but were released on DVD as a single episode. If you see an episode 1.1 and 1.2 that means both records should be combined to make episode 1. Cartoons are also known to combine up to 9 episodes together, for example Animaniacs season two.
		@XmlElement(name = "Director")
		@XmlJavaTypeAdapter(TheTvDbPipeAdapter.class)
		private List<String> directors;
		@XmlElement(name = "EpisodeName")
		private String name;
		@XmlElement(name = "EpisodeNumber", required = true)
		private Integer number;
		@XmlElement(name = "FirstAired")
		@XmlJavaTypeAdapter(TheTvDbDateAdapter.class)
		private Date firstAired;
		@XmlElement(name = "GuestStars")
		@XmlJavaTypeAdapter(TheTvDbPipeAdapter.class)
		private List<String> guestStarts;
		@XmlElement(name = "IMDB_ID")
		private String imdbId;
		@XmlElement(name = "Language", required = true)
		private String language;
		@XmlElement(name = "Overview")
		private String overview;
		@XmlElement(name = "Rating")
		private BigDecimal rating;
		@XmlElement(name = "RatingCount")
		private Integer ratingCount;
		@XmlElement(name = "SeasonNumber", required = true)
		private String seasonNumber;
		@XmlElement(name = "Writer")
		@XmlJavaTypeAdapter(TheTvDbPipeAdapter.class)
		private List<String> writers;
		@XmlElement(name = "airsafter_season")
		private Integer airsAfterSeason;
		@XmlElement(name = "airsbefore_episode")
		private Integer airsBeforeEpisode;
		@XmlElement(name = "airsbefore_season")
		private Integer airsBeforeSeason;
		@XmlElement(name = "filename")
		private String artwork;
		
		protected TheTvDbEpisode() {}

		protected Integer getId() {
			return this.id;
		}

		protected void setId(Integer id) {
			this.id = id;
		}

		protected BigDecimal getDvdNumber() {
			return this.dvdNumber;
		}

		protected void setDvdNumber(BigDecimal dvdNumber) {
			this.dvdNumber = dvdNumber;
		}

		protected List<String> getDirectors() {
			return this.directors;
		}

		protected void setDirectors(List<String> directors) {
			this.directors = directors;
		}

		protected String getName() {
			return this.name;
		}

		protected void setName(String name) {
			this.name = name;
		}

		protected Integer getNumber() {
			return this.number;
		}

		protected void setNumber(Integer number) {
			this.number = number;
		}

		protected Date getFirstAired() {
			return this.firstAired;
		}

		protected void setFirstAired(Date firstAired) {
			this.firstAired = firstAired;
		}

		protected List<String> getGuestStarts() {
			return this.guestStarts;
		}

		protected void setGuestStarts(List<String> guestStarts) {
			this.guestStarts = guestStarts;
		}

		protected String getImdbId() {
			return this.imdbId;
		}

		protected void setImdbId(String imdbId) {
			this.imdbId = imdbId;
		}

		protected String getLanguage() {
			return this.language;
		}

		protected void setLanguage(String language) {
			this.language = language;
		}

		protected String getOverview() {
			return this.overview;
		}

		protected void setOverview(String overview) {
			this.overview = overview;
		}

		protected BigDecimal getRating() {
			return this.rating;
		}

		protected void setRating(BigDecimal rating) {
			this.rating = rating;
		}

		protected Integer getRatingCount() {
			return this.ratingCount;
		}

		protected void setRatingCount(Integer ratingCount) {
			this.ratingCount = ratingCount;
		}

		protected String getSeasonNumber() {
			return this.seasonNumber;
		}

		protected void setSeasonNumber(String seasonNumber) {
			this.seasonNumber = seasonNumber;
		}

		protected List<String> getWriters() {
			return this.writers;
		}

		protected void setWriters(List<String> writers) {
			this.writers = writers;
		}

		protected Integer getAirsAfterSeason() {
			return this.airsAfterSeason;
		}

		protected void setAirsAfterSeason(Integer airsAfterSeason) {
			this.airsAfterSeason = airsAfterSeason;
		}

		protected Integer getAirsBeforeEpisode() {
			return this.airsBeforeEpisode;
		}

		protected void setAirsBeforeEpisode(Integer airsBeforeEpisode) {
			this.airsBeforeEpisode = airsBeforeEpisode;
		}

		protected Integer getAirsBeforeSeason() {
			return this.airsBeforeSeason;
		}

		protected void setAirsBeforeSeason(Integer airsBeforeSeason) {
			this.airsBeforeSeason = airsBeforeSeason;
		}

		protected String getArtwork() {
			return this.artwork;
		}

		protected void setArtwork(String artwork) {
			this.artwork = artwork;
		}
		
	}
	
}
