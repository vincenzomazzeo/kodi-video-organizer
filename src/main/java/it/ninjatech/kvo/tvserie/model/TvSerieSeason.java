package it.ninjatech.kvo.tvserie.model;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.ImageProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

public class TvSerieSeason implements Comparable<TvSerieSeason> {

	private final TvSerie tvSerie;
	private final String id;
	private final Integer number;
	private final SortedSet<TvSerieEpisode> episodes;
	private final EnumMap<ImageProvider, SortedSet<TvSerieSeasonImage>> images;
	
	protected TvSerieSeason(TvSerie tvSerie, String id, Integer number) {
		this.tvSerie = tvSerie;
		this.id = id;
		this.number = number;
		this.episodes = new TreeSet<>();
		this.images = new EnumMap<>(ImageProvider.class);
	}
	
	protected TvSerieSeason(TvSerie tvSerie, Integer number) {
		this(tvSerie, UUID.randomUUID().toString(), number);
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
		TvSerieSeason other = (TvSerieSeason)obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		}
		else if (!this.id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(TvSerieSeason other) {
		return this.number.compareTo(other.number);
	}

	public TvSerie getTvSerie() {
		return this.tvSerie;
	}

	public String getId() {
		return this.id;
	}

	public Integer getNumber() {
		return this.number;
	}
	
	public void addFanarttvImage(String id, String path, Integer season, String likes, EnhancedLocale language) {
        addImage(ImageProvider.Fanarttv, new TvSerieSeasonImage(id, ImageProvider.Fanarttv, path, season, likes != null ? new BigDecimal(likes) : null, null, language));
    }
    
    public void addFanarttvImage(String path, Integer season, Integer likes, EnhancedLocale language) {
        addImage(ImageProvider.Fanarttv, new TvSerieSeasonImage(ImageProvider.Fanarttv, path, season, likes != null ? new BigDecimal(likes) : null, null, language));
    }
    
    public void addTheTvDbImage(String id, String path, Integer season, String rating, String ratingCount, EnhancedLocale language) {
        addImage(ImageProvider.TheTvDb, new TvSerieSeasonImage(id, ImageProvider.TheTvDb, path, season, rating != null ? new BigDecimal(rating) : null, ratingCount, language));
    }
    
    public void addTheTvDbImage(String path, Integer season, BigDecimal rating, String ratingCount, EnhancedLocale language) {
	    addImage(ImageProvider.TheTvDb, new TvSerieSeasonImage(ImageProvider.TheTvDb, path, season, rating, ratingCount, language));
	}
	
	public boolean hasImages() {
		return !this.images.isEmpty();
	}
	
	public Set<TvSerieSeasonImage> getImages() {
		Set<TvSerieSeasonImage> result = new LinkedHashSet<>();
		
		if (this.images.containsKey(ImageProvider.TheTvDb)) {
			result.addAll(this.images.get(ImageProvider.TheTvDb));
		}
		if (this.images.containsKey(ImageProvider.Fanarttv)) {
			result.addAll(this.images.get(ImageProvider.Fanarttv));
		}
		
		return result;
	}
	
	public int getEpisodeCount() {
		return this.episodes.size();
	}
	
	public Set<TvSerieEpisode> getEpisodes() {
		return this.episodes.isEmpty() ? Collections.<TvSerieEpisode>emptySet() : Collections.unmodifiableSortedSet(this.episodes);
	}
	
	public String getAverageRating() {
		String result = null;	
		
		int count = 0;
		BigDecimal averageRating = new BigDecimal("0");
		for (TvSerieEpisode episode : this.episodes) {
			BigDecimal episodeRating = episode.getRating();
			if (episodeRating != null) {
				count++;
				averageRating = averageRating.add(episodeRating);
			}
		}
		if (count > 0) {
			result = averageRating.divide(new BigDecimal(count), 1, RoundingMode.HALF_UP).toString();
		}
		
		return result;
	}
	
	protected void addEpisode(TvSerieEpisode episode) {
		this.episodes.add(episode);
		episode.setSeason(this);
	}
	
	private void addImage(ImageProvider imageProvider, TvSerieSeasonImage image) {
        SortedSet<TvSerieSeasonImage> images = this.images.get(imageProvider);
        if (images == null) {
            images = new TreeSet<>(TvSerieSeasonImage.makeRatingComparator());
            this.images.put(imageProvider, images);
        }
        images.add(image);
    }
    
}
