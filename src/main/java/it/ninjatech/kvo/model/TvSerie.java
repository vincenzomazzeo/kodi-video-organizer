package it.ninjatech.kvo.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
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
	private final EnumMap<TvSerieFanart, SortedSet<TvSerieImage>> theTvDbFanarts;
	private final EnumMap<TvSerieFanart, SortedSet<TvSerieImage>> fanarttvFanarts;
	private final Set<String> extraFanarts;

	public TvSerie(String id, String providerId, String name, EnhancedLocale language) {
		this.id = id;
		this.providerId = providerId;
		this.name = name;
		this.language = language;
		this.seasons = new TreeMap<>();
		this.actors = new TreeSet<>();
		this.theTvDbFanarts = new EnumMap<>(TvSerieFanart.class);
		this.fanarttvFanarts = new EnumMap<>(TvSerieFanart.class);
		this.extraFanarts = new HashSet<>();
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
	
	public void addTheTvDbFanart(TvSerieFanart type, String path, BigDecimal rating, String ratingCount, EnhancedLocale lanaguage) {
		TvSerieImage fanart = new TvSerieImage(path, rating, ratingCount, lanaguage);
		
		SortedSet<TvSerieImage> fanarts = this.theTvDbFanarts.get(type);
		if (fanarts == null) {
			fanarts = new TreeSet<>(TvSerieImage.makeRatingComparator());
			this.theTvDbFanarts.put(type, fanarts);
		}
		
		fanarts.add(fanart);
	}
	
	public void addFanarttvFanart(TvSerieFanart type, String path, Integer likes, EnhancedLocale lanaguage) {
		TvSerieImage fanart = new TvSerieImage(path, likes != null ? new BigDecimal(likes) : null, null, lanaguage);
		
		SortedSet<TvSerieImage> fanarts = this.fanarttvFanarts.get(type);
		if (fanarts == null) {
			fanarts = new TreeSet<>(TvSerieImage.makeRatingComparator());
			this.fanarttvFanarts.put(type, fanarts);
		}
		
		fanarts.add(fanart);
	}
	
	public void addTheTvDbSeasonImage(String path, Integer season, BigDecimal rating, String ratingCount, EnhancedLocale lanaguage) {
		TvSerieSeason tvSerieSeason = this.seasons.get(season);
		if (tvSerieSeason != null) {
			tvSerieSeason.addTheTvDbImage(new TvSerieSeasonImage(path, season, rating, ratingCount, lanaguage));
		}
	}
	
	public void addFanarttvSeasonImage(String path, Integer season, Integer likes, EnhancedLocale lanaguage) {
		TvSerieSeason tvSerieSeason = this.seasons.get(season);
		if (tvSerieSeason != null) {
			tvSerieSeason.addFanarttvImage(new TvSerieSeasonImage(path, season, likes != null ? new BigDecimal(likes) : null, null, lanaguage));
		}
	}
	
	public Set<TvSerieImage> getTheTvDbFanart(TvSerieFanart type) {
		return this.theTvDbFanarts.containsKey(type) ? Collections.unmodifiableSortedSet(this.theTvDbFanarts.get(type)) : Collections.<TvSerieImage>emptySet();
	}
	
	public Set<TvSerieImage> getFanarttvFanart(TvSerieFanart type) {
		return this.fanarttvFanarts.containsKey(type) ? Collections.unmodifiableSortedSet(this.fanarttvFanarts.get(type)) : Collections.<TvSerieImage>emptySet();
	}
	
	public int seasonCount() {
		return this.seasons.size();
	}
	
	public Set<Integer> getSeasonNumbers() {
		return Collections.unmodifiableSortedSet((SortedSet<Integer>)this.seasons.keySet());
	}
	
	public Set<TvSerieSeason> getSeasons() {
		return Collections.unmodifiableSortedSet(new TreeSet<TvSerieSeason>(this.seasons.values()));
	}
	
	public Set<TvSerieActor> getActors() {
		return Collections.unmodifiableSortedSet(this.actors);
	}
	
	public void addExtrafanart(String extrafanart) {
		this.extraFanarts.add(extrafanart);
	}
	
	public boolean hasExtraFanarts() {
		return !this.extraFanarts.isEmpty();
	}
	
	public Set<String> getExtraFanarts() {
		return Collections.unmodifiableSet(this.extraFanarts);
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
