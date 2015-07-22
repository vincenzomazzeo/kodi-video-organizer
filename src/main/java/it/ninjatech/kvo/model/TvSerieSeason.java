package it.ninjatech.kvo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

public class TvSerieSeason implements Comparable<TvSerieSeason> {

	private final String id;
	private final Integer number;
	private final SortedSet<TvSerieEpisode> episodes;
	private final SortedSet<TvSerieSeasonImage> theTvDbImages;
	private final SortedSet<TvSerieSeasonImage> fanarttvImages;
	
	protected TvSerieSeason(String id, Integer number) {
		this.id = id;
		this.number = number;
		this.episodes = new TreeSet<>();
		this.theTvDbImages = new TreeSet<>(TvSerieSeasonImage.makeRatingComparator());
		this.fanarttvImages = new TreeSet<>(TvSerieSeasonImage.makeRatingComparator());
	}
	
	protected TvSerieSeason(Integer number) {
		this(UUID.randomUUID().toString(), number);
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

	public String getId() {
		return this.id;
	}

	public Integer getNumber() {
		return this.number;
	}
	
	public void addTheTvDbImage(TvSerieSeasonImage image) {
		this.theTvDbImages.add(image);
	}
	
	public void addFanarttvImage(TvSerieSeasonImage image) {
		this.fanarttvImages.add(image);
	}
	
	public String getPosterFilename() {
		String result = null;
		
		DecimalFormat df = new DecimalFormat("00");
		result = String.format("season%s-poster.jpg", df.format(this.number));
		
		return result;
	}
	
	public int episodeCount() {
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
		episode.setSeasonId(this.id);
	}
	
}
