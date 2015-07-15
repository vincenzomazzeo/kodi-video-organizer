package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.AbstractSlider;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.WebOverlay;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.text.WebTextArea;

public class TvSerieView extends WebPanel implements MouseListener {

	private static final long serialVersionUID = -8219959298613920784L;

	private static WebPanel makeSliderPane(String title, AbstractSlider slider) {
		WebPanel result = new WebPanel(new VerticalFlowLayout(0, 0));

		result.setOpaque(false);

		WebLabel titleL = new WebLabel(title);
		result.add(titleL);
		titleL.setFontSize(20);
		titleL.setMargin(5, 0, 2, 0);
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setForeground(Colors.FOREGROUND_STANDARD);
		titleL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		titleL.setDrawShade(true);

		result.add(slider);

		return result;
	}

	private final TvSerieController controller;
	private WebPopOver plotPopOver;
	private TvSerieFanartSlider fanartSlider;

	protected TvSerieView(TvSerieController controller, TvSeriePathEntity tvSeriePathEntity) {
		super();

		this.controller = controller;

		init(tvSeriePathEntity);
		initPlotPopOver(tvSeriePathEntity);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 1) {
			this.plotPopOver.show((Component)event.getSource());
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
	
	protected void setFanart(Image banner, Image character, Image clearart, Image fanart, Image landscape, Image logo, Image poster) {
		this.fanartSlider.setFanart(banner, character, clearart, fanart, landscape, logo, poster);
	}
	
	private void init(TvSeriePathEntity tvSeriePathEntity) {
		setLayout(new VerticalFlowLayout());

		setBackground(Colors.BACKGROUND_INFO);
		setPreferredWidth(0);

		this.fanartSlider = new TvSerieFanartSlider(this.controller);
		
		add(makeTitlePane(tvSeriePathEntity));
		add(makeGenresPane(tvSeriePathEntity));
		add(makeInfoPane(tvSeriePathEntity));
		add(WebSeparator.createHorizontal());
		add(makeSliderPane("Fanarts", this.fanartSlider));
		add(WebSeparator.createHorizontal());
		add(makeSliderPane("Seasons", new TvSerieSeasonSlider()));
		add(WebSeparator.createHorizontal());
		add(makeSliderPane("Actors", new TvSerieActorSlider()));
	}

	private void initPlotPopOver(TvSeriePathEntity tvSeriePathEntity) {
		this.plotPopOver = new WebPopOver(UI.get());
		this.plotPopOver.setMargin(2);
		this.plotPopOver.setMovable(false);
		this.plotPopOver.setLayout(new BorderLayout());

		WebTextArea plot = new WebTextArea(TvSerieUtils.getOverview(tvSeriePathEntity));
		plot.setLineWrap(true);
		plot.setWrapStyleWord(true);
		plot.setEditable(false);
		plot.setMargin(5);
		plot.setBackground(Colors.BACKGROUND_INFO);
		plot.setForeground(Colors.FOREGROUND_STANDARD);

		WebScrollPane scrollPane = new WebScrollPane(plot, false, false);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		scrollPane.getVerticalScrollBar().setBlockIncrement(30);

		this.plotPopOver.add(scrollPane, BorderLayout.CENTER);
	}

	private WebPanel makeTitlePane(TvSeriePathEntity tvSeriePathEntity) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

		result.setBackground(Colors.BACKGROUND_INFO);

		String status = TvSerieUtils.getStatus(tvSeriePathEntity);
		String rating = TvSerieUtils.getRating(tvSeriePathEntity);
		String ratingCount = TvSerieUtils.getRatingCount(tvSeriePathEntity);

		WebImage languageI = new WebImage(TvSerieUtils.getLanguage(tvSeriePathEntity).getLanguageFlag());
		result.add(languageI);

		WebLabel titleL = new WebLabel(TvSerieUtils.getTitle(tvSeriePathEntity));
		result.add(titleL);
		titleL.setMargin(2, 10, 5, 10);
		titleL.setFontSize(30);
		titleL.setForeground(Colors.FOREGROUND_TITLE);
		titleL.setShadeColor(Colors.FOREGROUND_SHADE_TITLE);
		titleL.setDrawShade(true);

		if (StringUtils.isNotEmpty(status)) {
			WebLabel statusL = new WebLabel(String.format("(%s)", status));
			result.add(statusL);
			statusL.setFontSize(20);
			statusL.setForeground(Colors.FOREGROUND_STANDARD);
			statusL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
			statusL.setDrawShade(true);
		}

		if (StringUtils.isNotEmpty(rating)) {
			WebPanel ratingPane = new WebPanel(new VerticalFlowLayout());
			result.add(ratingPane);
			ratingPane.setOpaque(false);

			WebImage star = new WebImage(ImageRetriever.retrieveWallStar());

			WebLabel ratingL = new WebLabel(rating);
			ratingL.setFontSize(14);
			ratingL.setForeground(Colors.FOREGROUND_STANDARD);
			ratingL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
			ratingL.setDrawShade(true);

			WebOverlay starOverlay = new WebOverlay(star, ratingL, SwingConstants.CENTER, SwingConstants.CENTER);
			ratingPane.add(starOverlay);
			starOverlay.setBackground(Colors.TRANSPARENT);

			if (StringUtils.isNotEmpty(ratingCount)) {
				WebLabel ratingCountL = new WebLabel(ratingCount);
				ratingPane.add(ratingCountL);
				ratingCountL.setHorizontalAlignment(SwingConstants.CENTER);
				ratingCountL.setFontSize(10);
				ratingCountL.setForeground(Colors.FOREGROUND_STANDARD);
				ratingCountL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
				ratingCountL.setDrawShade(true);
			}
		}

		return result;
	}

	private WebPanel makeGenresPane(TvSeriePathEntity tvSeriePathEntity) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

		result.setBackground(Colors.BACKGROUND_INFO);

		WebLabel genresL = new WebLabel(TvSerieUtils.getGenre(tvSeriePathEntity));
		result.add(genresL);
		genresL.setMargin(0, 5, 0, 5);
		genresL.setFontSize(14);
		genresL.setForeground(Colors.FOREGROUND_STANDARD);
		genresL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		genresL.setDrawShade(true);

		return result;
	}

	private WebPanel makeInfoPane(TvSeriePathEntity tvSeriePathEntity) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));

		result.setBackground(Colors.BACKGROUND_INFO);

		String firstAired = TvSerieUtils.getFirstAired(tvSeriePathEntity);
		String network = TvSerieUtils.getNetwork(tvSeriePathEntity);
		String contentRating = TvSerieUtils.getContentRating(tvSeriePathEntity);
		String imdbId = TvSerieUtils.getImdbId(tvSeriePathEntity);

		if (StringUtils.isNotBlank(firstAired)) {
			WebLabel firstAiredL = new WebLabel(String.format("First Aired: %s", firstAired));
			result.add(firstAiredL);
			firstAiredL.setFontSize(12);
			firstAiredL.setForeground(Colors.FOREGROUND_STANDARD);
			firstAiredL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
			firstAiredL.setDrawShade(true);
		}

		if (StringUtils.isNotBlank(network)) {
			WebLabel networkL = new WebLabel(String.format("Network: %s", network));
			result.add(networkL);
			networkL.setFontSize(12);
			networkL.setForeground(Colors.FOREGROUND_STANDARD);
			networkL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
			networkL.setDrawShade(true);
		}

		if (StringUtils.isNotBlank(contentRating)) {
			WebImage contentRatingI = new WebImage(UIUtils.getContentRatingWallIcon(contentRating));
			result.add(contentRatingI);
		}

		if (StringUtils.isNotBlank(imdbId)) {
			WebLinkLabel imdbL = new WebLinkLabel(ImageRetriever.retrieveWallIMDb());
			result.add(imdbL);
			imdbL.setLink(String.format("http://www.imdb.com/title/%s", imdbId), false);
		}

		if (StringUtils.isNotBlank(TvSerieUtils.getOverview(tvSeriePathEntity))) {
			WebImage plotI = new WebImage(ImageRetriever.retrieveWallBaloon());
			result.add(plotI);
			plotI.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			plotI.addMouseListener(this);
		}

		return result;
	}

}
