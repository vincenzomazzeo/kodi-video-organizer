package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.Labels;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

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
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

public class TvSerieEpisodeTile extends WebPanel implements MouseListener {

	private static final long serialVersionUID = 5106725843208684939L;

	private final TvSerieSeasonController controller;
	private final TvSerieEpisode episode;
	private final Map<String, WebImage> languageMap;
	private final ImageIcon voidVideoFileImage;
	private WebLabel title;
	private ComponentTransition imageTransition;
	private ComponentTransition videoFileTransition;
	private WebScrollPane languages;
	
	protected TvSerieEpisodeTile(TvSerieSeasonController controller, TvSerieEpisode episode, ImageIcon voidImage, ImageIcon voidVideoFileImage, int width) {
		super();
		
		this.controller = controller;
		this.episode = episode;
		this.languageMap = new HashMap<>();
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
	
	protected void addLanguage(EnhancedLocale language, String filename, boolean removable) {
		WebImage image = new WebImage(language.getLanguageFlag());
		this.languageMap.put(filename, image);
		image.setName(filename);
		TooltipManager.addTooltip(image, null, Labels.getTooltipTvSerieEpisodeTileSubtitleFile(filename, removable), TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(1));
		if (removable) {
			image.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			image.addMouseListener(this);
		}
		WebPanel languagesPane = (WebPanel)this.languages.getViewport().getView();
		languagesPane.add(image);
		languagesPane.revalidate();
		languagesPane.repaint();
	}
	
	protected void removeLanguage(String filename) {
		WebImage image = this.languageMap.get(filename);
		TooltipManager.removeTooltips(image);
		WebPanel languagesPane = (WebPanel)this.languages.getViewport().getView();
		languagesPane.remove(image);
		languagesPane.revalidate();
		languagesPane.repaint();
	}
	
	protected void destroy() {
		TooltipManager.removeTooltips(this.title);
		TooltipManager.removeTooltips(this.videoFileTransition);
		for (WebImage image : this.languageMap.values()) {
			TooltipManager.removeTooltips(image);
		}
		this.languageMap.clear();
	}
	
	private void init(ImageIcon voidImage, int width) {
		setLayout(new BorderLayout());
		setOpaque(false);
		
		this.title = UIUtils.makeStandardLabel(TvSerieUtils.getEpisodeName(this.episode), 14, new Insets(5, 5, 5, 5));
		add(this.title, BorderLayout.NORTH);
		this.title.setPreferredWidth(width - 10);
		this.title.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.title.addMouseListener(this);
		TooltipManager.addTooltip(this.title, null, Labels.CLICK_FOR_DETAIL, TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(1));

		WebPanel innerPane = UIUtils.makeStandardPane(new BorderLayout(10, 0));
		add(innerPane, BorderLayout.CENTER);

		this.imageTransition = new ComponentTransition(new WebImage(voidImage), new FadeTransitionEffect());
		this.imageTransition.setOpaque(false);
		JComponent imageComponent = null;
		if (StringUtils.isNotBlank(this.episode.getImdbId())) {
			WebLinkLabel imdbLink = UIUtils.makeImdbLink(this.episode.getImdbId());
			imageComponent = new WebOverlay(this.imageTransition, imdbLink, SwingUtilities.RIGHT, SwingUtilities.BOTTOM);
			imageComponent.setBackground(Colors.TRANSPARENT);
		}
		else {
			imageComponent = this.imageTransition;
		}
		innerPane.add(imageComponent, BorderLayout.WEST);
		
		WebPanel innerRightPane = UIUtils.makeStandardPane(new BorderLayout());
		innerPane.add(innerRightPane, BorderLayout.CENTER);
		
		WebPanel videoFileTransitionPane = UIUtils.makeStandardPane(new FlowLayout(FlowLayout.LEFT, 0, 5));
		innerRightPane.add(videoFileTransitionPane, BorderLayout.NORTH);
		this.videoFileTransition = new ComponentTransition(new WebImage(this.voidVideoFileImage), new FadeTransitionEffect());
		videoFileTransitionPane.add(this.videoFileTransition);
		this.videoFileTransition.setOpaque(false);
		
		WebPanel languagesPane = UIUtils.makeStandardPane(new FlowLayout(FlowLayout.LEFT, 5, 0));
		languagesPane.setOpaque(true);
		languagesPane.setBackground(Colors.BACKGROUND_INFO);
		languagesPane.setPreferredHeight(EnhancedLocaleMap.FLAG_HEIGHT + Dimensions.SCROLL_BAR_WIDTH);
		this.languages = UIUtils.makeScrollPane(languagesPane, WebScrollPane.VERTICAL_SCROLLBAR_NEVER, WebScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		innerRightPane.add(this.languages, BorderLayout.CENTER);
	}
	
}
