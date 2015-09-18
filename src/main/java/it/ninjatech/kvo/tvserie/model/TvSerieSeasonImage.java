package it.ninjatech.kvo.tvserie.model;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.ImageProvider;

import java.math.BigDecimal;

public class TvSerieSeasonImage extends AbstractTvSerieImage {

	private final Integer season;
	
	protected TvSerieSeasonImage(ImageProvider provider, String path, Integer season, BigDecimal rating, String ratingCount, EnhancedLocale language) {
		super(provider, path, rating, ratingCount, language);

		this.season = season;
	}

	public Integer getSeason() {
		return this.season;
	}

}
