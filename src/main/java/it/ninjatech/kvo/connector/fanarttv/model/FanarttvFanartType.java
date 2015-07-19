package it.ninjatech.kvo.connector.fanarttv.model;

import it.ninjatech.kvo.model.TvSerieFanart;

public enum FanarttvFanartType {

	CharacterArt(TvSerieFanart.Character),
	Clearart(TvSerieFanart.Clearart),
	ClearLogo(TvSerieFanart.Logo),
	HdClearart(TvSerieFanart.Clearart),
	HdTvLogo(TvSerieFanart.Logo),
	SeasonPoster(null),
	ShowBackground(TvSerieFanart.Fanart),
	TvBanner(TvSerieFanart.Banner),
	TvPoster(TvSerieFanart.Poster),
	TvThumb(TvSerieFanart.Landscape);
	
	private final TvSerieFanart fanart;
	
	private FanarttvFanartType(TvSerieFanart fanart) {
		this.fanart = fanart;
	}

	protected TvSerieFanart getFanart() {
		return this.fanart;
	}
	
}
