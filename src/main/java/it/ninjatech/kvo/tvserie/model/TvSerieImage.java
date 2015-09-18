package it.ninjatech.kvo.tvserie.model;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.ImageProvider;

import java.math.BigDecimal;

public class TvSerieImage extends AbstractTvSerieImage {

	protected TvSerieImage(ImageProvider provider, String path, BigDecimal rating, String ratingCount, EnhancedLocale language) {
		super(provider, path, rating, ratingCount, language);
	}

}
