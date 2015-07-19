package it.ninjatech.kvo.model;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class TvSerieSeason implements Comparable<TvSerieSeason> {

	private final String id;
	private final Integer number;
	private final Set<TvSerieEpisode> episodes;
	private final Set<TvSerieSeasonImage> theTvDbImages;
	private final Set<TvSerieSeasonImage> fanarttvImages;
	
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
	
	protected void addEpisode(TvSerieEpisode episode) {
		this.episodes.add(episode);
		episode.setSeasonId(this.id);
	}
	
}
