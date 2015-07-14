package it.ninjatech.kvo.ui;

import javax.swing.ImageIcon;

import com.alee.laf.panel.WebPanel;

public final class UIUtils {

	public static WebPanel makeSeparatorPane(int height) {
		WebPanel result = new WebPanel();
		
		result.setPreferredHeight(height);
		
		return result;
	}
	
	public static WebPanel makeHorizontalFillerPane(int width) {
		WebPanel result = new WebPanel();
		
		result.setPreferredWidth(width);
		
		return result;
	}
	
	public static ImageIcon getContentRatingWallIcon(String contentRating) {
		ImageIcon result = null;
		
		switch (contentRating) {
		case "TV-14":
			result = ImageRetriever.retrieveWallContentRatingTV14();
			break;
		case "TV-G":
			result = ImageRetriever.retrieveWallContentRatingTVG();
			break;
		case "TV-MA":
			result = ImageRetriever.retrieveWallContentRatingTVMA();
			break;
		case "TV-PG":
			result = ImageRetriever.retrieveWallContentRatingTVPG();
			break;
		case "TV-Y":
			result = ImageRetriever.retrieveWallContentRatingTVY();
			break;
		case "TV-Y7":
			result = ImageRetriever.retrieveWallContentRatingTVY7();
			break;
		}
		
		return result;
	}
	
	private UIUtils() {}
	
}
