package it.ninjatech.kvo.tvserie.model;

import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.FsElement;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class TvSeriePathEntity extends AbstractPathEntity {

	private final TvSeriesPathEntity tvSeriesPathEntity;
	private final SortedSet<FsElement> fsElements;
	private final Map<Integer, SortedSet<String>> videoFiles;
	private final Map<Integer, SortedSet<String>> subtitleFiles;
	private final SortedSet<String> extraFanarts;
	private TvSerie tvSerie;

	protected TvSeriePathEntity(String id, String path, String label, TvSeriesPathEntity tvSeriesPathEntity) {
		super(id, path, label);

		this.tvSeriesPathEntity = tvSeriesPathEntity;
		this.fsElements = new TreeSet<>();
		this.videoFiles = new TreeMap<>();
		this.subtitleFiles = new TreeMap<>();
		this.extraFanarts = new TreeSet<>();
		this.tvSerie = null;
	}

	protected TvSeriePathEntity(File file, TvSeriesPathEntity tvSeriesPathEntity) {
		super(file);

		this.tvSeriesPathEntity = tvSeriesPathEntity;
		this.fsElements = new TreeSet<>();
		this.videoFiles = new TreeMap<>();
		this.subtitleFiles = new TreeMap<>();
		this.extraFanarts = new TreeSet<>();
		this.tvSerie = null;
	}

	@Override
    public String toString() {
        return String.format("[%s] %s - %s", getId(), getPath(), getLabel());
    }
	
	public TvSeriesPathEntity getTvSeriesPathEntity() {
		return this.tvSeriesPathEntity;
	}

	public void addFsElement(FsElement fsElement) {
	    this.fsElements.add(fsElement);
	}
	
	public void setFsElements(SortedSet<FsElement> fsElements) {
		this.fsElements.clear();
		this.fsElements.addAll(fsElements);
	}
	
	public Set<FsElement> getFsElements() {
		return Collections.unmodifiableSortedSet(this.fsElements);
	}
	
	public TvSerie getTvSerie() {
		return this.tvSerie;
	}
	
	public void setVideoFiles(Map<Integer, SortedSet<String>> videoFiles) {
		this.videoFiles.clear();
		this.videoFiles.putAll(videoFiles);
	}
	
	public void setSubtitleFiles(Map<Integer, SortedSet<String>> subtitleFiles) {
		this.subtitleFiles.clear();
		this.subtitleFiles.putAll(subtitleFiles);
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
		    Set<String> videoFiles = new TreeSet<>(this.videoFiles.get(season));
			
			TvSerieSeason tvSerieSeason = this.tvSerie.getSeason(season);
			if (tvSerieSeason != null) {
				for (TvSerieEpisode episode : tvSerieSeason.getEpisodes()) {
					if (episode.getFilename() != null) {
						videoFiles.remove(episode.getFilename());
					}
				}
			}
			
			for (String videoFile : videoFiles) {
			    result.add((new File(videoFile)).getName());
			}
		}
		
		return result;
	}
	
	public Set<String> getSubtitleFilesNotReferenced(Integer season) {
		Set<String> result = new TreeSet<>();
		
		if (this.subtitleFiles.containsKey(season)) {
		    Set<String> subtitleFiles = new TreeSet<>(this.subtitleFiles.get(season));
			
			TvSerieSeason tvSerieSeason = this.tvSerie.getSeason(season);
			if (tvSerieSeason != null) {
				for (TvSerieEpisode episode : tvSerieSeason.getEpisodes()) {
					subtitleFiles.removeAll(episode.getSubtitleFilenames());
				}
			}
			
			for (String subtitleFile : subtitleFiles) {
			    result.add((new File(subtitleFile)).getName());
			}
		}
		
		return result;
	}

	public void setTvSerie(TvSerie tvSerie) {
		this.tvSerie = tvSerie;
		this.tvSerie.setTvSeriePathEntity(this);
	}
	
}
