package it.ninjatech.kvo.model;

import java.math.BigDecimal;

public class TvSerieImage extends AbstractTvSerieImage {

	protected TvSerieImage(TvSerieImageProvider provider, String path, BigDecimal rating, String ratingCount, EnhancedLocale language) {
		super(provider, path, rating, ratingCount, language);
	}

}
