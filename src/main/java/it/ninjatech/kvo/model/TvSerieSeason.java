package it.ninjatech.kvo.model;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class TvSerieSeason {

	private final String id;
	private final Integer number;
	private final Set<TvSerieEpisode> episodes;
	private final Set<TvSerieImage> images;
	
	protected TvSerieSeason(String id, Integer number) {
		this.id = id;
		this.number = number;
		this.episodes = new TreeSet<>();
		this.images = new TreeSet<>(TvSerieImage.makeRatingComparator());
	}
	
	protected TvSerieSeason(Integer number) {
		this(UUID.randomUUID().toString(), number);
	}
	
	protected void addEpisode(TvSerieEpisode episode) {
		this.episodes.add(episode);
		episode.setSeasonId(this.id);
	}

	public Integer getNumber() {
		return this.number;
	}
	
	public void addImage(TvSerieImage image) {
		this.images.add(image);
	}
	
}
