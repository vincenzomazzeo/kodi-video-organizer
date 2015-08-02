package it.ninjatech.kvo.model;

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
