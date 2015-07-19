package it.ninjatech.kvo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.UUID;

public class TvSerieSeasonImage {

	public static Comparator<TvSerieSeasonImage> makeRatingComparator() {
		return new Comparator<TvSerieSeasonImage>() {

			@Override
			public int compare(TvSerieSeasonImage first, TvSerieSeasonImage second) {
				int result = 0;
				
				if (first.rating != null && second.rating != null) {
					return first.rating.compareTo(second.rating);
				}
				else if (first.rating != null) {
					result = -1;
				}
				else if (second.rating != null) {
					result = 1;
				}
				
				return result;
			}
			
		};
	}
	
	private final String id;
	private final String path;
	private final Integer season;
	private final BigDecimal rating;
	private final String ratingCount;
	private final EnhancedLocale language;
	
	protected TvSerieSeasonImage(String path, Integer season, BigDecimal rating, String ratingCount, EnhancedLocale language) {
		this.id = UUID.randomUUID().toString();
		this.path = path;
		this.season = season;
		this.rating = rating;
		this.ratingCount = ratingCount;
		this.language = language;
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
		TvSerieSeasonImage other = (TvSerieSeasonImage)obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		}
		else if (!this.id.equals(other.id))
			return false;
		return true;
	}
	
	public String getId() {
		return this.id;
	}

	public String getPath() {
		return this.path;
	}

	public Integer getSeason() {
		return this.season;
	}

	public String getRating() {
		return this.rating != null ? this.rating.setScale(1, RoundingMode.HALF_UP).toString() : null;
	}

	public String getRatingCount() {
		return this.ratingCount;
	}

	public EnhancedLocale getLanguage() {
		return this.language;
	}
	
}
