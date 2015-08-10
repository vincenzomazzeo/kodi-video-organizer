package it.ninjatech.kvo.model;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class TvSeriePathEntity extends AbstractPathEntity {

	private final TvSeriesPathEntity tvSeriesPathEntity;
	private final Map<Integer, SortedSet<String>> videoFiles;
	private final Map<Integer, SortedSet<String>> subtitleFiles;
	private final SortedSet<String> extraFanarts;
	private TvSerie tvSerie;

	protected TvSeriePathEntity(String id, String path, String label, TvSeriesPathEntity tvSeriesPathEntity) {
		super(id, path, label);

		this.tvSeriesPathEntity = tvSeriesPathEntity;
		this.videoFiles = new TreeMap<>();
		this.subtitleFiles = new TreeMap<>();
		this.extraFanarts = new TreeSet<>();
		this.tvSerie = null;
	}

	protected TvSeriePathEntity(File file, TvSeriesPathEntity tvSeriesPathEntity) {
		super(file);

		this.tvSeriesPathEntity = tvSeriesPathEntity;
		this.videoFiles = new TreeMap<>();
		this.subtitleFiles = new TreeMap<>();
		this.extraFanarts = new TreeSet<>();
		this.tvSerie = null;
	}

	public TvSeriesPathEntity getTvSeriesPathEntity() {
		return this.tvSeriesPathEntity;
	}

	public TvSerie getTvSerie() {
		return this.tvSerie;
	}
	
	public void setVideoFiles(Integer season, SortedSet<String> videoFiles) {
		this.videoFiles.put(season, videoFiles);
	}
	
	public void setSubtitleFiles(Integer season, SortedSet<String> subtitleFiles) {
		this.subtitleFiles.put(season, subtitleFiles);
	}
	
	public void setExtraFanarts(Set<String> extraFanarts) {
		this.extraFanarts.clear();
		this.extraFanarts.addAll(extraFanarts);
	}
	
	public boolean hasExtraFanarts() {
		return !this.extraFanarts.isEmpty();
	}
	
	public Set<String> getExtraFanarts() {
		return Collections.unmodifiableSortedSet(this.extraFanarts);
	}
	
	public Set<String> getVideoFilesNotReferenced(Integer season) {
		Set<String> result = new TreeSet<>();
		
		if (this.videoFiles.containsKey(season)) {
			result.addAll(this.videoFiles.get(season));
			
			TvSerieSeason tvSerieSeason = this.tvSerie.getSeason(season);
			if (tvSerieSeason != null) {
				for (TvSerieEpisode episode : tvSerieSeason.getEpisodes()) {
					if (episode.getFilename() != null) {
						result.remove(episode.getFilename());
					}
				}
			}
		}
		
		return result;
	}
	
	public Set<String> getSubtitleFilesNotReferenced(Integer season) {
		Set<String> result = new TreeSet<>();
		
		if (this.subtitleFiles.containsKey(season)) {
			result.addAll(this.subtitleFiles.get(season));
			
			TvSerieSeason tvSerieSeason = this.tvSerie.getSeason(season);
			if (tvSerieSeason != null) {
				for (TvSerieEpisode episode : tvSerieSeason.getEpisodes()) {
					result.removeAll(episode.getSubtitleFilenames());
				}
			}
		}
		
		return result;
	}

	public void setTvSerie(TvSerie tvSerie) {
		this.tvSerie = tvSerie;
		this.tvSerie.setTvSeriePathEntity(this);
	}

}
