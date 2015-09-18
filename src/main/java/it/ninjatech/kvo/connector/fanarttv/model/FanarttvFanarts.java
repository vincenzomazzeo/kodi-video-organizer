package it.ninjatech.kvo.connector.fanarttv.model;

import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FanarttvFanarts {

	private static final String NAME = "name";
	private static final String THE_TV_DB_ID = "thetvdb_id";
	private static final String CLEAR_LOGOS = "clearlogo";
	private static final String HD_TV_LOGOS = "hdtvlogo";
	private static final String TV_THUMBS = "tvthumb";
	private static final String SEASON_POSTERS = "seasonposter";
	private static final String TV_POSTERS = "tvposter";
	private static final String SHOW_BACKGROUNDS = "showbackground";
	private static final String CLEAR_ARTS = "clearart";
	private static final String TV_BANNERS = "tvbanner";
	private static final String SEASON_THUMBS = "seasonthumb";
	private static final String HD_CLEARARTS = "hdclearart";
	private static final String CHARACTER_ARTS = "characterart";
	private static final String SEASON_BANNERS = "seasonbanner";

	@JsonProperty(NAME)
	private final String name;
	@JsonProperty(THE_TV_DB_ID)
	private final String theTvDbId;
	@JsonProperty(CLEAR_LOGOS)
	private final Set<Fanart> clearLogos;
	@JsonProperty(HD_TV_LOGOS)
	private final Set<Fanart> hdTvLogos;
	@JsonProperty(TV_THUMBS)
	private final Set<Fanart> tvThumbs;
	@JsonProperty(SEASON_POSTERS)
	private final Set<Season> seasonPosters;
	@JsonProperty(TV_POSTERS)
	private final Set<Fanart> tvPosters;
	@JsonProperty(SHOW_BACKGROUNDS)
	private final Set<Season> showBackgrounds;
	@JsonProperty(CLEAR_ARTS)
	private final Set<Fanart> cleararts;
	@JsonProperty(TV_BANNERS)
	private final Set<Fanart> tvBanners;
	@JsonProperty(SEASON_THUMBS)
	private final Set<Season> seasonThumbs;
	@JsonProperty(HD_CLEARARTS)
	private final Set<Fanart> hdCleararts;
	@JsonProperty(CHARACTER_ARTS)
	private final Set<Fanart> characterArts;
	@JsonProperty(SEASON_BANNERS)
	private final Set<Season> seasonBanners;

	@JsonCreator
	private FanarttvFanarts(@JsonProperty(NAME) String name,
							@JsonProperty(THE_TV_DB_ID) String theTvDbId,
							@JsonProperty(CLEAR_LOGOS) Set<Fanart> clearLogos,
							@JsonProperty(HD_TV_LOGOS) Set<Fanart> hdTvLogos,
							@JsonProperty(TV_THUMBS) Set<Fanart> tvThumbs,
							@JsonProperty(SEASON_POSTERS) Set<Season> seasonPosters,
							@JsonProperty(TV_POSTERS) Set<Fanart> tvPosters,
							@JsonProperty(SHOW_BACKGROUNDS) Set<Season> showBackgrounds,
							@JsonProperty(CLEAR_ARTS) Set<Fanart> cleararts,
							@JsonProperty(TV_BANNERS) Set<Fanart> tvBanners,
							@JsonProperty(SEASON_THUMBS) Set<Season> seasonThumbs,
							@JsonProperty(HD_CLEARARTS) Set<Fanart> hdCleararts,
							@JsonProperty(CHARACTER_ARTS) Set<Fanart> characterArts,
							@JsonProperty(SEASON_BANNERS) Set<Season> seasonBanners) {
		this.name = name;
		this.theTvDbId = theTvDbId;
		this.clearLogos = clearLogos;
		this.hdTvLogos = hdTvLogos;
		this.tvThumbs = tvThumbs;
		this.seasonPosters = seasonPosters;
		this.tvPosters = tvPosters;
		this.showBackgrounds = showBackgrounds;
		this.cleararts = cleararts;
		this.tvBanners = tvBanners;
		this.seasonThumbs = seasonThumbs;
		this.hdCleararts = hdCleararts;
		this.characterArts = characterArts;
		this.seasonBanners = seasonBanners;
	}

	public void fill(TvSerie tvSerie) {
		if (this.clearLogos != null) {
			for (Fanart clearLogo : this.clearLogos) {
				clearLogo.fill(tvSerie, FanarttvFanartType.ClearLogo);
			}
		}
		if (this.hdTvLogos != null) {
			for (Fanart hdTvLogo : this.hdTvLogos) {
				hdTvLogo.fill(tvSerie, FanarttvFanartType.HdTvLogo);
			}
		}
		if (this.tvThumbs != null) {
			for (Fanart tvThumb : this.tvThumbs) {
				tvThumb.fill(tvSerie, FanarttvFanartType.TvThumb);
			}
		}
		if (this.seasonPosters != null) {
			for (Season seasonPoster : this.seasonPosters) {
				seasonPoster.fill(tvSerie, FanarttvFanartType.SeasonPoster);
			}
		}
		if (this.tvPosters != null) {
			for (Fanart tvPoster : this.tvPosters) {
				tvPoster.fill(tvSerie, FanarttvFanartType.TvPoster);
			}
		}
		if (this.showBackgrounds != null) {
			for (Season showBackground : this.showBackgrounds) {
				showBackground.fill(tvSerie, FanarttvFanartType.ShowBackground);
			}
		}
		if (this.cleararts != null) {
			for (Fanart clearart : this.cleararts) {
				clearart.fill(tvSerie, FanarttvFanartType.Clearart);
			}
		}
		if (this.tvBanners != null) {
			for (Fanart tvBanner : this.tvBanners) {
				tvBanner.fill(tvSerie, FanarttvFanartType.TvBanner);
			}
		}
		if (this.hdCleararts != null) {
			for (Fanart hdClearart : this.hdCleararts) {
				hdClearart.fill(tvSerie, FanarttvFanartType.HdClearart);
			}
		}
		if (this.characterArts != null) {
			for (Fanart characterArt : this.characterArts) {
				characterArt.fill(tvSerie, FanarttvFanartType.CharacterArt);
			}
		}
	}

	private static class Fanart {

		private static final String ID = "id";
		private static final String URL = "url";
		private static final String LANG = "lang";
		private static final String LIKES = "likes";

		@JsonProperty(ID)
		protected final String id;
		@JsonProperty(URL)
		protected final String url;
		@JsonProperty(LANG)
		protected final String lang;
		@JsonProperty(LIKES)
		protected final String likes;

		@JsonCreator
		private Fanart(@JsonProperty(ID) String id,
					   @JsonProperty(URL) String url,
					   @JsonProperty(LANG) String lang,
					   @JsonProperty(LIKES) String likes) {
			this.id = id;
			this.url = url;
			this.lang = lang;
			this.likes = likes;
		}

		private void fill(TvSerie tvSerie, FanarttvFanartType type) {
			tvSerie.addFanarttvFanart(type.getFanart(), this.url, getLikes(), this.lang != null ? EnhancedLocaleMap.getByLanguage(this.lang) : EnhancedLocaleMap.getEmptyLocale());
		}
		
		private Integer getLikes() {
			Integer result = null;
			
			if (StringUtils.isNotBlank(this.likes) && !this.likes.equals("0")) {
				result = Integer.valueOf(this.likes);
			}
			
			return result;
		}

	}

	private static class Season extends Fanart {

		private static final String SEASON = "season";

		@JsonProperty(SEASON)
		private final String season;

		@JsonCreator
		private Season(@JsonProperty(Fanart.ID) String id,
					   @JsonProperty(Fanart.URL) String url,
					   @JsonProperty(Fanart.LANG) String lang,
					   @JsonProperty(Fanart.LIKES) String likes,
					   @JsonProperty(SEASON) String season) {
			super(id, url, lang, likes);

			this.season = season;
		}

		private void fill(TvSerie tvSerie, FanarttvFanartType type) {
			if (type == FanarttvFanartType.SeasonPoster) {
				tvSerie.addFanarttvSeasonImage(this.url, Integer.valueOf(this.season), super.getLikes(), this.lang != null ? EnhancedLocaleMap.getByLanguage(this.lang) : EnhancedLocaleMap.getEmptyLocale());
			}
			else {
				super.fill(tvSerie, type);
			}
		}

	}

}
