package it.ninjatech.kvo.connector.thetvdb.model;

import it.ninjatech.kvo.model.TvSerieFanart;

public enum TheTvDbBannerType {

	Fanart("fanart", TvSerieFanart.Fanart),
	Poster("poster", TvSerieFanart.Poster),
	Season("season", null),
	Series("series", TvSerieFanart.Banner);
	
	private final String name;
	private final TvSerieFanart fanart;

	private TheTvDbBannerType(String name, TvSerieFanart fanart) {
		this.name = name;
		this.fanart = fanart;
	}
	
	protected TvSerieFanart getFanart() {
		return this.fanart;
	}

	protected static TheTvDbBannerType parse(String name) {
		TheTvDbBannerType result = null;
		
		for (TheTvDbBannerType type : values()) {
			if (type.name.equals(name)) {
				result = type;
				break;
			}
		}
		
		return result;
	}
	
}
