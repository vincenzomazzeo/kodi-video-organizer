package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.WebOverlay;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.utils.SwingUtils;

public class FanartChoicePane extends WebPanel implements MouseListener {

	private static final long serialVersionUID = -2374626151773298936L;

	private static WebDecoratedImage makeImage(ImageIcon image, MouseListener mouseListener, String tooltip) {
		WebDecoratedImage result = new WebDecoratedImage(image);

		result.setMinimumSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
		result.setShadeWidth(5);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setDrawGlassLayer(false);
		result.addMouseListener(mouseListener);
		if (tooltip != null) {
			TooltipManager.addTooltip(result, null, tooltip, TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
		}

		return result;
	}

	private final FanartChoicePaneListener listener;
	private Object data;
	private ComponentTransition imageTransition;
	private WebLabel ratingCount;
	private WebLabel rating;

	public FanartChoicePane(Object data, FanartChoicePaneListener listener,  
	                        ImageIcon image, EnhancedLocale language, ImageIcon logo, String rating, String ratingCount) {
		super(new VerticalFlowLayout());

		this.data = data;
		this.listener = listener;

		init(image, language, logo, rating, ratingCount);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event)) {
			if (event.getClickCount() == 1) {
				this.listener.fanartChoicePaneSingleClick(this);
			}
			else if (event.getClickCount() == 2) {
				this.listener.fanartChoicePaneDoubleClick(this);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}
	
	public Object getData() {
		return this.data;
	}
	
	public void setImage(Image image) {
		if (image != null) {
			removeTooltip();
			this.imageTransition.performTransition(makeImage(new ImageIcon(image), this, "<html><div align='center'>Single click to select<br />Double click for full size image</div></html>"));
		}
	}
	
	public void dispose() {
		removeTooltip();
	}

	private void init(ImageIcon image, EnhancedLocale language, ImageIcon logo, String rating, String ratingCount) {
		setOpaque(false);

		this.imageTransition = new ComponentTransition(makeImage(image, this, null), new FadeTransitionEffect());
		add(this.imageTransition);
		this.imageTransition.setOpaque(false);

		WebPanel bottomPane = new WebPanel(new BorderLayout());
		add(bottomPane);
		bottomPane.setMargin(0, 5, 0, 5);
		bottomPane.setOpaque(false);

		WebPanel bottomLeftPane = new WebPanel();
		bottomPane.add(bottomLeftPane, BorderLayout.WEST);
		bottomLeftPane.setOpaque(false);

		WebPanel bottomCenterPane = new WebPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		bottomPane.add(bottomCenterPane, BorderLayout.CENTER);
		bottomCenterPane.setOpaque(false);

		bottomCenterPane.add(new WebImage(language.getLanguageFlag()));

		WebPanel ratingPane = new WebPanel(new VerticalFlowLayout());
		bottomCenterPane.add(ratingPane);
		ratingPane.setOpaque(false);

		WebImage star = new WebImage(ImageRetriever.retrieveWallStar());

		this.rating = new WebLabel();
		this.rating.setFontSize(14);
		this.rating.setForeground(Colors.FOREGROUND_STANDARD);
		this.rating.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		this.rating.setDrawShade(true);
		if (StringUtils.isNotBlank(rating)) {
			this.rating.setText(rating);
		}

		WebOverlay starOverlay = new WebOverlay(star, this.rating, SwingConstants.CENTER, SwingConstants.CENTER);
		ratingPane.add(starOverlay);
		starOverlay.setBackground(Colors.TRANSPARENT);

		this.ratingCount = new WebLabel("0");
		ratingPane.add(this.ratingCount);
		this.ratingCount.setHorizontalAlignment(SwingConstants.CENTER);
		this.ratingCount.setFontSize(10);
		this.ratingCount.setForeground(Colors.FOREGROUND_STANDARD);
		this.ratingCount.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		if (StringUtils.isNotBlank(ratingCount)) {
			this.ratingCount.setText(ratingCount);
			this.ratingCount.setDrawShade(true);
		}
		else {
			this.ratingCount.setForeground(Colors.TRANSPARENT);
		}

		WebPanel bottomRightPane = new WebPanel(new BorderLayout());
		bottomPane.add(bottomRightPane, BorderLayout.EAST);
		bottomRightPane.setOpaque(false);

		bottomRightPane.add(new WebImage(logo), BorderLayout.NORTH);

		SwingUtils.equalizeComponentsWidths(bottomLeftPane, bottomRightPane);
	}

	private void removeTooltip() {
		WebDecoratedImage content = (WebDecoratedImage)this.imageTransition.getContent();
		if (content != null) {
			TooltipManager.removeTooltips(content);
		}
	}
	
	public static interface FanartChoicePaneListener {
		
		public void fanartChoicePaneSingleClick(FanartChoicePane pane);
		
		public void fanartChoicePaneDoubleClick(FanartChoicePane pane);
		
	}
	
}
