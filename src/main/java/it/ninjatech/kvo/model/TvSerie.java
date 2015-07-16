package it.ninjatech.kvo.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

public class TvSerie {

	private final String id;
	private final String providerId;
	private String name;
	private EnhancedLocale language;
	private Date firstAired;
	private String contentRating;
	private List<String> genres;
	private String network;
	private String overview;
	private String rating;
	private String ratingCount;
	private String status;
	private String banner;
	private String fanart;
	private String poster;
	private String imdbId;
	private final Map<Integer, TvSerieSeason> seasons;
	private final SortedSet<TvSerieActor> actors;
	private final EnumMap<TvSerieImage.Type, Set<TvSerieImage>> images;

	public TvSerie(String id, String providerId, String name, EnhancedLocale language) {
		this.id = id;
		this.providerId = providerId;
		this.name = name;
		this.language = language;
		this.seasons = new TreeMap<>();
		this.actors = new TreeSet<>();
		this.images = new EnumMap<>(TvSerieImage.Type.class);
	}

	public TvSerie(String providerId, String name, EnhancedLocale language) {
		this(UUID.randomUUID().toString(), providerId, name, language);
	}
	
	public void addEpisode(Integer seasonNumber, TvSerieEpisode episode) {
		TvSerieSeason season = this.seasons.get(seasonNumber);
		if (season == null) {
			season = new TvSerieSeason(seasonNumber);
			this.seasons.put(seasonNumber, season);
		}
		season.addEpisode(episode);
	}
	
	public void addActor(String name, String role, String path, Integer sortOrder) {
		this.actors.add(new TvSerieActor(name, role, path, sortOrder));
	}
	
	public void addImage(String path, String type, Integer season, BigDecimal rating, String ratingCount) {
		TvSerieImage image = new TvSerieImage(path, type, season, rating, ratingCount);
		
		if (image.getType() == TvSerieImage.Type.Season) {
			this.seasons.get(image.getSeason()).addImage(image);
		}
		else {
			Set<TvSerieImage> images = this.images.get(image.getType());
			if (images == null) {
				images = new TreeSet<>(TvSerieImage.makeRatingComparator());
				this.images.put(image.getType(), images);
			}
			images.add(image);
		}
	}
	
	public int seasonCount() {
		return this.seasons.size();
	}
	
	public Set<Integer> getSeasonNumbers() {
		return Collections.unmodifiableSortedSet((SortedSet<Integer>)this.seasons.keySet());
	}
	
	public Set<TvSerieActor> getActors() {
		return Collections.unmodifiableSortedSet(this.actors);
	}

	public String getId() {
		return this.id;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EnhancedLocale getLanguage() {
		return this.language;
	}

	public void setLanguage(EnhancedLocale language) {
		this.language = language;
	}

	public Date getFirstAired() {
		return this.firstAired;
	}

	public void setFirstAired(Date firstAired) {
		this.firstAired = firstAired;
	}

	public String getContentRating() {
		return this.contentRating;
	}

	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	public List<String> getGenres() {
		return this.genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	public String getNetwork() {
		return this.network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getOverview() {
		return this.overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRatingCount() {
		return this.ratingCount;
	}

	public void setRatingCount(String ratingCount) {
		this.ratingCount = ratingCount;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBanner() {
		return this.banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getFanart() {
		return this.fanart;
	}

	public void setFanart(String fanart) {
		this.fanart = fanart;
	}

	public String getPoster() {
		return this.poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getImdbId() {
		return this.imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

}
