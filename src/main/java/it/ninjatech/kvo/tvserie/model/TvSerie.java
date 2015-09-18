package it.ninjatech.kvo.tvserie.model;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.ImageProvider;

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
	private TvSeriePathEntity tvSeriePathEntity;
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
	private final EnumMap<ImageProvider, EnumMap<TvSerieFanart, SortedSet<TvSerieImage>>> fanarts;

	public TvSerie(String id, String providerId, String name, EnhancedLocale language) {
		this.id = id;
		this.providerId = providerId;
		this.name = name;
		this.language = language;
		this.seasons = new TreeMap<>();
		this.actors = new TreeSet<>();
		this.fanarts = new EnumMap<>(ImageProvider.class);
	}

	public TvSerie(String providerId, String name, EnhancedLocale language) {
		this(UUID.randomUUID().toString(), providerId, name, language);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TvSerie other = (TvSerie)obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		}
		else if (!this.id.equals(other.id))
			return false;
		return true;
	}
	
	public void clear() {
		this.firstAired = null;
		this.contentRating = null;
		this.genres = null;
		this.network = null;
		this.overview = null;
		this.rating = null;
		this.ratingCount = null;
		this.status = null;
		this.banner = null;
		this.fanart = null;
		this.poster = null;
		this.imdbId = null;
		this.seasons.clear();
		this.actors.clear();
		this.fanarts.clear();
	}

	public void addEpisode(Integer seasonNumber, TvSerieEpisode episode) {
		TvSerieSeason season = this.seasons.get(seasonNumber);
		if (season == null) {
			season = new TvSerieSeason(this, seasonNumber);
			this.seasons.put(seasonNumber, season);
		}
		season.addEpisode(episode);
	}

	public void addActor(String name, String role, String imagePath, Integer sortOrder) {
		this.actors.add(new TvSerieActor(name, role, imagePath, sortOrder));
	}

	public void addTheTvDbFanart(TvSerieFanart type, String path, BigDecimal rating, String ratingCount, EnhancedLocale lanaguage) {
		TvSerieImage fanart = new TvSerieImage(ImageProvider.TheTvDb, path, rating, ratingCount, lanaguage);

		EnumMap<TvSerieFanart, SortedSet<TvSerieImage>> theTvDbFanarts = this.fanarts.get(ImageProvider.TheTvDb);
		if (theTvDbFanarts == null) {
			theTvDbFanarts = new EnumMap<>(TvSerieFanart.class);
			this.fanarts.put(ImageProvider.TheTvDb, theTvDbFanarts);
		}

		SortedSet<TvSerieImage> fanarts = theTvDbFanarts.get(type);
		if (fanarts == null) {
			fanarts = new TreeSet<>(TvSerieImage.makeRatingComparator());
			theTvDbFanarts.put(type, fanarts);
		}

		fanarts.add(fanart);
	}

	public void addFanarttvFanart(TvSerieFanart type, String path, Integer likes, EnhancedLocale lanaguage) {
		TvSerieImage fanart = new TvSerieImage(ImageProvider.Fanarttv, path, likes != null ? new BigDecimal(likes) : null, null, lanaguage);

		EnumMap<TvSerieFanart, SortedSet<TvSerieImage>> fanarttvFanarts = this.fanarts.get(ImageProvider.Fanarttv);
		if (fanarttvFanarts == null) {
			fanarttvFanarts = new EnumMap<>(TvSerieFanart.class);
			this.fanarts.put(ImageProvider.Fanarttv, fanarttvFanarts);
		}

		SortedSet<TvSerieImage> fanarts = fanarttvFanarts.get(type);
		if (fanarts == null) {
			fanarts = new TreeSet<>(TvSerieImage.makeRatingComparator());
			fanarttvFanarts.put(type, fanarts);
		}

		fanarts.add(fanart);
	}

	public void addTheTvDbSeasonImage(String path, Integer season, BigDecimal rating, String ratingCount, EnhancedLocale lanaguage) {
		TvSerieSeason tvSerieSeason = this.seasons.get(season);
		if (tvSerieSeason != null) {
			tvSerieSeason.addTheTvDbImage(path, season, rating, ratingCount, lanaguage);
		}
	}

	public void addFanarttvSeasonImage(String path, Integer season, Integer likes, EnhancedLocale lanaguage) {
		TvSerieSeason tvSerieSeason = this.seasons.get(season);
		if (tvSerieSeason != null) {
			tvSerieSeason.addFanarttvImage(path, season, likes, lanaguage);
		}
	}

	public Set<TvSerieImage> getTheTvDbFanart(TvSerieFanart type) {
		Set<TvSerieImage> result = null;

		if (this.fanarts.containsKey(ImageProvider.TheTvDb)) {
			EnumMap<TvSerieFanart, SortedSet<TvSerieImage>> theTvDbFanarts = this.fanarts.get(ImageProvider.TheTvDb);
			result = theTvDbFanarts.containsKey(type) ? Collections.unmodifiableSortedSet(theTvDbFanarts.get(type)) : Collections.<TvSerieImage> emptySet();
		}
		else {
			result = Collections.emptySet();
		}

		return result;
	}

	public Set<TvSerieImage> getFanarttvFanart(TvSerieFanart type) {
		Set<TvSerieImage> result = null;

		if (this.fanarts.containsKey(ImageProvider.Fanarttv)) {
			EnumMap<TvSerieFanart, SortedSet<TvSerieImage>> fanarttvFanarts = this.fanarts.get(ImageProvider.Fanarttv);
			result = fanarttvFanarts.containsKey(type) ? Collections.unmodifiableSortedSet(fanarttvFanarts.get(type)) : Collections.<TvSerieImage> emptySet();
		}
		else {
			result = Collections.emptySet();
		}

		return result;
	}

	public boolean hasFanarts(TvSerieFanart type) {
		boolean result = false;

		result = (this.fanarts.containsKey(ImageProvider.Fanarttv) && this.fanarts.get(ImageProvider.Fanarttv).containsKey(type)) ||
				 (this.fanarts.containsKey(ImageProvider.TheTvDb) && this.fanarts.get(ImageProvider.TheTvDb).containsKey(type));

		return result;
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
	
	public TvSerieSeason getSeason(Integer season) {
		return this.seasons.get(season);
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

	public TvSeriePathEntity getTvSeriePathEntity() {
		return this.tvSeriePathEntity;
	}

	public void setTvSeriePathEntity(TvSeriePathEntity tvSeriePathEntity) {
		this.tvSeriePathEntity = tvSeriePathEntity;
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
