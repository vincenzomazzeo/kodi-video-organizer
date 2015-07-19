package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.WebOverlay;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.blocks.BlocksTransitionEffect;
import com.alee.extended.transition.effects.curtain.CurtainTransitionEffect;
import com.alee.extended.transition.effects.curtain.CurtainType;
import com.alee.extended.transition.effects.slide.SlideTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.utils.SwingUtils;

public class FanartChoicePane extends WebPanel {

	private static final long serialVersionUID = -2374626151773298936L;

	private final Object data;
	private ComponentTransition imageTransition;
	private ComponentTransition languageTransition;
	private ComponentTransition logoTransition;
	private WebLabel ratingCount;
	private WebLabel rating;
	
	public FanartChoicePane(Object data, ImageIcon image, EnhancedLocale language, ImageIcon logo, String rating, String ratingCount) {
		super(new VerticalFlowLayout());
		
		this.data = data;
		
		init(image, language, logo, rating, ratingCount);
	}
	
	public Object getData() {
		return this.data;
	}

	private void init(ImageIcon image, EnhancedLocale language, ImageIcon logo, String rating, String ratingCount) {
		setOpaque(false);
		
		WebDecoratedImage imageC = new WebDecoratedImage(image);
		imageC.setMinimumSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
		imageC.setShadeWidth(5);
		imageC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imageC.setDrawGlassLayer(false);
		
		CurtainTransitionEffect imageTransitionEffect = new CurtainTransitionEffect();
		imageTransitionEffect.setType(CurtainType.fade);
		this.imageTransition = new ComponentTransition(imageC, imageTransitionEffect);
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
		
		WebImage languageC = new WebImage(language.getLanguageFlag());
		this.languageTransition = new ComponentTransition(languageC, new SlideTransitionEffect());
		bottomCenterPane.add(this.languageTransition);
		this.languageTransition.setOpaque(false);
		
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
		
		WebImage logoC = new WebImage(logo);
		this.logoTransition = new ComponentTransition(logoC, new BlocksTransitionEffect());
		bottomRightPane.add(this.logoTransition, BorderLayout.NORTH);
		this.logoTransition.setOpaque(false);
		
		SwingUtils.equalizeComponentsWidths(bottomLeftPane, bottomRightPane);
	}
	
}
