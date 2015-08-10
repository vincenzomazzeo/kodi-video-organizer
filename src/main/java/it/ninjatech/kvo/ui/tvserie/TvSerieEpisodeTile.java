package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.TvSerieUtils;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.panel.WebOverlay;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.TransitionListener;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

public class TvSerieEpisodeTile extends WebPanel implements MouseListener, TransitionListener {

	private static final long serialVersionUID = 5106725843208684939L;

	private final TvSerieSeasonController controller;
	private final TvSerieEpisode episode;
	private final Map<String, WebImage> subtitleMap;
	private final ImageIcon voidVideoFileImage;
	private WebLabel title;
	private JComponent imageComponent;
	private ComponentTransition imageTransition;
	private ComponentTransition videoFileTransition;
	private WebScrollPane subtitles;
	
	protected TvSerieEpisodeTile(TvSerieSeasonController controller, TvSerieEpisode episode, ImageIcon voidImage, ImageIcon voidVideoFileImage, int width) {
		super();
		
		this.controller = controller;
		this.episode = episode;
		this.subtitleMap = new HashMap<>();
		this.voidVideoFileImage = voidVideoFileImage;
		
		init(voidImage, width);
		
		setTransferHandler(this.controller.makeEpisodeTileDropTransferHandler(episode, this));
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isRightMouseButton(event)) {
			if (event.getSource() == this.videoFileTransition) {
				this.controller.notifyVideoFileRightClick(this.episode, this);
			}
			else {
				WebImage image = (WebImage)event.getSource();
				this.controller.notifyLanguageRightClick(this.episode, this, image.getName());
			}
		}
		else if (SwingUtilities.isLeftMouseButton(event)) {
			this.controller.notifyEpisodeClick(this.episode);
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

	@Override
	public void transitionStarted() {
	}

	@Override
	public void transitionFinished() {
		if (this.imageTransition != this.imageComponent) {
			this.imageComponent.revalidate();
			this.imageComponent.repaint();
		}
	}

	protected void setImage(Image image) {
		this.imageTransition.performTransition(new WebImage(image));
	}
	
	protected void setVideoFile(String filename, boolean removable) {
		TooltipManager.addTooltip(this.videoFileTransition, null, Labels.getTooltipTvSerieEpisodeTileVideoFile(filename, removable), TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(1));
		if (removable) {
			this.videoFileTransition.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.videoFileTransition.addMouseListener(this);
		}
		this.videoFileTransition.performTransition(new WebImage(ImageRetriever.retrieveEpisodeTileVideoFile()));
	}
	
	protected void clearVideoFile() {
		TooltipManager.removeTooltips(this.videoFileTransition);
		this.videoFileTransition.setCursor(Cursor.getDefaultCursor());
		this.videoFileTransition.removeMouseListener(this);
		this.videoFileTransition.performTransition(new WebImage(this.voidVideoFileImage));
	}
	
	protected void addSubtitle(EnhancedLocale language, String filename, boolean removable) {
		WebImage image = new WebImage(language.getLanguageFlag());
		this.subtitleMap.put(filename, image);
		image.setName(filename);
		TooltipManager.addTooltip(image, null, Labels.getTooltipTvSerieEpisodeTileSubtitleFile(filename, removable), TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(1));
		if (removable) {
			image.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			image.addMouseListener(this);
		}
		WebPanel subtitlesPane = (WebPanel)this.subtitles.getViewport().getView();
		subtitlesPane.add(image);
		subtitlesPane.revalidate();
		subtitlesPane.repaint();
	}
	
	protected void removeSubtitle(String filename) {
		WebImage image = this.subtitleMap.get(filename);
		TooltipManager.removeTooltips(image);
		WebPanel subtitlesPane = (WebPanel)this.subtitles.getViewport().getView();
		subtitlesPane.remove(image);
		subtitlesPane.revalidate();
		subtitlesPane.repaint();
	}
	
	protected void destroy() {
		TooltipManager.removeTooltips(this.title);
		TooltipManager.removeTooltips(this.videoFileTransition);
		for (WebImage image : this.subtitleMap.values()) {
			TooltipManager.removeTooltips(image);
		}
		this.subtitleMap.clear();
	}
	
	private void init(ImageIcon voidImage, int width) {
		setLayout(new BorderLayout());
		setOpaque(false);
		
		this.title = UIUtils.makeStandardLabel(TvSerieUtils.getEpisodeName(this.episode), 14, new Insets(5, 5, 5, 5), null);
		add(this.title, BorderLayout.NORTH);
		this.title.setPreferredWidth(width - 10);
		this.title.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.title.addMouseListener(this);
		TooltipManager.addTooltip(this.title, null, Labels.CLICK_FOR_DETAIL, TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(1));

		WebPanel innerPane = UIUtils.makeStandardPane(new BorderLayout(10, 0));
		add(innerPane, BorderLayout.CENTER);

		this.imageTransition = new ComponentTransition(new WebImage(voidImage), new FadeTransitionEffect());
		this.imageTransition.addTransitionListener(this);
		this.imageTransition.setBackground(Colors.BACKGROUND_INFO);
		this.imageComponent = null;
		if (StringUtils.isNotBlank(this.episode.getImdbId())) {
			WebLinkLabel imdbLink = UIUtils.makeImdbLink(this.episode.getImdbId());
			this.imageComponent = new WebOverlay(this.imageTransition, imdbLink, SwingUtilities.RIGHT, SwingUtilities.BOTTOM);
		}
		else {
			this.imageComponent = this.imageTransition;
		}
		innerPane.add(this.imageComponent, BorderLayout.WEST);
		
		WebPanel innerRightPane = UIUtils.makeStandardPane(new BorderLayout());
		innerPane.add(innerRightPane, BorderLayout.CENTER);
		
		WebPanel videoFileTransitionPane = UIUtils.makeStandardPane(new FlowLayout(FlowLayout.LEFT, 0, 5));
		innerRightPane.add(videoFileTransitionPane, BorderLayout.NORTH);
		this.videoFileTransition = new ComponentTransition(new WebImage(this.voidVideoFileImage), new FadeTransitionEffect());
		videoFileTransitionPane.add(this.videoFileTransition);
		this.videoFileTransition.setOpaque(false);
		
		WebPanel subtitlesPane = UIUtils.makeStandardPane(new FlowLayout(FlowLayout.LEFT, 5, 0));
		subtitlesPane.setOpaque(true);
		subtitlesPane.setBackground(Colors.BACKGROUND_INFO);
		subtitlesPane.setPreferredHeight(EnhancedLocaleMap.FLAG_HEIGHT + Dimensions.SCROLL_BAR_WIDTH);
		this.subtitles = UIUtils.makeScrollPane(subtitlesPane, WebScrollPane.VERTICAL_SCROLLBAR_NEVER, WebScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		innerRightPane.add(this.subtitles, BorderLayout.CENTER);
	}
	
}
