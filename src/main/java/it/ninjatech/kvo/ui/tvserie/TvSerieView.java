package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.model.TvSerieActor;
import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler.TvSerieImageLoaderListener;
import it.ninjatech.kvo.ui.TvSerieUtils;
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
//TODO UIUtils
public class TvSerieView extends WebPanel implements MouseListener, TvSerieImageLoaderListener {

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
	private WebPanel titlePane;
	private WebImage language;
	private WebLabel title;
	private WebLabel status;
	private WebPanel ratingPane;
	private WebLabel rating;
	private WebLabel ratingCount;
	private WebPanel genresPane;
	private WebLabel genres;
	private WebPanel infoPane;
	private WebLabel firstAired;
	private WebLabel network;
	private WebImage contentRating;
	private WebLinkLabel imdb;
	private WebImage plot;
	private WebImage extrafanarts;
	private TvSerieFanartSlider fanartSlider;
	private TvSerieSeasonSlider seasonSlider;
	private TvSerieActorSlider actorSlider;

	protected TvSerieView(TvSerieController controller) {
		super();

		this.controller = controller;

		init();
		initPlotPopOver();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event) && event.getClickCount() == 1) {
			Object source = event.getSource();
			if (source == this.plot) {
				this.plotPopOver.show((Component)event.getSource());
			}
			else if (source == this.extrafanarts) {
				this.controller.notifyExtraFanartsClick();
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

	@Override
	public void notifyImageLoaded(String id, Image image, Object supportData) {
		if (supportData.getClass().equals(TvSerieFanart.class)) {
			this.fanartSlider.setFanart((TvSerieFanart)supportData, image);
		}
		else if (supportData.getClass().equals(TvSerieSeason.class)) {
			this.seasonSlider.setSeason((TvSerieSeason)supportData, image);
		}
		else if (supportData.getClass().equals(TvSerieActor.class)) {
			this.actorSlider.setActor((TvSerieActor)supportData, image);
		}
	}

	protected void fill(TvSeriePathEntity tvSeriePathEntity) {
		// Title
		String status = TvSerieUtils.getStatus(tvSeriePathEntity);
		String rating = TvSerieUtils.getRating(tvSeriePathEntity);
		String ratingCount = TvSerieUtils.getRatingCount(tvSeriePathEntity);

		this.titlePane.removeAll();

		this.language.setIcon(tvSeriePathEntity.getTvSerie().getLanguage().getLanguageFlag());
		this.titlePane.add(this.language);
		this.title.setText(TvSerieUtils.getTitle(tvSeriePathEntity));
		this.titlePane.add(this.title);
		if (StringUtils.isNotEmpty(status)) {
			this.status.setText(String.format("(%s)", status));
			this.titlePane.add(this.status);
		}
		if (StringUtils.isNotEmpty(rating)) {
			this.rating.setText(rating);
			this.titlePane.add(ratingPane);
			if (StringUtils.isNotEmpty(ratingCount)) {
				this.ratingCount.setText(ratingCount);
			}
		}

		// Genres
		this.genres.setText(TvSerieUtils.getGenre(tvSeriePathEntity));

		// Info
		String firstAired = TvSerieUtils.getFirstAired(tvSeriePathEntity);
		String network = TvSerieUtils.getNetwork(tvSeriePathEntity);
		String contentRating = TvSerieUtils.getContentRating(tvSeriePathEntity);
		String imdbId = TvSerieUtils.getImdbId(tvSeriePathEntity);
		String overview = TvSerieUtils.getOverview(tvSeriePathEntity);

		this.infoPane.removeAll();

		if (StringUtils.isNotBlank(firstAired)) {
			this.firstAired.setText(String.format("First Aired: %s", firstAired));
			this.infoPane.add(this.firstAired);
		}
		if (StringUtils.isNotBlank(network)) {
			this.network.setText(String.format("Network: %s", network));
			this.infoPane.add(this.network);
		}
		if (StringUtils.isNotBlank(contentRating)) {
			this.contentRating.setIcon(UIUtils.getContentRatingWallIcon(contentRating));
			this.infoPane.add(this.contentRating);
		}
		if (StringUtils.isNotBlank(imdbId) && ImdbManager.getInstance().isEnabled()) {
			this.imdb.setIcon(ImageRetriever.retrieveWallIMDb());
			this.infoPane.add(this.imdb);
			this.imdb.setLink(ImdbManager.getTitleUrl(imdbId), false);
		}
		if (StringUtils.isNotBlank(overview)) {
			((WebTextArea)((WebScrollPane)this.plotPopOver.getContentPane().getComponent(0)).getViewport().getView()).setText(overview);
			this.infoPane.add(this.plot);
		}
		if(TvSerieUtils.hasExtraFanarts(tvSeriePathEntity)) {
			this.infoPane.add(this.extrafanarts);
		}
	}

	protected TvSerieFanartSlider getFanartSlider() {
		return this.fanartSlider;
	}

	protected TvSerieSeasonSlider getSeasonSlider() {
		return this.seasonSlider;
	}

	protected TvSerieActorSlider getActorSlider() {
		return this.actorSlider;
	}

	private void init() {
		setLayout(new VerticalFlowLayout());

		setBackground(Colors.BACKGROUND_INFO);
		setPreferredWidth(0);

		this.fanartSlider = new TvSerieFanartSlider(this.controller);
		this.seasonSlider = new TvSerieSeasonSlider(this.controller);
		this.actorSlider = new TvSerieActorSlider(this.controller);

		makeTitlePane();
		makeGenresPane();
		makeInfoPane();

		add(this.titlePane);
		add(this.genresPane);
		add(this.infoPane);
		add(WebSeparator.createHorizontal());
		add(makeSliderPane("Fanarts", this.fanartSlider));
		add(WebSeparator.createHorizontal());
		add(makeSliderPane("Seasons", this.seasonSlider));
		add(WebSeparator.createHorizontal());
		add(makeSliderPane("Actors", this.actorSlider));
	}

	private void initPlotPopOver() {
		// TODO sistemare: non si vede più
		this.plotPopOver = new WebPopOver(this);
		this.plotPopOver.setMargin(2);
		this.plotPopOver.setMovable(false);
		this.plotPopOver.setLayout(new BorderLayout());

		WebTextArea plot = new WebTextArea();
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

	private void makeTitlePane() {
		this.titlePane = new WebPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		this.titlePane.setBackground(Colors.BACKGROUND_INFO);

		this.language = new WebImage();

		this.title = new WebLabel();
		this.title.setMargin(2, 10, 5, 10);
		this.title.setFontSize(30);
		this.title.setForeground(Colors.FOREGROUND_TITLE);
		this.title.setShadeColor(Colors.FOREGROUND_SHADE_TITLE);
		this.title.setDrawShade(true);

		this.status = new WebLabel();
		this.status.setFontSize(20);
		this.status.setForeground(Colors.FOREGROUND_STANDARD);
		this.status.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		this.status.setDrawShade(true);

		this.ratingPane = new WebPanel(new VerticalFlowLayout());
		this.ratingPane.setOpaque(false);

		WebImage star = new WebImage(ImageRetriever.retrieveWallStar());

		this.rating = new WebLabel();
		this.rating.setFontSize(14);
		this.rating.setForeground(Colors.FOREGROUND_STANDARD);
		this.rating.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		this.rating.setDrawShade(true);

		WebOverlay starOverlay = new WebOverlay(star, this.rating, SwingConstants.CENTER, SwingConstants.CENTER);
		this.ratingPane.add(starOverlay);
		starOverlay.setBackground(Colors.TRANSPARENT);

		this.ratingCount = new WebLabel();
		this.ratingPane.add(this.ratingCount);
		this.ratingCount.setHorizontalAlignment(SwingConstants.CENTER);
		this.ratingCount.setFontSize(10);
		this.ratingCount.setForeground(Colors.FOREGROUND_STANDARD);
		this.ratingCount.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		this.ratingCount.setDrawShade(true);
	}

	private void makeGenresPane() {
		this.genresPane = new WebPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.genresPane.setBackground(Colors.BACKGROUND_INFO);

		this.genres = new WebLabel();
		this.genresPane.add(this.genres);
		this.genres.setMargin(0, 5, 0, 5);
		this.genres.setFontSize(14);
		this.genres.setForeground(Colors.FOREGROUND_STANDARD);
		this.genres.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		this.genres.setDrawShade(true);
	}

	private WebPanel makeInfoPane() {
		this.infoPane = new WebPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		this.infoPane.setBackground(Colors.BACKGROUND_INFO);

		this.firstAired = new WebLabel();
		this.firstAired.setFontSize(12);
		this.firstAired.setForeground(Colors.FOREGROUND_STANDARD);
		this.firstAired.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		this.firstAired.setDrawShade(true);

		this.network = new WebLabel();
		this.network.setFontSize(12);
		this.network.setForeground(Colors.FOREGROUND_STANDARD);
		this.network.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		this.network.setDrawShade(true);

		this.contentRating = new WebImage();

		this.imdb = new WebLinkLabel();
		this.imdb.setToolTipText("Find out more on IMDb");

		this.plot = new WebImage(ImageRetriever.retrieveWallBaloon());
		this.plot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.plot.addMouseListener(this);
		this.plot.setToolTipText("Overview");
		
		this.extrafanarts = new WebImage(ImageRetriever.retrieveWallExtraFanarts());
		this.extrafanarts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.extrafanarts.addMouseListener(this);
		this.extrafanarts.setToolTipText("Extra Fanarts");

		return this.infoPane;
	}

}
