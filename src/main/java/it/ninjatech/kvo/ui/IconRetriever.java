package it.ninjatech.kvo.ui;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public final class IconRetriever {

	public enum IconName {
		
		Folder("folder.png"),
		Folder_Movies("folder_movies.png"),
		Folder_TvSeries("folder_tvseries.png"),
		TvSerie("tvserie.png");
		
		private final String value; 
		
		private IconName(String name) {
			this.value = String.format("/images/%s", name);
		}
		
	}

	private static final int EXPLORER_TREE_ICON_SIZE = 16;
	private static final int EXPLORER_TREE_MENU_ICON_SIZE = 50;
	private static final int EXPLORER_TREE_TAB_ICON_SIZE = 32;
	
	private static ImageIcon explorerTreeFolder;
	private static ImageIcon explorerTreeFolderMovies;
	private static ImageIcon explorerTreeFolderTvSeries;
	private static ImageIcon explorerTreeFolderMoviesMenu;
	private static ImageIcon explorerTreeFolderTvSeriesMenu;
	private static ImageIcon explorerTreeFolderTab;
	private static ImageIcon explorerTreeFolderMoviesTab;
	private static ImageIcon explorerTreeFolderTvSeriesTab;
	private static ImageIcon explorerTreeTvSerie;
	
	public static ImageIcon retrieveExplorerTreeFolder() {
		if (explorerTreeFolder == null) {
			explorerTreeFolder = retrieveAndScaleIcon(IconName.Folder, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeFolder;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderMovies() {
		if (explorerTreeFolderMovies == null) {
			explorerTreeFolderMovies = retrieveAndScaleIcon(IconName.Folder_Movies, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeFolderMovies;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderTvSeries() {
		if (explorerTreeFolderTvSeries == null) {
			explorerTreeFolderTvSeries = retrieveAndScaleIcon(IconName.Folder_TvSeries, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeFolderTvSeries;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderMoviesMenu() {
		if (explorerTreeFolderMoviesMenu == null) {
			explorerTreeFolderMoviesMenu = retrieveAndScaleIcon(IconName.Folder_Movies, EXPLORER_TREE_MENU_ICON_SIZE);
		}
		
		return explorerTreeFolderMoviesMenu;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderTvSeriesMenu() {
		if (explorerTreeFolderTvSeriesMenu == null) {
			explorerTreeFolderTvSeriesMenu = retrieveAndScaleIcon(IconName.Folder_TvSeries, EXPLORER_TREE_MENU_ICON_SIZE);
		}
		
		return explorerTreeFolderTvSeriesMenu;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderTab() {
		if (explorerTreeFolderTab == null) {
			explorerTreeFolderTab = retrieveAndScaleIcon(IconName.Folder, EXPLORER_TREE_TAB_ICON_SIZE);
		}
		
		return explorerTreeFolderTab;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderMoviesTab() {
		if (explorerTreeFolderMoviesTab == null) {
			explorerTreeFolderMoviesTab = retrieveAndScaleIcon(IconName.Folder_Movies, EXPLORER_TREE_TAB_ICON_SIZE);
		}
		
		return explorerTreeFolderMoviesTab;
	}
	
	public static ImageIcon retrieveExplorerTreeFolderTvSeriesTab() {
		if (explorerTreeFolderTvSeriesTab == null) {
			explorerTreeFolderTvSeriesTab = retrieveAndScaleIcon(IconName.Folder_TvSeries, EXPLORER_TREE_TAB_ICON_SIZE);
		}
		
		return explorerTreeFolderTvSeriesTab;
	}
	
	public static ImageIcon retrieveExplorerTreeTvSerie() {
		if (explorerTreeTvSerie == null) {
			explorerTreeTvSerie = retrieveAndScaleIcon(IconName.TvSerie, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeTvSerie;
	}
	
	private static URL retrieveIcon(IconName icon) {
		return IconRetriever.class.getResource(icon.value);
	}
	
	private static ImageIcon retrieveAndScaleIcon(IconName iconName, int size) {
		ImageIcon result = null;

		result = new ImageIcon(retrieveIcon(iconName));

		double scaleFactor = (double)size / (double)result.getIconWidth();

		if (scaleFactor != 0d) {
			result = new ImageIcon(result.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
		}

		return result;
	}
	
	private IconRetriever() {}
	
}
