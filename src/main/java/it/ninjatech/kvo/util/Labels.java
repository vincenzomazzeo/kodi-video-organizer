package it.ninjatech.kvo.util;

import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;

public final class Labels {

	public static final String APPLICATION_TITLE = "Kodi Video Organizer";
	
	public static final String ACTORS = "Actors";
	public static final String API_KEY = "API Key";
	public static final String CLICK_FOR_DETAIL = "Click for detail";
	public static final String CLICK_TO_OPEN_IN_SYSTEM_EXPLORER = "Open in System Explorer";
	public static final String CONFIRM = "Confirm";
	public static final String DIRECTORS = "Directors";
	public static final String ENABLED = "Enabled";
	public static final String EPISODE_SUBTITLE = "Episode Subtitle";
	public static final String EXCEPTION_CONSOLE = "Exception Console";
	public static final String EXIT = "Exit";
	public static final String EXTRA_FANARTS = "Extra Fanarts";
	public static final String FAILED_TO_CREATE_SETTINGS_FILE = "Failed to create settings file";
	public static final String FANARTS = "Fanarts";
	public static final String FETCHING_DATA = "Fetching data";
	public static final String FETCH = "Fetch";
	public static final String FILENAME = "Filename";
	public static final String GUEST_STARS = "Guest Stars";
	public static final String ID = "ID";
	public static final String IMDB = "Find out more on IMDb";
	public static final String LANGUAGE = "Language";
	public static final String LOADING_EXTRA_FANARTS = "Loading Extra Fanarts";
	public static final String MOVIE = "Movie";
	public static final String MOVIES = "Movies";
	public static final String NEITHER_IMAGE_NOR_IMDB = "Neither image nor IMDB link available for this person";
	public static final String NO_TV_SERIE_FOUND_ASK_FOR_SEARCH = "No TV Serie found. Do you want to search again changing name or languange?";
	public static final String OPEN_IN_SYSYEM_EXPLORER = "Open in System Explorer";
	public static final String OVERVIEW = "Overview";
	public static final String PREFERRED_LANGUAGE = "Preferred Language";
	public static final String ROOTS = "Roots";
	public static final String SAVING_SEASON = "Saving Season";
	public static final String SCANNING = "Scanning...";
	public static final String SCANNING_EXTRAFANARTS = "Scanning Extrafanarts";
	public static final String SCRAPERS_SETTINGS = "Scrapers Settings";
	public static final String SEARCH = "Search";
	public static final String SEARCH_FOR_TV_SERIE = "Search for TV Serie";
	public static final String SEARCHING_FOR_TV_SERIE = "Searching for TV Serie";
	public static final String SEASONS = "Seasons";
	public static final String START_SCANNING = "Start scanning...";
	public static final String STORING_SCRAPERS_SETTINGS = "Storing Scrapers Settings";
	public static final String STORING_SETTINGS = "Storing Settings";
	public static final String STORING_TV_SERIE_SEASON = "Storing Tv Serie Season data";
	public static final String SUBTITLE = "Subtitle";
	public static final String SUBTITLE_FILES = "Subtitle Files";
	public static final String TITLE = "Title";
	public static final String TV_SERIE = "Tv Serie";
	public static final String TV_SERIES = "Tv Series";
	public static final String TV_SERIE_SEARCH_MULTI_RESULT = "TV Serie Search Multi Result";
	public static final String TV_SERIE_SEASON_CREATION = "TV Serie Season Creation";
	public static final String VIDEO = "Video";
	public static final String VIDEO_FILES = "Video Files";
	public static final String WRITERS = "Writers";
	public static final String WRONG_API_KEY = "Wrong API Key";
	
	public static final String LOADING_EPISODE_IMAGE = "Loading episode image";
	public static final String LOADING_SEASON_IMAGE = "Loading season image";
	
	public static final String NOTIFICATION_THE_TV_DB_NOT_CONFIGURED = "<html><b>TheTVDB</b> scraper is <u>not</u> configured. Go to <b>Scrapers Settings</b> to configure it</html>";
	
	public static final String TOOLTIP_CREATE = "<html><div align='center'>Left click to create</div></html>";
	public static final String TOOLTIP_CREATE_FULL = "<html><div align='center'>Left click to create<br />Right click for full size image</div></html>";
	public static final String TOOLTIP_EXPLORER_ROOTS = "<html><div align='center'>Here you can add your video root folders<br />Just <i>CTRL + click</i> to add a new root!</div></html>";
	public static final String TOOLTIP_HANDLE = "<html><div align='center'>Left click to handle</div></html>";
	public static final String TOOLTIP_HANDLE_FULL = "<html><div align='center'>Left click to handle<br />Right click for full size image</div></html>";
	public static final String TOOLTIP_IMAGE_CHANGE_FULL = "<html><div align='center'>Left click to change<br />Right click for full size image</div></html>";
	public static final String TOOLTIP_IMAGE_CHANGE = "<html><div align='center'>Left click to change</div></html>";
	public static final String TOOLTIP_IMAGE_FULL = "<html><div align='center'>Right click for full size image</div></html>";
	public static final String TOOLTIP_IMAGE_SELECT_FULL = "<html><div align='center'>Left click to select<br />Right click for full size image</div></html>";
	
	public static String dbSavingEntity(String entity) {
		return String.format("Saving %s", entity);
	}
	
	public static String dbSavedEntity(String entity) {
		return String.format("%s saved", entity);
	}
	
	public static String getFailedToRename(String from, String to) {
		return String.format("Failed to rename '%s' to '%s'", from, to);
	}
	
	public static String getTvSerieTitleSeason(TvSerieSeason season) {
		return String.format("%s - Season %s", TvSerieHelper.getTitle(season.getTvSerie().getTvSeriePathEntity()), season.getNumber());
	}
	
	public static String getTvSerieSeason(TvSerieSeason season) {
		return String.format("Season %d", season.getNumber());
	}
	
	public static String getTvSerieSeasonEpisodeCount(TvSerieSeason season) {
		return String.format("Episodes: %d", season.getEpisodeCount());
	}
	
	public static String getTvSerieSeasonCreationMessage(TvSerieSeason season) {
		return String.format("<html>Do you want to create <b>%s - %s</b> folder</html>", TvSerieHelper.getTitle(season.getTvSerie().getTvSeriePathEntity()), getTvSerieSeason(season));
	}
	
	public static String getTvSerieSeasonCreationFailMessage(TvSerieSeason season) {
		return String.format("<html><b>%s - %s</b> folder creation failed</html>", TvSerieHelper.getTitle(season.getTvSerie().getTvSeriePathEntity()), getTvSerieSeason(season));
	}
	
	public static String getTvSerieSeasonCreationSuccessMessage(TvSerieSeason season) {
		return String.format("<html><b>%s - %s</b> folder created</html>", TvSerieHelper.getTitle(season.getTvSerie().getTvSeriePathEntity()), getTvSerieSeason(season));
	}
	
	public static String getFirstAired(String firstAired) {
		return String.format("First Aired: %s", firstAired);
	}
	
	public static String getNetwork(String network) {
		return String.format("Network: %s", network);
	}
	
	public static String getTooltipTvSerieEpisodeTileVideoFile(String filename, boolean removable) {
		return String.format("<html><div align='center'>%s%s</div></html>", filename, removable ? "<br />Right click to remove" : "");
	}
	
	public static String getTooltipTvSerieEpisodeTileSubtitleFile(String filename, boolean removable) {
		return String.format("<html><div align='center'>%s%s</div></html>", filename, removable ? "<br />Right click to remove" : "");
	}

	public static String getScanningRoot(String typePlural, String root) {
		return String.format("Scanning %s root %s", typePlural, root);
	}
	
	public static String getScanning(String item) {
		return String.format("Scanning %s", item);
	}
	
	public static String getChooseRoot(String root) {
		return String.format("Choose %s root", root);
	}
	
	public static String getInsertScraperApiKey(String scraper) {
		return String.format("Insert %s API Key", scraper);
	}
	
	public static String getContactingScraper(String scraper) {
		return String.format("Contacting %s", scraper);
	}
	
	public static String getNoFanartTryRefresh(String fanart) {
		return String.format("No %s fanart found. Try to refresh TV Serie.", fanart);
	}
	
	public static String notificationRootAdded(String typePlural, String root) {
		return String.format("<html>%s root <b>%s</b> added</html>", typePlural, root);
	}
	
	public static String notificationTvSerieFetched(String tvSerie) {
		return String.format("<html>TV Serie <b>%s</b> fetched</html>", tvSerie);
	}
	
	private Labels() {}
	
}
