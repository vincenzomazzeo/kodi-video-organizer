package it.ninjatech.kvo.util;

import java.util.Set;

import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;

public final class Labels {

	public static final String APPLICATION_TITLE = "Kodi Video Organizer";
	
	public static final String ACTORS = "Actors";
	public static final String ADAPT_PATH_TO_NAME = "Adapt Path to Name";
	public static final String ADD_TV_SERIE = "Add TV Serie";
	public static final String API_KEY = "API Key";
	public static final String CLICK_FOR_DETAIL = "Click for detail";
	public static final String CLICK_TO_OPEN_IN_SYSTEM_EXPLORER = "Open in System Explorer";
	public static final String CONFIRM = "Confirm";
	public static final String DIRECTORS = "Directors";
	public static final String ENABLED = "Enabled";
	public static final String EPISODE_SUBTITLE = "Episode Subtitle";
	public static final String ERROR = "Error";
	public static final String EXCEPTION_CONSOLE = "Exception Console";
	public static final String EXIT = "Exit";
	public static final String EXTRA_FANARTS = "Extra Fanarts";
	public static final String FAILED_TO_CREATE_SETTINGS_FILE = "Failed to create settings file";
	public static final String FAILED_TO_UPDATE_SEASON = "Failed to update season";
	public static final String FANARTS = "Fanarts";
	public static final String FETCHING_DATA = "Fetching data";
	public static final String FETCH = "Fetch";
	public static final String FILENAME = "Filename";
	public static final String FOLLOWING_TV_SERIES_MISSING = "The following TV Series were not found...";
	public static final String GUEST_STARS = "Guest Stars";
	public static final String ID = "ID";
	public static final String IGNORE_TV_SERIE = "Ignore this TV Serie";
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
	public static final String REMOVE = "Remove";
	public static final String REMOVE_FROM_DISK = "Remove from disk (cannot be undone)";
	public static final String REMOVE_TV_SERIE = "Remove TV Serie";
	public static final String REMOVE_TV_SERIES_ROOT = "Remove TV Serie Root";
	public static final String ROOTS = "Roots";
	public static final String SCAN = "Scan";
	public static final String SCAN_RECURSIVE = "Recursive Scan";
	public static final String SCANNING = "Scanning...";
	public static final String SCANNING_EXTRAFANARTS = "Scanning Extrafanarts";
	public static final String SCRAPERS_SETTINGS = "Scrapers Settings";
	public static final String SEARCH = "Search";
	public static final String SEARCH_FOR_TV_SERIE = "Search for TV Serie";
	public static final String SEARCHING_FOR_TV_SERIE = "Searching for TV Serie";
	public static final String SEARCHING_FOR_TV_SERIES = "Searching for TV Series";
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
	
	/* TV Serie Workers */
	public static final String TV_SERIE_TASK_CHECK = "Checking existence...";
	public static final String TV_SERIE_TASK_SCAN = "Scanning files...";
	public static final String TV_SERIE_TASK_FETCH_THE_TV_DB = "Retrieving data from TheTvDB...";
	public static final String TV_SERIE_TASK_FETCH_FANART_TV = "Retrieving data from Fanart.TV...";
	public static final String TV_SERIE_TASK_DELETE = "Deleting data from DB...";
	public static final String TV_SERIE_TASK_SAVE = "Saving data into DB...";
	
	//
	
	public static final String TV_SERIE_WORKER_ADD_ROOT_1 = "Preparing to adding TV Serie root %s";
	public static final String TV_SERIE_WORKER_ADD_ROOT_2 = "(1/3) Creating data for root %s";
	public static final String TV_SERIE_WORKER_ADD_ROOT_3 = "(2/3) Scanning root %s";
	public static final String TV_SERIE_WORKER_ADD_ROOT_4 = "(3/3) Saving data for root %s";
	
	public static String tvSerieWorkerAddRoot(String message, String root) {
	    return String.format(message, root);
	}
	
	private static final String TV_SERIE_WORKER_FETCH_1 = "(%d/%d) %s";
	
	public static String tvSerieWorkerFetch(int current, int total, String name) {
	    return String.format(TV_SERIE_WORKER_FETCH_1, current, total, name);
	}
	
	private static final String TV_SERIE_WORKER_SCAN_1 = "%s";
	
	public static String tvSerieWorkerScan(String name) {
        return String.format(TV_SERIE_WORKER_SCAN_1, name);
    }
	
	private static final String TV_SERIE_WORKER_ADAPT_PATH_TO_NAME_1 = "%s";
    
    public static String tvSerieWorkerAdaptPathToName(String name) {
        return String.format(TV_SERIE_WORKER_ADAPT_PATH_TO_NAME_1, name);
    }
	
	private static final String TV_SERIE_WORKER_REMOVE_1 = "Removing TV Serie %s";
    
    public static String tvSerieWorkerRemove(String name) {
        return String.format(TV_SERIE_WORKER_REMOVE_1, name);
    }
    
    private static final String TV_SERIES_WORKER_REMOVE_1 = "Removing TV Serie root %s";
    
    public static String tvSeriesWorkerRemove(String name) {
        return String.format(TV_SERIES_WORKER_REMOVE_1, name);
    }
	
	public static final String TV_SERIE_SEASON_WORKER_1 = "Saving TV Serie %s - Season %d";
	public static final String TV_SERIE_SEASON_WORKER_2 = "%s -> Video";
	public static final String TV_SERIE_SEASON_WORKER_3 = "%s -> Subtitle";
	
	public static String tvSerieSeasonWorker(String message, String name, Integer season) {
	    return String.format(message, name, season);
	}
	
	private static final String TV_SERIE_SEASON_CREATE_WORKER_1 = "Creating TV Serie %s - Season %d";
	
	public static String tvSerieSeasonCreateWorker(String name, Integer season) {
        return String.format(TV_SERIE_SEASON_CREATE_WORKER_1, name, season);
    }
	
	public static final String TV_SERIE_SCAN_ROOT_WORKER_1 = "Scanning root %s";
	public static final String TV_SERIE_SCAN_ROOT_WORKER_2 = "(%2$d/%3$d) %1$s";
	
	public static String tvSerieScanRootWorker(String message, String name, int current, int total) {
	    return String.format(message, name, current, total);
	}
	
	/********************/
	
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
	
	public static String getTvSeriesRootAlreadyExists(String root) {
        return String.format("<html>Tv Series root <b>%s</b> already exists</html>", root);
    }
	
	public static String getTvSerieRemove(String tvSerie) {
	    return String.format("Are you sure you want to remove TV Serie %s?", tvSerie);
	}
	
	public static String getTvSeriesRootRemove(String root) {
	    return String.format("Are you sure you want to remove TV Serie root %s?", root);
	}
	
	public static String notificationRootAdded(String typePlural, String root) {
		return String.format("<html>%s root <b>%s</b> added</html>", typePlural, root);
	}
	
	public static String notificationTvSerieFetched(String tvSerie) {
		return String.format("<html>TV Serie <b>%s</b> fetched</html>", tvSerie);
	}
	
	public static String notificationTvSeriesRootRemoved(String root) {
	    return String.format("<html>The TV Serie root <b>%s</b> is no longer present and has been removed</html>", root);
	}
	
	public static String notificationTvSeriesRefreshRemove(Set<TvSeriePathEntity> entitiesRefreshed,
	                                                       Set<TvSeriePathEntity> entitiesRemoved) {
	    StringBuilder result = new StringBuilder("<html>");
	    if (!entitiesRefreshed.isEmpty()) {
	        result.append("<p>The following TV Series were refreshed<ul>");
	        for (TvSeriePathEntity entity : entitiesRefreshed) {
	            result.append("<li><b>").append(entity.getLabel()).append("</b></li>");
	        }
	        result.append("</ul></p>");
	    }
	    if (!entitiesRemoved.isEmpty()) {
	        result.append("<br /><p>The following TV Series are no longer present and have been removed<ul>");
            for (TvSeriePathEntity entity : entitiesRemoved) {
                result.append("<li><b>").append(entity.getLabel()).append("</b></li>");
            }
            result.append("</ul></p>");
	    }
	    result.append("</html>");
	    
	    return result.toString();
	}
	
	private Labels() {}
	
}
