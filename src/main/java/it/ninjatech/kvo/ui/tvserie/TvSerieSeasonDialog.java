package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.WebOverlay;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

public class TvSerieSeasonDialog extends WebDialog implements WindowListener {

	private static final long serialVersionUID = -2012464643608233002L;
	
	public static WebDecoratedImage makeImagePane(ImageIcon image, Dimension size) {
		WebDecoratedImage result = new WebDecoratedImage(image);
		
		result.setMinimumSize(size);
		result.setShadeWidth(5);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setDrawGlassLayer(false);
		TooltipManager.addTooltip(result, null, "<html><div align='center'>Single click to select<br />Double click for full size image</div></html>", TooltipWay.down, (int)TimeUnit.SECONDS.toMillis(2));
		
		return result;
	}
	
	private final TvSerieSeasonController controller;
	private Dimension seasonImageSize;
	private ComponentTransition transition;

	protected TvSerieSeasonDialog(TvSerieSeasonController controller, TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season) {
		super(UI.get(), String.format("%s - Season %s", TvSerieUtils.getTitle(tvSeriePathEntity), season.getNumber()), true);
		
		this.controller = controller;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);
		
		init(season);
		
		pack();
		setLocationRelativeTo(UI.get());
	}
	
	@Override
	public void windowOpened(WindowEvent event) {
	}

	@Override
	public void windowClosing(WindowEvent event) {
		removeTooltip();
		this.controller.notifyClosing();
	}

	@Override
	public void windowClosed(WindowEvent event) {
	}

	@Override
	public void windowIconified(WindowEvent event) {
	}

	@Override
	public void windowDeiconified(WindowEvent event) {
	}

	@Override
	public void windowActivated(WindowEvent event) {
	}

	@Override
	public void windowDeactivated(WindowEvent event) {
	}
	
	protected Dimension getSeasonImageSize() {
		return this.seasonImageSize;
	}

	protected void setSeasonImage(Image image) {
		if (image != null) {
			removeTooltip();
			this.transition.performTransition(makeImagePane(new ImageIcon(image), this.seasonImageSize));
		}
	}
	
	private void init(TvSerieSeason season) {
		WebPanel content = new WebPanel(new VerticalFlowLayout());
		setContentPane(content);

		content.setBackground(Colors.BACKGROUND_INFO);
		
		WebPanel headerPane = makeHeaderPane(season);
		WebPanel contentPane = makeContentPane(season);
		Dimension size = Dimensions.getTvSerieSeasonHandlerSize();
		contentPane.setPreferredWidth(size.width);
		contentPane.setPreferredHeight(size.height - this.seasonImageSize.height - 20);
		
		content.add(headerPane);
		content.add(WebSeparator.createHorizontal());
		content.add(contentPane);
	}
	
	private WebPanel makeHeaderPane(TvSerieSeason season) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		
		result.setOpaque(false);
		result.setMargin(5, 10, 5, 10);
		
		this.seasonImageSize = Dimensions.getTvSerieSeasonHandlerPosterSize();
		ImageIcon voidImage = UIUtils.makeEmptyIcon(this.seasonImageSize, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		WebDecoratedImage image = makeImagePane(voidImage, this.seasonImageSize);
		this.transition = new ComponentTransition(image, new FadeTransitionEffect());
		result.add(this.transition);
		this.transition.setOpaque(false);
		
		WebPanel dataPane = new WebPanel(new VerticalFlowLayout());
		result.add(dataPane);
		dataPane.setOpaque(false);
		
		WebPanel titlePane = new WebPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		dataPane.add(titlePane);
		titlePane.setOpaque(false);
		WebLabel title = new WebLabel(String.format("Season %d", season.getNumber()));
		titlePane.add(title);
		title.setMargin(2, 10, 5, 10);
		title.setFontSize(30);
		title.setForeground(Colors.FOREGROUND_TITLE);
		title.setShadeColor(Colors.FOREGROUND_SHADE_TITLE);
		title.setDrawShade(true);
		
		WebPanel infoPane = new WebPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
		dataPane.add(infoPane);
		infoPane.setOpaque(false);
		WebLabel episodes = new WebLabel(String.format("Episodes: %d", season.episodeCount()));
		infoPane.add(episodes);
		episodes.setFontSize(20);
		episodes.setForeground(Colors.FOREGROUND_STANDARD);
		episodes.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		episodes.setDrawShade(true);
		
		WebImage star = new WebImage(ImageRetriever.retrieveWallStar());
		WebLabel rating = new WebLabel(season.getAverageRating());
		rating.setFontSize(14);
		rating.setForeground(Colors.FOREGROUND_STANDARD);
		rating.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		rating.setDrawShade(true);
		WebOverlay starOverlay = new WebOverlay(star, rating, SwingConstants.CENTER, SwingConstants.CENTER);
		infoPane.add(starOverlay);
		starOverlay.setBackground(Colors.TRANSPARENT);
		
		return result;
	}
	
	private WebPanel makeContentPane(TvSerieSeason season) {
		WebPanel result = new WebPanel(new BorderLayout());
		
		result.setOpaque(false);
		
		WebPanel operationPane = new WebPanel(new BorderLayout());
		result.add(operationPane, BorderLayout.WEST);
		operationPane.setOpaque(false);
		operationPane.add(makeEpisodesPane(season), BorderLayout.WEST);
		operationPane.add(makeFilesPane(), BorderLayout.EAST);
		
		result.add(makeDetailPane(), BorderLayout.CENTER);
		
		return result;
	}
	
	private WebScrollPane makeEpisodesPane(TvSerieSeason season) {
		WebScrollPane result = null;

		WebPanel content = new WebPanel(new VerticalFlowLayout());
		result = new WebScrollPane(content, false, false);
		result.getVerticalScrollBar().setBlockIncrement(30);
		result.getVerticalScrollBar().setUnitIncrement(30);
		result.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		result.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		content.setBackground(Colors.BACKGROUND_INFO);
		
		Dimension episodeImageSize = Dimensions.getTvSerieSeasonEpisodeImageSize();
		for (TvSerieEpisode episode : season.getEpisodes()) {
			content.add(makeEpisodeTile(episode, episodeImageSize));
			content.add(WebSeparator.createHorizontal());
		}
		
		return result;
	}
	
	private WebPanel makeFilesPane() {
		WebPanel result = new WebPanel();
		
		result.setOpaque(false);
		
		return result;
	}
	
	private WebPanel makeDetailPane() {
		WebPanel result = new WebPanel();
		
		result.setOpaque(false);
		
		return result;
	}
	
	private WebPanel makeEpisodeTile(TvSerieEpisode episode, Dimension size) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		result.setOpaque(false);
		
		ImageIcon voidImage = UIUtils.makeEmptyIcon(size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		ComponentTransition transition = new ComponentTransition(new WebImage(voidImage), new FadeTransitionEffect());
		result.add(transition);
		transition.setOpaque(false);
		
		return result;
	}
	
	private void removeTooltip() {
		WebDecoratedImage content = (WebDecoratedImage)this.transition.getContent();
		if (content != null) {
			TooltipManager.removeTooltips(content);
		}
	}
	
}
