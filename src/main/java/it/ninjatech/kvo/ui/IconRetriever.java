package it.ninjatech.kvo.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class IconRetriever {

	public enum IconName {
		
		ExplorerRootIconMovieMenu("root_icon_movie_menu.png"),
		ExplorerRootIconMovie("root_icon_movie.png"),
		ExplorerRootIconTvShowMenu("root_icon_tvshow_menu.png"),
		ExplorerRootIconTvShow("root_icon_tvshow.png"),
		ExplorerRootIcon("root_icon.png");
		
		private final String value; 
		
		private IconName(String name) {
			this.value = String.format("/images/%s", name);
		}
		
	}

	private static final int EXPLORER_TREE_ICON_SIZE = 16;
	
	private static Icon explorerTreeRootIcon;
	private static Icon explorerTreeMoviesRootIcon;
	private static Icon explorerTreeTvSeriesRootIcon;
	
	public static URL retrieveIcon(IconName icon) {
		return IconRetriever.class.getResource(icon.value);
	}
	
	public static Icon retrieveExplorerTreeRootIcon() {
		if (explorerTreeRootIcon == null) {
			explorerTreeRootIcon = retrieveAndScaleIcon(IconName.ExplorerRootIcon, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeRootIcon;
	}
	
	public static Icon retrieveExplorerTreeMoviesRootIcon() {
		if (explorerTreeMoviesRootIcon == null) {
			explorerTreeMoviesRootIcon = retrieveAndScaleIcon(IconName.ExplorerRootIconMovie, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeMoviesRootIcon;
	}
	
	public static Icon retrieveExplorerTreeTvSeriesRootIcon() {
		if (explorerTreeTvSeriesRootIcon == null) {
			explorerTreeTvSeriesRootIcon = retrieveAndScaleIcon(IconName.ExplorerRootIconTvShow, EXPLORER_TREE_ICON_SIZE);
		}
		
		return explorerTreeTvSeriesRootIcon;
	}
	
	private static Icon retrieveAndScaleIcon(IconName iconName, int size) {
		Icon result = null;

		result = new ImageIcon(retrieveIcon(iconName));

		double scaleFactor = (double)size / (double)result.getIconWidth();

		if (scaleFactor != 0d) {
			BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = bufferedImage.createGraphics();
			graphics.scale(scaleFactor, scaleFactor);
			result.paintIcon(UI.get(), graphics, 0, 0);
			graphics.dispose();

			result = new ImageIcon(bufferedImage);
		}

		return result;
	}
	
	private IconRetriever() {}
	
}
