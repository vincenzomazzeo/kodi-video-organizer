package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;

import com.alee.extended.image.WebImage;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

public class TvSerieEpisodeTile extends WebPanel {

	private static final long serialVersionUID = 5106725843208684939L;

	private final TvSerieSeasonController controller;
	private final ImageIcon voidVideoFileImage;
	private ComponentTransition imageTransition;
	private ComponentTransition videoFileTransition;
	private WebScrollPane languages;
	
	protected TvSerieEpisodeTile(TvSerieSeasonController controller, TvSerieEpisode episode, ImageIcon voidImage, ImageIcon voidVideoFileImage, int width) {
		super();
		
		this.controller = controller;
		this.voidVideoFileImage = voidVideoFileImage;
		
		init(episode, voidImage, width);
		
		setTransferHandler(this.controller.makeEpisodeTileDropTransferHandler(episode, this));
	}
	
	protected void setVideoFile() {
		this.videoFileTransition.performTransition(new WebImage(ImageRetriever.retrieveEpisodeTileVideoFile()));
	}
	
	protected void clearVideoFile() {
		this.videoFileTransition.performTransition(new WebImage(this.voidVideoFileImage));
	}
	
	private void init(TvSerieEpisode episode, ImageIcon voidImage, int width) {
		setLayout(new BorderLayout());
		setOpaque(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		WebLabel name = new WebLabel(TvSerieUtils.getEpisodeName(episode));
		add(name, BorderLayout.NORTH);
		name.setPreferredWidth(width - 10);
		name.setMargin(5, 5, 5, 5);
		name.setFontSize(14);
		name.setForeground(Colors.FOREGROUND_STANDARD);
		name.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		name.setDrawShade(true);

		WebPanel innerPane = new WebPanel(new BorderLayout(10, 0));
		add(innerPane, BorderLayout.CENTER);
		innerPane.setOpaque(false);

		this.imageTransition = new ComponentTransition(new WebImage(voidImage), new FadeTransitionEffect());
		innerPane.add(this.imageTransition, BorderLayout.WEST);
		this.imageTransition.setOpaque(false);
		
		WebPanel innerRightPane = new WebPanel(new BorderLayout());
		innerPane.add(innerRightPane, BorderLayout.CENTER);
		innerRightPane.setOpaque(false);
		
		WebPanel videoFileTransitionPane = new WebPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
		innerRightPane.add(videoFileTransitionPane, BorderLayout.NORTH);
		videoFileTransitionPane.setOpaque(false);
		this.videoFileTransition = new ComponentTransition(new WebImage(this.voidVideoFileImage), new FadeTransitionEffect());
		videoFileTransitionPane.add(this.videoFileTransition);
		this.videoFileTransition.setOpaque(false);
		
		WebPanel languagesPane = new WebPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		languagesPane.setBackground(Colors.BACKGROUND_INFO);
		languagesPane.setPreferredHeight(EnhancedLocaleMap.FLAG_HEIGHT);
		this.languages = new WebScrollPane(languagesPane, false, false);
		innerRightPane.add(this.languages, BorderLayout.CENTER);
		this.languages.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.languages.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.languages.getHorizontalScrollBar().setBlockIncrement(30);
		this.languages.getHorizontalScrollBar().setUnitIncrement(30);
	}
	
}
