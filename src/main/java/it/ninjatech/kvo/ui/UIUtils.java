package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.ui.component.AbstractSlider;
import it.ninjatech.kvo.ui.component.FullImageDialog;
import it.ninjatech.kvo.ui.component.PersonFullImagePane;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.worker.AbstractWorker;
import it.ninjatech.kvo.worker.PersonFullWorker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.BorderPainter;
import com.alee.extended.panel.WebOverlay;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.managers.style.skin.web.WebLabelPainter;

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
	
	public static void showPersonFullImage(String name) {
		PersonFullWorker worker = new PersonFullWorker(name);
		IndeterminateProgressDialogWorker<PersonFullWorker.PersonFullWorkerResult> progressWorker = new IndeterminateProgressDialogWorker<>(worker, name);

		PersonFullWorker.PersonFullWorkerResult result = null;

		progressWorker.start();
		try {
			result = progressWorker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}

		if (result != null && (result.getImage() != null || StringUtils.isNotBlank(result.getImdbId()))) {
			PersonFullImagePane pane = new PersonFullImagePane(result.getImage(), result.getImdbId());
			FullImageDialog dialog = new FullImageDialog(pane, name);
			dialog.setVisible(true);
		}
		else {
			WebOptionPane.showMessageDialog(UI.get(), "Neither image nor IMDB link available for this actor");
		}
	}
	
	public static WebPanel makeRatingPane(WebLabel rating, WebLabel ratingCount) {
		WebPanel result = new WebPanel(new VerticalFlowLayout());
		
		result.setOpaque(false);
		
		WebImage star = new WebImage(ImageRetriever.retrieveWallStar());
		
		rating.setFontSize(14);
		rating.setForeground(Colors.FOREGROUND_STANDARD);
		rating.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		rating.setDrawShade(true);
		
		WebOverlay starOverlay = new WebOverlay(star, rating, SwingConstants.CENTER, SwingConstants.CENTER);
		result.add(starOverlay);
		starOverlay.setBackground(Colors.TRANSPARENT);
		
		result.add(ratingCount);
		ratingCount.setHorizontalAlignment(SwingConstants.CENTER);
		ratingCount.setFontSize(10);
		ratingCount.setForeground(Colors.FOREGROUND_STANDARD);
		ratingCount.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		ratingCount.setDrawShade(true);
		
		return result;
	}
	
	public static WebLabel makeTitleLabel(String title, Integer fontSize, Insets margin) {
		WebLabel result = new WebLabel(title);
		
		if (margin != null) {
			result.setMargin(margin);
		}
		result.setFontSize(fontSize);
		result.setForeground(Colors.FOREGROUND_TITLE);
		result.setShadeColor(Colors.FOREGROUND_SHADE_TITLE);
		result.setDrawShade(true);
		
		return result;
	}
	
	public static WebLabel makeStandardLabel(String title, Integer fontSize, Insets margin, BorderPainter<?> borderPainter) {
		WebLabel result = new WebLabel(title);
		
		if (borderPainter != null) {
			result.setPainter(new WebLabelPainter<>(borderPainter));
		}
		if (margin != null) {
			result.setMargin(margin);
		}
		if (fontSize != null) {
			result.setFontSize(fontSize);
		}
		result.setForeground(Colors.FOREGROUND_STANDARD);
		result.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		result.setDrawShade(true);
		
		return result;
	}
	
	public static WebLabel makeStandardLabel(String title, Integer fontSize, Insets margin) {
		return makeStandardLabel(title, fontSize, margin, null);
	}
	
	public static WebDecoratedImage makeImagePane(ImageIcon image, Dimension size) {
		WebDecoratedImage result = new WebDecoratedImage(image);

		result.setMinimumSize(size);
		result.setShadeWidth(5);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setDrawGlassLayer(false);

		return result;
	}

	public static WebDecoratedImage makeImagePane(Image image, Dimension size) {
		return makeImagePane(new ImageIcon(image), size);
	}
	
	public static WebDecoratedImage makeImagePane(Dimension size) {
		return makeImagePane(UIUtils.makeEmptyIcon(size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA), size);
	}
	
	public static ComponentTransition makeClickableTransition(Component view) {
		ComponentTransition result =  new ComponentTransition(view, new FadeTransitionEffect());
		
		result.setOpaque(false);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		return result;
	}
	
	public static WebPanel makeStandardPane(LayoutManager layoutManager) {
		WebPanel result = new WebPanel(layoutManager);
		
		result.setOpaque(false);
		
		return result;
	}
	
	public static WebPanel makeFlowLayoutPane(int align, int hgap, int vgap, Component... components) {
		WebPanel result = makeStandardPane(new FlowLayout(align, hgap, vgap));

		result.add(components);
		
		return result;
	}
	
	public static WebPanel makeFlowLayoutPane(int align, int hgap, int vgap, List<Component> components) {
		WebPanel result = makeStandardPane(new FlowLayout(align, hgap, vgap));

		result.add(components);
		
		return result;
	}
	
	public static WebButton makeButton(ImageIcon icon, ActionListener actionListener) {
		WebButton result = WebButton.createIconWebButton(icon, StyleConstants.smallRound, true);
		
		if (actionListener != null) {
			result.addActionListener(actionListener);
		}
		
		return result;
	}
	
	public static WebScrollPane makeScrollPane(Component view, int verticalScrollBarPolicy, int horizontalScrollBarPolicy) {
		WebScrollPane result = new WebScrollPane(view, false, false);
		
		result.setVerticalScrollBarPolicy(verticalScrollBarPolicy);
		result.getVerticalScrollBar().setBlockIncrement(30);
		result.getVerticalScrollBar().setUnitIncrement(30);
		result.setHorizontalScrollBarPolicy(horizontalScrollBarPolicy);
		result.getHorizontalScrollBar().setBlockIncrement(30);
		result.getHorizontalScrollBar().setUnitIncrement(30);
		
		return result;
	}
	
	public static WebScrollPane makeTextArea(WebTextArea textArea) {
		WebScrollPane result = null;
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setMargin(5);
		textArea.setBackground(Colors.BACKGROUND_INFO);
		textArea.setForeground(Colors.FOREGROUND_STANDARD);

		result = makeScrollPane(textArea, WebScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, WebScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		return result;
	}
	
	public static WebPanel makeSliderPane(String title, AbstractSlider slider) {
		WebPanel result = makeStandardPane(new VerticalFlowLayout(0, 0));

		WebLabel titleL = makeStandardLabel(title, 20, new Insets(5, 0, 2, 0));
		result.add(titleL);
		titleL.setHorizontalAlignment(SwingConstants.CENTER);

		result.add(slider);

		return result;
	}
	
	public static WebLinkLabel makeImdbLink(String link) {
		WebLinkLabel result = new WebLinkLabel();
		
		result.setToolTipText(Labels.IMDB);
		result.setIcon(ImageRetriever.retrieveWallIMDb());
		result.setLink(ImdbManager.getTitleUrl(link), false);
		
		return result;
	}
	
	private UIUtils() {}
	
}
