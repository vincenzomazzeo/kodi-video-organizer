package it.ninjatech.kvo.ui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;

public final class ImageRetriever {

	private enum ImageName {

		Apikey("apikey.jpg"),
		ArrowLeft("arrow_left.png"),
		ArrowRight("arrow_right.png"),
		Baloon("baloon.png"),
		Cancel("cancel.png"),
		ContentRating_TV14("TV-14.png"),
		ContentRating_TVG("TV-G.png"),
		ContentRating_TVMA("TV-MA.png"),
		ContentRating_TVPG("TV-PG.png"),
		ContentRating_TVY("TV-Y.png"),
		ContentRating_TVY7("TV-Y7.png"),
		ExceptionConsole("exception_console.png"),
		ExtraFanarts("extra_fanarts.png"),
		Fanarttv_Logo("fanarttv_logo.png"),
		Folder("folder.png"),
		Folder_Movies("folder_movies.png"),
		Folder_TvSeries("folder_tvseries.png"),
		IMDb_Logo("imdb_logo.png"),
		Loading("loading.gif"),
		Ok("ok.png"),
		Scrapers_Settings("scrapers_settings.png"),
		Star("star.png"),
		TvSerie("tvserie.png"),
		TvSerie_Tile_Poster("tvserie_tile_poster.png"),
		TheTVDB_Logo("thetvdb_logo.png");

		private final String value;

		private ImageName(String name) {
			this.value = String.format("/images/%s", name);
		}

	}

	private static final int APIKEY_SIZE = 40;
	private static final int DIALOG_BUTTON_SIZE = 32;
	public static final int EPISODE_TILE_VIDEO_FILE_SIZE = 40;
	private static final int EXPLORER_TREE_ICON_SIZE = 16;
	private static final int EXPLORER_TREE_MENU_ICON_SIZE = 50;
	private static final int EXPLORER_TREE_TAB_ICON_SIZE = 32;
	private static final int FANART_CHOICE_LOGO_SIZE = 75;
	private static final int FANARTTV_LOGO_SIZE = 285;
	private static final int IMDB_LOGO_SIZE = 119;
	private static final int MENU_BAR_BUTTON_SIZE = 40;
	private static final int THE_TV_DB_LOGO_SIZE = 300;
	private static final int WALL_ARROR_SIZE = 32;
	private static final int WALL_BALOON_SIZE = 30;
	private static final int WALL_CONTENT_RATING_SIZE = 20;
	private static final int WALL_EXTRA_FANARTS_SIZE = 25;
	private static final int WALL_IMDB_SIZE = 20;
	private static final int WALL_STAR_SIZE = 40;

	private static ImageIcon episodeTileVideoFile;
	private static ImageIcon explorerTilePosterTvSerie;
	private static ImageIcon explorerTreeFolder;
	private static ImageIcon explorerTreeFolderMovies;
	private static ImageIcon explorerTreeFolderTvSeries;
	private static ImageIcon explorerTreeFolderMoviesMenu;
	private static ImageIcon explorerTreeFolderTvSeriesMenu;
	private static ImageIcon explorerTreeFolderTab;
	private static ImageIcon explorerTreeFolderMoviesTab;
	private static ImageIcon explorerTreeFolderTvSeriesTab;
	private static ImageIcon explorerTreeTvSerie;
	private static ImageIcon loading;
	private static ImageIcon wallArrowLeft;
	private static ImageIcon wallArrowRight;
	private static ImageIcon wallBaloon;
	private static ImageIcon wallContentRatingTV14;
	private static ImageIcon wallContentRatingTVG;
	private static ImageIcon wallContentRatingTVMA;
	private static ImageIcon wallContentRatingTVPG;
	private static ImageIcon wallContentRatingTVY;
	private static ImageIcon wallContentRatingTVY7;
	private static ImageIcon wallExtraFanarts;
	private static ImageIcon wallIMDb;
	private static ImageIcon wallStar;

	public static ImageIcon retrieveApikey() {
		return retrieveAndScaleImage(ImageName.Apikey, APIKEY_SIZE);
	}

	public static ImageIcon retrieveDialogCancel() {
		return retrieveAndScaleImage(ImageName.Cancel, DIALOG_BUTTON_SIZE);
	}

	public static ImageIcon retrieveDialogOk() {
		return retrieveAndScaleImage(ImageName.Ok, DIALOG_BUTTON_SIZE);
	}

	public static ImageIcon retrieveEpisodeTileVideoFile() {
		if (episodeTileVideoFile == null) {
			episodeTileVideoFile = retrieveAndScaleImage(ImageName.TvSerie, EPISODE_TILE_VIDEO_FILE_SIZE);
		}

		return episodeTileVideoFile;
	}

	public static ImageIcon retrieveExplorerTilePosterTvSerie() {
		if (explorerTilePosterTvSerie == null) {
			explorerTilePosterTvSerie = retrieveAndScaleImage(ImageName.TvSerie_Tile_Poster, Dimensions.getExplorerTilePosterSize());
		}

		return explorerTilePosterTvSerie;
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

	public static ImageIcon retrieveFanartChoiceFanarttvLogo() {
		return retrieveAndScaleImageByWidth(ImageName.Fanarttv_Logo, FANART_CHOICE_LOGO_SIZE);

	}

	public static ImageIcon retrieveFanartChoiceTheTvDbLogo() {
		return retrieveAndScaleImageByWidth(ImageName.TheTVDB_Logo, FANART_CHOICE_LOGO_SIZE);
	}

	public static ImageIcon retrieveFanarttvLogo() {
		return retrieveAndScaleImageByWidth(ImageName.Fanarttv_Logo, FANARTTV_LOGO_SIZE);
	}

	public static ImageIcon retrieveImdbLogo() {
		return retrieveAndScaleImageByWidth(ImageName.IMDb_Logo, IMDB_LOGO_SIZE);
	}

	// TODO remove if not used
	public static ImageIcon retrieveLoading() {
		if (loading == null) {
			loading = retrieveImage(ImageName.Loading);
		}

		return loading;
	}

	public static ImageIcon retrieveTheTvDbLogo() {
		return retrieveAndScaleImageByWidth(ImageName.TheTVDB_Logo, THE_TV_DB_LOGO_SIZE);
	}

	public static ImageIcon retrieveToolBarExceptionConsole() {
		return retrieveAndScaleImage(ImageName.ExceptionConsole, MENU_BAR_BUTTON_SIZE);
	}

	public static ImageIcon retrieveToolBarScrapersSettings() {
		return retrieveAndScaleImage(ImageName.Scrapers_Settings, MENU_BAR_BUTTON_SIZE);
	}

	public static ImageIcon retrieveTvSerieSeasonDialogCancel() {
		return retrieveAndScaleImage(ImageName.Cancel, MENU_BAR_BUTTON_SIZE);
	}

	public static ImageIcon retrieveTvSerieSeasonDialogConfirm() {
		return retrieveAndScaleImage(ImageName.Ok, MENU_BAR_BUTTON_SIZE);
	}

	public static ImageIcon retrieveWallArrowLeft() {
		if (wallArrowLeft == null) {
			wallArrowLeft = retrieveAndScaleImage(ImageName.ArrowLeft, WALL_ARROR_SIZE);
		}

		return wallArrowLeft;
	}

	public static ImageIcon retrieveWallArrowRight() {
		if (wallArrowRight == null) {
			wallArrowRight = retrieveAndScaleImage(ImageName.ArrowRight, WALL_ARROR_SIZE);
		}

		return wallArrowRight;
	}

	public static ImageIcon retrieveWallBaloon() {
		if (wallBaloon == null) {
			wallBaloon = retrieveAndScaleImage(ImageName.Baloon, WALL_BALOON_SIZE);
		}

		return wallBaloon;
	}

	public static ImageIcon retrieveWallContentRatingTV14() {
		if (wallContentRatingTV14 == null) {
			wallContentRatingTV14 = retrieveAndScaleImage(ImageName.ContentRating_TV14, WALL_CONTENT_RATING_SIZE);
		}

		return wallContentRatingTV14;
	}

	public static ImageIcon retrieveWallContentRatingTVG() {
		if (wallContentRatingTVG == null) {
			wallContentRatingTVG = retrieveAndScaleImage(ImageName.ContentRating_TVG, WALL_CONTENT_RATING_SIZE);
		}

		return wallContentRatingTVG;
	}

	public static ImageIcon retrieveWallContentRatingTVMA() {
		if (wallContentRatingTVMA == null) {
			wallContentRatingTVMA = retrieveAndScaleImage(ImageName.ContentRating_TVMA, WALL_CONTENT_RATING_SIZE);
		}

		return wallContentRatingTVMA;
	}

	public static ImageIcon retrieveWallContentRatingTVPG() {
		if (wallContentRatingTVPG == null) {
			wallContentRatingTVPG = retrieveAndScaleImage(ImageName.ContentRating_TVPG, WALL_CONTENT_RATING_SIZE);
		}

		return wallContentRatingTVPG;
	}

	public static ImageIcon retrieveWallContentRatingTVY() {
		if (wallContentRatingTVY == null) {
			wallContentRatingTVY = retrieveAndScaleImage(ImageName.ContentRating_TVY, WALL_CONTENT_RATING_SIZE);
		}

		return wallContentRatingTVY;
	}

	public static ImageIcon retrieveWallContentRatingTVY7() {
		if (wallContentRatingTVY7 == null) {
			wallContentRatingTVY7 = retrieveAndScaleImage(ImageName.ContentRating_TVY7, WALL_CONTENT_RATING_SIZE);
		}

		return wallContentRatingTVY7;
	}

	public static ImageIcon retrieveWallExtraFanarts() {
		if (wallExtraFanarts == null) {
			wallExtraFanarts = retrieveAndScaleImage(ImageName.ExtraFanarts, WALL_EXTRA_FANARTS_SIZE);
		}

		return wallExtraFanarts;
	}

	public static ImageIcon retrieveWallIMDb() {
		if (wallIMDb == null) {
			wallIMDb = retrieveAndScaleImageByHeight(ImageName.IMDb_Logo, WALL_IMDB_SIZE);
		}

		return wallIMDb;
	}

	public static ImageIcon retrieveWallStar() {
		if (wallStar == null) {
			wallStar = retrieveAndScaleImage(ImageName.Star, WALL_STAR_SIZE);
		}

		return wallStar;
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

	private static ImageIcon retrieveAndScaleImageByHeight(ImageName imageName, int height) {
		ImageIcon result = null;

		result = retrieveImage(imageName);

		double scaleFactor = (double)height / (double)result.getIconHeight();
		double width = scaleFactor * (double)result.getIconWidth();

		if (scaleFactor != 0d) {
			result = new ImageIcon(result.getImage().getScaledInstance((int)width, height, Image.SCALE_SMOOTH));
		}

		return result;
	}

	private static ImageIcon retrieveAndScaleImage(ImageName imageName, Dimension size) {
		ImageIcon result = null;

		result = new ImageIcon(retrieveImage(imageName).getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH));

		return result;
	}

	private ImageRetriever() {
	}

}
