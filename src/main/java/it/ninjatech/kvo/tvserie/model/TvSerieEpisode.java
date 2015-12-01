package it.ninjatech.kvo.tvserie.model;

import it.ninjatech.kvo.model.EnhancedLocale;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

// TODO capire quali metodi possono diventare protected

public class TvSerieEpisode implements Comparable<TvSerieEpisode> {
	
	private final String id;
	private final String providerId;
	private final Integer number;
	private final EnhancedLocale language;
	
	private String filename;
	private final Set<String> subtitleFilenames;
	
	private TvSerieSeason season;
	private BigDecimal dvdNumber;
	private List<String> directors;
	private String name;
	private Date firstAired;
	private List<String> guestStars;
	private String imdbId;
	private String overview;
	private BigDecimal rating;
	private Integer ratingCount;
	private List<String> writers;
	private String artwork;
	
	public TvSerieEpisode(String id, String providerId, Integer number, EnhancedLocale language) {
		this.id = id;
		this.providerId = providerId;
		this.number = number;
		this.language = language;
		this.subtitleFilenames = new TreeSet<>();
	}
	
	public TvSerieEpisode(String providerId, Integer number, EnhancedLocale language) {
		this(UUID.randomUUID().toString(), providerId, number, language);
	}
	
	@Override
    public String toString() {
        return String.format("[%s] %d - %s - %s", this.id, this.number, this.name, this.filename);
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
		TvSerieEpisode other = (TvSerieEpisode)obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		}
		else if (!this.id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(TvSerieEpisode other) {
		return this.number.compareTo(other.number);
	}
	
	public String getId() {
		return this.id;
	}

	public String getProviderId() {
		return this.providerId;
	}
	
	public Integer getNumber() {
		return this.number;
	}

	public EnhancedLocale getLanguage() {
		return this.language;
	}

	public TvSerieSeason getSeason() {
		return this.season;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public Set<String> getSubtitleFilenames() {
		return this.subtitleFilenames;
	}
	
	public void setSubtitleFilenames(Set<String> subtitleFilenames) {
		this.subtitleFilenames.clear();
		this.subtitleFilenames.addAll(subtitleFilenames);
	}
	
	public void addSubtitleFilename(String subtitleFilename) {
		this.subtitleFilenames.add(subtitleFilename);
	}
	
	public void removeSubtitleFilename(String subtitleFilename) {
		this.subtitleFilenames.remove(subtitleFilename);
	}

	public BigDecimal getDvdNumber() {
		return this.dvdNumber;
	}

	public void setDvdNumber(BigDecimal dvdNumber) {
		this.dvdNumber = dvdNumber;
	}

	public List<String> getDirectors() {
		return this.directors;
	}

	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFirstAired() {
		return this.firstAired;
	}

	public void setFirstAired(Date firstAired) {
		this.firstAired = firstAired;
	}

	public List<String> getGuestStars() {
		return this.guestStars;
	}

	public void setGuestStars(List<String> guestStars) {
		this.guestStars = guestStars;
	}

	public String getImdbId() {
		return this.imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getOverview() {
		return this.overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public BigDecimal getRating() {
		return this.rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public Integer getRatingCount() {
		return this.ratingCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public List<String> getWriters() {
		return this.writers;
	}

	public void setWriters(List<String> writers) {
		this.writers = writers;
	}

	public String getArtwork() {
		return this.artwork;
	}

	public void setArtwork(String artwork) {
		this.artwork = artwork;
	}
	
	public void clearFilenames() {
	    this.filename = null;
	    this.subtitleFilenames.clear();
	}
	
	protected void setSeason(TvSerieSeason season) {
		this.season = season;
	}

}
