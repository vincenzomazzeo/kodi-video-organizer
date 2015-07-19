package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSerieImage;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.FanartChoicePane;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.utils.SwingUtils;

public class TvSerieFanartChoiceView extends WebPanel {

	private static final long serialVersionUID = -5271160094897583185L;
	
	private final TvSerieFanartChoiceController tvSerieFanartChoiceController;
	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieFanart fanart;
	private ImageIcon voidImage;
	private ImageIcon theTvDbLogo;
	private ImageIcon fanarttvLogo;
	private ComponentTransition currentTransition;
	private FanartChoicePane chosenPane;
	
	protected TvSerieFanartChoiceView(TvSerieFanartChoiceController tvSerieFanartChoiceController, TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart) {
		super();
		
		this.tvSerieFanartChoiceController = tvSerieFanartChoiceController;
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.fanart = fanart;
		
		init();
	}

	private void init() {
		setLayout(new VerticalFlowLayout());

		setBackground(Colors.BACKGROUND_INFO);
		
		this.voidImage = UIUtils.makeEmptyIcon(Dimensions.getTvSerieFanartChooserSize(this.fanart), Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		this.theTvDbLogo = ImageRetriever.retrieveFanartChoiceTheTvDbLogo();
		this.fanarttvLogo = ImageRetriever.retrieveFanartChoiceFanarttvLogo();
		
		WebScrollPane gallery = new WebScrollPane(makeGalleryPane(), false, false);
		gallery.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gallery.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		gallery.setBackground(Colors.BACKGROUND_INFO);
		gallery.setPreferredHeight(600);
		gallery.getVerticalScrollBar().setBlockIncrement(30);
		gallery.getVerticalScrollBar().setUnitIncrement(30);
		
		add(makeHeaderPane());
		add(WebSeparator.createHorizontal());
		add(gallery);
	}
	
	private WebPanel makeHeaderPane() {
		WebPanel result = new WebPanel(new FlowLayout());
		
		result.setOpaque(false);
		result.setMargin(10, 10, 10, 30);
		
		WebPanel current = makeCurrentPane();
		WebPanel chosen = makeChosenPanel();
		
		SwingUtils.equalizeComponentsHeights(current, chosen);
		
		result.add(current);
		result.add(chosen);
		
		return result;
	}
	
	private WebPanel makeCurrentPane() {
		WebPanel result = new WebPanel(new VerticalFlowLayout());

		result.setOpaque(false);
		
		WebPanel container = new WebPanel(new VerticalFlowLayout());
		result.add(container, BorderLayout.NORTH);
		container.setOpaque(false);
		
		WebLabel title = new WebLabel("Current");
		container.add(title);
		title.setFontSize(16);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(Colors.FOREGROUND_STANDARD);
		title.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		title.setDrawShade(true);
		
		Dimension imageSize = Dimensions.getTvSerieFanartChooserSize(this.fanart);
		WebDecoratedImage image = new WebDecoratedImage(UIUtils.makeEmptyIcon(imageSize, Colors.BACKGROUND_MISSING_IMAGE_ALPHA));
		image.setMinimumSize(imageSize);
		image.setShadeWidth(5);
		image.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		image.setDrawGlassLayer(false);
		
		this.currentTransition = new ComponentTransition(image, new FadeTransitionEffect());
		container.add(this.currentTransition);
		this.currentTransition.setOpaque(false);
		
		return result;
	}
	
	private WebPanel makeChosenPanel() {
		WebPanel result = new WebPanel(new VerticalFlowLayout());

		result.setOpaque(false);
		
		WebLabel title = new WebLabel("Chosen");
		result.add(title);
		title.setFontSize(16);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(Colors.FOREGROUND_STANDARD);
		title.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		title.setDrawShade(true);
		
		this.chosenPane = new FanartChoicePane(null, this.voidImage, EnhancedLocaleMap.getEmptyLocale(), UIUtils.makeEmptyIcon(new Dimension(this.theTvDbLogo.getIconWidth(), 1), Colors.BACKGROUND_INFO), null, null);
		result.add(this.chosenPane);
		
		return result;
	}
	
	private WebPanel makeGalleryPane() {
		WebPanel result = new WebPanel(new FlowLayout());
		
		result.setBackground(Colors.BACKGROUND_INFO);
		
		WebPanel leftSide = new WebPanel(new VerticalFlowLayout(0, 10));
		result.add(leftSide);
		leftSide.setOpaque(false);
		WebPanel rightSide = new WebPanel(new VerticalFlowLayout(0, 10));
		result.add(rightSide);
		rightSide.setOpaque(false);
		
		int i = 0;
		
		Set<TvSerieImage> theTvDbFanarts = TvSerieUtils.getTheTvDbFanarts(this.tvSeriePathEntity, this.fanart);
		for (TvSerieImage theTvDbFanart : theTvDbFanarts) {
			FanartChoicePane pane = new FanartChoicePane(theTvDbFanart, voidImage, theTvDbFanart.getLanguage(), this.theTvDbLogo, theTvDbFanart.getRating(), theTvDbFanart.getRatingCount());
			if (i++ % 2 == 0) {
				leftSide.add(pane);
			}
			else {
				rightSide.add(pane);
			}
		}
		
		Set<TvSerieImage> fanarttvFanarts = TvSerieUtils.getFanarttvFanarts(this.tvSeriePathEntity, this.fanart);
		for (TvSerieImage fanarttvFanart : fanarttvFanarts) {
			FanartChoicePane pane = new FanartChoicePane(fanarttvFanart, voidImage, fanarttvFanart.getLanguage(), this.fanarttvLogo, fanarttvFanart.getRating(), null);
			if (i++ % 2 == 0) {
				leftSide.add(pane);
			}
			else {
				rightSide.add(pane);
			}
		}
		
		SwingUtils.equalizeComponentsHeights(leftSide, rightSide);
		
		return result;
	}
	
}
