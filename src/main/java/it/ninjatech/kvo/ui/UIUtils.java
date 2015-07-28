package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.ui.component.FullImageDialog;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.worker.AbstractWorker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.alee.extended.image.WebImage;
import com.alee.laf.panel.WebPanel;

public final class UIUtils {

	public static WebPanel makeVerticalFillerPane(int height, boolean opaque) {
		WebPanel result = new WebPanel();
		
		result.setPreferredHeight(height);
		result.setOpaque(opaque);
		
		return result;
	}
	
	public static WebPanel makeHorizontalFillerPane(int width, boolean opaque) {
		WebPanel result = new WebPanel();
		
		result.setPreferredWidth(width);
		result.setOpaque(opaque);
		
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
	
	public static ImageIcon makeEmptyIcon(Dimension size, Color color) {
		ImageIcon result = null;
		
		BufferedImage bufferedImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor(color);
		graphics.fillRect(0, 0, size.width, size.height);
		result = new ImageIcon(bufferedImage);
		
		return result;
	}
	
	public static void showFullImage(AbstractWorker<Image> worker, String loadingTitle, String imageTitle) {
		IndeterminateProgressDialogWorker<Image> progressWorker = new IndeterminateProgressDialogWorker<>(worker, loadingTitle);

		Image result = null;

		progressWorker.start();
		try {
			result = progressWorker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}

		if (result != null) {
			WebImage image = new WebImage(result);
			FullImageDialog dialog = new FullImageDialog(image, imageTitle);
			dialog.setVisible(true);
		}
	}
	
	private UIUtils() {}
	
}
