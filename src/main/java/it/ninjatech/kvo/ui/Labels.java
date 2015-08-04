package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;

public final class Labels {

	public static final String CLICK_FOR_DETAIL = "Click for detail";
	public static final String CLICK_TO_OPEN_IN_SYSTEM_EXPLORER = "Open in System Explorer";
	public static final String DIRECTORS = "Directors";
	public static final String EXCEPTION_CONSOLE = "Exception Console";
	public static final String FILENAME = "Filename";
	public static final String GUEST_STARS = "Guest Stars";
	public static final String IMDB = "Find out more on IMDb";
	public static final String LANGUAGE = "Language";
	public static final String SCRAPERS_SETTINGS = "Scrapers Settings";
	public static final String SUBTITLE_FILES = "Subtitle Files";
	public static final String VIDEO_FILES = "Video Files";
	public static final String WRITERS = "Writers";
	
	public static final String LOADING_EPISODE_IMAGE = "Loading episode image";
	public static final String LOADING_SEASON_IMAGE = "Loading season image";
	
	public static final String TOOLTIP_IMAGE_CHANGE_FULL = "<html><div align='center'>Left click to change<br />Right click for full size image</div></html>";
	public static final String TOOLTIP_IMAGE_CHANGE = "<html><div align='center'>Left click to change</div></html>";
	public static final String TOOLTIP_IMAGE_FULL = "<html><div align='center'>Right click for full size image</div></html>";
	public static final String TOOLTIP_IMAGE_SELECT_FULL = "<html><div align='center'>Left click to select<br />Right click for full size image</div></html>";
	
	public static String getTvSerieTitleSeason(TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season) {
		return String.format("%s - Season %s", TvSerieUtils.getTitle(tvSeriePathEntity), season.getNumber());
	}
	
	public static String getTvSerieSeason(TvSerieSeason season) {
		return String.format("Season %d", season.getNumber());
	}
	
	public static String getTvSerieSeasonEpisodeCount(TvSerieSeason season) {
		return String.format("Episodes: %d", season.getEpisodeCount());
	}
	
	public static String getFirstAired(String firstAired) {
		return String.format("First Aired: %s", firstAired);
	}
	
	public static String getTooltipTvSerieEpisodeTileVideoFile(String filename, boolean removable) {
		return String.format("<html><div align='center'>%s%s</div></html>", filename, removable ? "<br />Right click to remove" : "");
	}
	
	public static String getTooltipTvSerieEpisodeTileSubtitleFile(String filename, boolean removable) {
		return String.format("<html><div align='center'>%s%s</div></html>", filename, removable ? "<br />Right click to remove" : "");
	}
	
	private Labels() {}
	
}
