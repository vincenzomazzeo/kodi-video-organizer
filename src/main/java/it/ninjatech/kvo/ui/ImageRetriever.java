package it.ninjatech.kvo.ui;

import java.awt.Image;

import javax.swing.ImageIcon;

public final class ImageRetriever {

	private enum ImageName {
		
		Apikey("apikey.jpg"),
		ExceptionConsole("exception_console.png"),
		Folder("folder.png"),
		Folder_Movies("folder_movies.png"),
		Folder_TvSeries("folder_tvseries.png"),
		Scrapers_Settings("scrapers_settings.png"),
		TvSerie("tvserie.png"),
		TheTVDB_Logo("thetvdb_logo.png");
		
		private final String value; 
		
		private ImageName(String name) {
			this.value = String.format("/images/%s", name);
		}
		
	}

	private static final int APIKEY_SIZE = 40;
	private static final int EXPLORER_TREE_ICON_SIZE = 16;
	private static final int EXPLORER_TREE_MENU_ICON_SIZE = 50;
	private static final int EXPLORER_TREE_TAB_ICON_SIZE = 32;
	private static final int MENU_BAR_BUTTON_SIZE = 40;
	private static final int THE_TV_DB_LOGO_SIZE = 300;
	
	private static ImageIcon apikey;
	private static ImageIcon explorerTreeFolder;
	private static ImageIcon explorerTreeFolderMovies;
	private static ImageIcon explorerTreeFolderTvSeries;
	private static ImageIcon explorerTreeFolderMoviesMenu;
	private static ImageIcon explorerTreeFolderTvSeriesMenu;
	private static ImageIcon explorerTreeFolderTab;
	private static ImageIcon explorerTreeFolderMoviesTab;
	private static ImageIcon explorerTreeFolderTvSeriesTab;
	private static ImageIcon explorerTreeTvSerie;
	private static ImageIcon theTvDbLogo;
	private static ImageIcon toolBarExceptionConsole;
	private static ImageIcon toolBarScrapersSettings;
	
	public static ImageIcon retrieveApikey() {
		if (apikey == null) {
			apikey = retrieveAndScaleImage(ImageName.Apikey, APIKEY_SIZE);
		}
		
		return apikey;
	}
	
	public static ImageIcon retrieveExplorerTreeFolder() {
		if (explorerTreeFolder == null) {
			explorerTreeFolder = retrieveAndScaleImage(ImageName.Folder, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeFolder;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderMovies() {
		if (explorerTreeFolderMovies == null) {
			explorerTreeFolderMovies = retrieveAndScaleImage(ImageName.Folder_Movies, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeFolderMovies;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderTvSeries() {
		if (explorerTreeFolderTvSeries == null) {
			explorerTreeFolderTvSeries = retrieveAndScaleImage(ImageName.Folder_TvSeries, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeFolderTvSeries;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderMoviesMenu() {
		if (explorerTreeFolderMoviesMenu == null) {
			explorerTreeFolderMoviesMenu = retrieveAndScaleImage(ImageName.Folder_Movies, EXPLORER_TREE_MENU_ICON_SIZE);
		}
		
		return explorerTreeFolderMoviesMenu;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderTvSeriesMenu() {
		if (explorerTreeFolderTvSeriesMenu == null) {
			explorerTreeFolderTvSeriesMenu = retrieveAndScaleImage(ImageName.Folder_TvSeries, EXPLORER_TREE_MENU_ICON_SIZE);
		}
		
		return explorerTreeFolderTvSeriesMenu;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderTab() {
		if (explorerTreeFolderTab == null) {
			explorerTreeFolderTab = retrieveAndScaleImage(ImageName.Folder, EXPLORER_TREE_TAB_ICON_SIZE);
		}
		
		return explorerTreeFolderTab;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderMoviesTab() {
		if (explorerTreeFolderMoviesTab == null) {
			explorerTreeFolderMoviesTab = retrieveAndScaleImage(ImageName.Folder_Movies, EXPLORER_TREE_TAB_ICON_SIZE);
		}
		
		return explorerTreeFolderMoviesTab;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderTvSeriesTab() {
		if (explorerTreeFolderTvSeriesTab == null) {
			explorerTreeFolderTvSeriesTab = retrieveAndScaleImage(ImageName.Folder_TvSeries, EXPLORER_TREE_TAB_ICON_SIZE);
		}
		
		return explorerTreeFolderTvSeriesTab;
	}
	
	public static ImageIcon retrieveExplorerTreeTvSerie() {
		if (explorerTreeTvSerie == null) {
			explorerTreeTvSerie = retrieveAndScaleImage(ImageName.TvSerie, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeTvSerie;
	}
	
	public static ImageIcon retrieveTheTvDbLogo() {
		if (theTvDbLogo == null) {
			theTvDbLogo = retrieveAndScaleImageByWidth(ImageName.TheTVDB_Logo, THE_TV_DB_LOGO_SIZE);
		}
		
		return theTvDbLogo;
	}
	
	public static ImageIcon retrieveToolBarExceptionConsole() {
		if (toolBarExceptionConsole == null) {
			toolBarExceptionConsole = retrieveAndScaleImage(ImageName.ExceptionConsole, MENU_BAR_BUTTON_SIZE);
		}
		
		return toolBarExceptionConsole;
	}
	
	public static ImageIcon retrieveToolBarScrapersSettings() {
		if (toolBarScrapersSettings == null) {
			toolBarScrapersSettings = retrieveAndScaleImage(ImageName.Scrapers_Settings, MENU_BAR_BUTTON_SIZE);
		}
		
		return toolBarScrapersSettings;
	}
	
	private static ImageIcon retrieveImage(ImageName imageName) {
		return new ImageIcon(ImageRetriever.class.getResource(imageName.value));
	}
	
	private static ImageIcon retrieveAndScaleImage(ImageName imageName, int size) {
		ImageIcon result = null;

		result = retrieveImage(imageName);

		double scaleFactor = (double)size / (double)result.getIconWidth();

		if (scaleFactor != 0d) {
			result = new ImageIcon(result.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
		}

		return result;
	}
	
	private static ImageIcon retrieveAndScaleImageByWidth(ImageName imageName, int width) {
		ImageIcon result = null;

		result = retrieveImage(imageName);

		double scaleFactor = (double)width / (double)result.getIconWidth();
		double height = scaleFactor * (double)result.getIconHeight();

		if (scaleFactor != 0d) {
			result = new ImageIcon(result.getImage().getScaledInstance(width, (int)height, Image.SCALE_SMOOTH));
		}

		return result;
	}
	
	private ImageRetriever() {}
	
}
