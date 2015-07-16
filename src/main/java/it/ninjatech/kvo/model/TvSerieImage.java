package it.ninjatech.kvo.model;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.UUID;

public class TvSerieImage {

	public enum Type {
		
		Fanart("fanart"),
		Poster("poster"),
		Season("season"),
		Series("series");
		
		private final String value;
		
		private Type(String value) {
			this.value = value;
		}
		
		private static Type parse(String value) {
			Type result = null;
			
			for (Type type : Type.values()) {
				if (type.value.equalsIgnoreCase(value)) {
					result = type;
					break;
				}
			}
			
			return result;
		}
		
	}

	public static Comparator<TvSerieImage> makeRatingComparator() {
		return new Comparator<TvSerieImage>() {

			@Override
			public int compare(TvSerieImage first, TvSerieImage second) {
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
	private final Type type;
	private final Integer season;
	private final BigDecimal rating;
	private final String ratingCount;
	
	protected TvSerieImage(String path, String type, Integer season, BigDecimal rating, String ratingCount) {
		this.id = UUID.randomUUID().toString();
		this.path = path;
		this.type = Type.parse(type);
		this.season = season;
		this.rating = rating;
		this.ratingCount = ratingCount;
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
		TvSerieImage other = (TvSerieImage)obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		}
		else if (!this.id.equals(other.id))
			return false;
		return true;
	}

	public String getPath() {
		return this.path;
	}

	public Type getType() {
		return this.type;
	}

	public Integer getSeason() {
		return this.season;
	}

	public String getRating() {
		return this.rating != null ? this.rating.toString() : null;
	}

	public String getRatingCount() {
		return this.ratingCount;
	}
	
}
