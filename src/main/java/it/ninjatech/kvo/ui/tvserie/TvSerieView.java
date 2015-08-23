package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSerieActor;
import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler.TvSerieImageLoaderListener;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.Logger;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.text.WebTextArea;


public class TvSerieView extends WebPanel implements TvSerieImageLoaderListener, ActionListener {

	private static final long serialVersionUID = -8219959298613920784L;

	private final TvSerieController controller;
	private WebPopOver plotPopOver;
	private WebButton plotPopOverClose;
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
	private WebButton plot;
	private WebButton extrafanarts;
	private TvSerieFanartSlider fanarts;
	private TvSerieSeasonImageSlider seasons;
	private TvSerieActorImageSlider actors;

	protected TvSerieView(TvSerieController controller) {
		super();

		this.controller = controller;

		init();
		initPlotPopOver();
	}

	@Override
	public void notifyImageLoaded(String id, Image image, Object supportData) {
		if (supportData.getClass().equals(TvSerieFanart.class)) {
			this.fanarts.setImage((TvSerieFanart)supportData, image);
		}
		else if (supportData.getClass().equals(TvSerieSeason.class)) {
			this.seasons.setImage((TvSerieSeason)supportData, image);
		}
		else if (supportData.getClass().equals(TvSerieActor.class)) {
			this.actors.setImage((TvSerieActor)supportData, image);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.plot) {
			this.plotPopOver.show((Component)event.getSource());
		}
		else if (event.getSource() == this.extrafanarts) {
			this.controller.notifyExtraFanartsClick();
		}
		else if (event.getSource().equals(this.plotPopOverClose)) {
			this.plotPopOver.dispose();
		}
	}

	protected void fill(TvSerie tvSerie) {
		// Title
		String status = TvSerieHelper.getStatus(tvSerie);
		String rating = TvSerieHelper.getRating(tvSerie);
		String ratingCount = TvSerieHelper.getRatingCount(tvSerie);

		this.titlePane.removeAll();

		this.language.setIcon(tvSerie.getLanguage().getLanguageFlag());
		this.titlePane.add(this.language);
		this.title.setText(TvSerieHelper.getTitle(tvSerie.getTvSeriePathEntity()));
		this.titlePane.add(this.title);
		if (StringUtils.isNotEmpty(status)) {
			this.status.setText(String.format("(%s)", status));
			this.titlePane.add(this.status);
		}
		if (StringUtils.isNotEmpty(rating)) {
			this.rating.setText(rating);
			this.titlePane.add(this.ratingPane);
			if (StringUtils.isNotEmpty(ratingCount)) {
				this.ratingCount.setText(ratingCount);
			}
		}

		// Genres
		this.genres.setText(TvSerieHelper.getGenre(tvSerie));

		// Info
		String firstAired = TvSerieHelper.getFirstAired(tvSerie);
		String network = TvSerieHelper.getNetwork(tvSerie);
		String contentRating = TvSerieHelper.getContentRating(tvSerie);
		String imdbId = TvSerieHelper.getImdbId(tvSerie);
		String overview = TvSerieHelper.getOverview(tvSerie);

		this.infoPane.removeAll();

		if (StringUtils.isNotBlank(firstAired)) {
			this.firstAired.setText(Labels.getFirstAired(firstAired));
			this.infoPane.add(this.firstAired);
		}
		if (StringUtils.isNotBlank(network)) {
			this.network.setText(Labels.getNetwork(network));
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
			WebTextArea plot = (WebTextArea)((WebScrollPane)this.plotPopOver.getContentPane().getComponent(1)).getViewport().getView();
			plot.setText(overview);
			plot.setCaretPosition(0);
			this.infoPane.add(this.plot);
		}
		if (TvSerieHelper.hasExtraFanarts(tvSerie.getTvSeriePathEntity())) {
			this.infoPane.add(this.extrafanarts);
		}

		// Fanarts
		this.fanarts.fill(Arrays.asList(TvSerieFanart.values()));

		// Seasons
		this.seasons.fill(TvSerieHelper.getSeasons(tvSerie));

		// Actors
		Collection<TvSerieActor> actors = TvSerieHelper.getActors(tvSerie);
		this.actors.setVisible(!actors.isEmpty());
		this.actors.fill(actors);
	}

	protected Dimension getFanartSize(TvSerieFanart fanart) {
		return this.fanarts.getImageSize(fanart);
	}

	protected Dimension getSeasonSize() {
		return this.seasons.getImageSize(null);
	}

	protected Dimension getActorSize() {
		return this.actors.getImageSize(null);
	}
	
	protected void refreshSeason(TvSerieSeason season) {
		this.seasons.refresh(season);
	}

	protected void destroy() {
		Logger.log("*** TvSerieView -> destroy ***\n");
		this.fanarts.destroy();
		this.seasons.destroy();
		this.actors.destroy();
	}

	private void init() {
		setLayout(new VerticalFlowLayout());

		setBackground(Colors.BACKGROUND_INFO);
		setPreferredWidth(0);

		this.fanarts = new TvSerieFanartSlider(TvSerieController.FANARTS_SLIDER_ID, this.controller);
		this.seasons = new TvSerieSeasonImageSlider(TvSerieController.SEASONS_SLIDER_ID, this.controller);
		this.actors = new TvSerieActorImageSlider(TvSerieController.ACTORS_SLIDER_ID, this.controller);

		makeTitlePane();
		makeGenresPane();
		makeInfoPane();

		add(this.titlePane);
		add(this.genresPane);
		add(this.infoPane);
		add(WebSeparator.createHorizontal());
		add(UIUtils.makeSliderPane(Labels.FANARTS, this.fanarts));
		add(WebSeparator.createHorizontal());
		add(UIUtils.makeSliderPane(Labels.SEASONS, this.seasons));
		add(WebSeparator.createHorizontal());
		add(UIUtils.makeSliderPane(Labels.ACTORS, this.actors));
	}

	private void initPlotPopOver() {
		this.plotPopOver = new WebPopOver();
		this.plotPopOver.setMargin(2);
		this.plotPopOver.setMovable(false);
		this.plotPopOver.setLayout(new BorderLayout());
		this.plotPopOver.setModal(true);
		this.plotPopOver.setAlwaysOnTop(true);
		this.plotPopOver.setCloseOnFocusLoss(false);

		this.plotPopOverClose = UIUtils.makeButton(ImageRetriever.retrievePopupCancel(), this);
		this.plotPopOver.add(UIUtils.makeFlowLayoutPane(FlowLayout.RIGHT, 2, 2, this.plotPopOverClose), BorderLayout.NORTH);

		WebTextArea plot = new WebTextArea();
		plot.setLineWrap(true);
		plot.setWrapStyleWord(true);
		plot.setEditable(false);
		plot.setMargin(5);
		plot.setBackground(Colors.BACKGROUND_INFO);
		plot.setForeground(Colors.FOREGROUND_STANDARD);

		WebScrollPane scrollPane = UIUtils.makeScrollPane(plot, WebScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, WebScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(300, 300));

		this.plotPopOver.add(scrollPane, BorderLayout.CENTER);
	}

	private void makeTitlePane() {
		this.language = new WebImage();

		this.title = UIUtils.makeTitleLabel("", 30, new Insets(2, 10, 5, 10));

		this.status = UIUtils.makeStandardLabel("", 20, null, null);

		this.rating = new WebLabel();
		this.ratingCount = new WebLabel();
		this.ratingPane = UIUtils.makeRatingPane(this.rating, this.ratingCount);

		this.titlePane = UIUtils.makeFlowLayoutPane(FlowLayout.CENTER, 5, 0,
													this.language,
													this.title,
													this.status,
													this.ratingPane);
	}

	private void makeGenresPane() {
		this.genres = UIUtils.makeStandardLabel("", 14, new Insets(0, 5, 0, 5), null);

		this.genresPane = UIUtils.makeFlowLayoutPane(FlowLayout.CENTER, 0, 0, this.genres);
	}

	private void makeInfoPane() {
		this.firstAired = UIUtils.makeStandardLabel("", 12, null, null);

		this.network = UIUtils.makeStandardLabel("", 12, null, null);

		this.contentRating = new WebImage();

		this.imdb = UIUtils.makeImdbLink("");

		this.plot = UIUtils.makeButton(ImageRetriever.retrieveWallBaloon(), this);
		this.plot.setToolTipText(Labels.OVERVIEW);

		this.extrafanarts = UIUtils.makeButton(ImageRetriever.retrieveWallExtraFanarts(), this);
		this.extrafanarts.setToolTipText(Labels.EXTRA_FANARTS);

		this.infoPane = UIUtils.makeFlowLayoutPane(FlowLayout.CENTER, 15, 5,
												   this.firstAired,
												   this.network,
												   this.contentRating,
												   this.imdb,
												   this.plot,
												   this.extrafanarts);
	}

}
