package it.ninjatech.kvo.tvserie.model;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.ImageProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.UUID;

public abstract class AbstractTvSerieImage {

	public static Comparator<AbstractTvSerieImage> makeRatingComparator() {
		return new Comparator<AbstractTvSerieImage>() {

			@Override
			public int compare(AbstractTvSerieImage first, AbstractTvSerieImage second) {
				int result = 0;
				
				if (first.rating != null && second.rating != null) {
					return second.rating.compareTo(first.rating);
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
	private final ImageProvider provider;
	private final String path;
	private final BigDecimal rating;
	private final String ratingCount;
	private final EnhancedLocale language;
	
	protected AbstractTvSerieImage(String id, ImageProvider provider, String path, BigDecimal rating, String ratingCount, EnhancedLocale language) {
	    this.id = id;
        this.provider = provider;
        this.path = path;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.language = language;
	}
	
	protected AbstractTvSerieImage(ImageProvider provider, String path, BigDecimal rating, String ratingCount, EnhancedLocale language) {
	    this(UUID.randomUUID().toString(), provider, path, rating, ratingCount, language);
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
		AbstractTvSerieImage other = (AbstractTvSerieImage)obj;
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

	public ImageProvider getProvider() {
		return this.provider;
	}

	public String getPath() {
		return this.path;
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
