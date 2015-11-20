package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler.TvSerieImageLoaderListener;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.PersonImageSlider;
import it.ninjatech.kvo.ui.tvserie.TvSerieEpisodeController.FileData;
import it.ninjatech.kvo.ui.tvserie.TvSerieEpisodeController.FileListModel;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.Logger;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.transition.ComponentTransition;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.text.WebTextArea;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

public class TvSerieEpisodeView extends WebPanel implements TvSerieImageLoaderListener, MouseListener {

	private static final long serialVersionUID = 8472397528077088727L;

	private final TvSerieEpisodeController controller;
	private ImageIcon voidArtwork;
	private WebPanel titlePane;
	private WebImage language;
	private WebLabel title;
	private WebLabel episodeNumber;
	private WebLabel rating;
	private WebLabel ratingCount;
	private WebPanel ratingPane;
	private WebPanel infoPane;
	private WebLabel firstAired;
	private WebLinkLabel imdb;
	private WebPanel contentPane;
	private ComponentTransition artworkTransition;
	private WebTextArea overview;
	private WebList fileList;
	private WebPanel directorsWritersPane;
	private PersonImageSlider directors;
	private PersonImageSlider writers;
	private WebPanel guestStarsPane;
	private PersonImageSlider guestStars;

	protected TvSerieEpisodeView(TvSerieEpisodeController controller, FileListModel fileListModel) {
		super();

		this.controller = controller;

		init(fileListModel);
	}

	@Override
	public void notifyImageLoaded(String id, Image image, Object supportData) {
		if (supportData != null) {
			if (supportData.getClass().equals(String.class)) {
				switch ((String)supportData) {
				case TvSerieEpisodeController.ARTWORK_ID:
					this.artworkTransition.performTransition(new WebImage(image));
					this.artworkTransition.addMouseListener(this);
					TooltipManager.addTooltip(this.artworkTransition, null, Labels.TOOLTIP_IMAGE_FULL, TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
					break;
				case TvSerieEpisodeController.DIRECTORS_SLIDER_ID:
					this.directors.setImage(id, image);
					break;
				case TvSerieEpisodeController.GUEST_STARS_SLIDER_ID:
					this.guestStars.setImage(id, image);
					break;
				case TvSerieEpisodeController.WRITERS_SLIDER_ID:
					this.writers.setImage(id, image);
					break;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isRightMouseButton(event)) {
			this.controller.notifyArtworkRightClick();
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

	protected void fill(TvSerieEpisode tvSerieEpisode) {
		// Title
		String rating = TvSerieHelper.getEpisodeRating(tvSerieEpisode);
		String ratingCount = TvSerieHelper.getEpisodeRatingCount(tvSerieEpisode);

		this.titlePane.removeAll();

		this.language.setIcon(tvSerieEpisode.getLanguage().getLanguageFlag());
		this.titlePane.add(this.language);
		this.title.setText(tvSerieEpisode.getName());
		this.titlePane.add(this.title);
		this.episodeNumber.setText(String.format("(%d/%d)", tvSerieEpisode.getNumber(), tvSerieEpisode.getSeason().getEpisodeCount()));
		this.titlePane.add(this.episodeNumber);
		if (StringUtils.isNotEmpty(rating)) {
			this.rating.setText(rating);
			this.titlePane.add(this.ratingPane);
			if (StringUtils.isNotEmpty(ratingCount)) {
				this.ratingCount.setText(ratingCount);
			}
		}

		// Info
		String firstAired = TvSerieHelper.getFirstAired(tvSerieEpisode);
		String imdbId = tvSerieEpisode.getImdbId();

		this.infoPane.removeAll();

		if (StringUtils.isNotBlank(firstAired)) {
			this.firstAired.setText(Labels.getFirstAired(firstAired));
			this.infoPane.add(this.firstAired);
		}
		if (StringUtils.isNotBlank(imdbId) && ImdbManager.getInstance().isEnabled()) {
			this.imdb.setIcon(ImageRetriever.retrieveWallIMDb());
			this.infoPane.add(this.imdb);
			this.imdb.setLink(ImdbManager.getTitleUrl(imdbId), false);
		}

		// Content
		String overview = tvSerieEpisode.getOverview();

		if (StringUtils.isNotBlank(overview)) {
			this.overview.setText(overview);
			this.overview.setCaretPosition(0);
		}
		else {
			this.overview.setText("");
		}

		// Directors
		Collection<String> directors = TvSerieHelper.getEpisodeDirectors(tvSerieEpisode);
		this.directors.setVisible(!directors.isEmpty());
		this.directors.fill(directors);

		// Writers
		Collection<String> writers = TvSerieHelper.getEpisodeWriters(tvSerieEpisode);
		this.writers.setVisible(!writers.isEmpty());
		this.writers.fill(writers);

		// Guest Stars
		Collection<String> guestStars = TvSerieHelper.getEpisodeGuestStars(tvSerieEpisode);
		this.guestStars.setVisible(!guestStars.isEmpty());
		this.guestStars.fill(guestStars);
	}

	protected Dimension getArtworkSize() {
		return new Dimension(this.voidArtwork.getIconWidth(), this.voidArtwork.getIconHeight());
	}

	protected Dimension getDirectorSize() {
		return this.directors.getImageSize(null);
	}

	protected Dimension getWriterSize() {
		return this.writers.getImageSize(null);
	}

	protected Dimension getGuestStarSize() {
		return this.guestStars.getImageSize(null);
	}

	protected void destroy() {
		Logger.log("*** TvSerieEpisodeView -> destroy ***\n");
		TooltipManager.removeTooltips(this.artworkTransition);
		this.directors.destroy();
		this.writers.destroy();
		this.guestStars.destroy();
	}

	private void init(FileListModel fileListModel) {
		setLayout(new VerticalFlowLayout());
		setBackground(Colors.BACKGROUND_INFO);

		makeTitlePane();
		makeInfoPane();
		makeContentPane(fileListModel);
		makeDirectorsWritersPane();
		makeGuestStarsPane();

		add(this.titlePane);
		add(this.infoPane);
		add(WebSeparator.createHorizontal());
		add(this.contentPane);
		add(WebSeparator.createHorizontal());
		add(this.directorsWritersPane);
		add(WebSeparator.createHorizontal());
		add(this.guestStarsPane);
	}

	private void makeTitlePane() {
		this.language = new WebImage();

		this.title = UIUtils.makeTitleLabel("", 24, new Insets(2, 2, 2, 2));

		this.episodeNumber = UIUtils.makeStandardLabel("", 18, null, null);

		this.rating = new WebLabel();
		this.ratingCount = new WebLabel();
		this.ratingPane = UIUtils.makeRatingPane(this.rating, this.ratingCount);

		this.titlePane = UIUtils.makeFlowLayoutPane(FlowLayout.CENTER, 5, 0);
	}

	private void makeInfoPane() {
		this.firstAired = UIUtils.makeStandardLabel("", 12, null, null);

		this.imdb = UIUtils.makeImdbTitleLink("");

		this.infoPane = UIUtils.makeFlowLayoutPane(FlowLayout.CENTER, 15, 5);
	}

	@SuppressWarnings("unchecked")
	private void makeContentPane(FileListModel fileListModel) {
		this.voidArtwork = UIUtils.makeEmptyIcon(Dimensions.getTvSerieEpisodeImageSize(), Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		this.artworkTransition = UIUtils.makeClickableTransition(new WebImage(this.voidArtwork));

		this.overview = new WebTextArea();
		WebScrollPane overviewPane = UIUtils.makeTextArea(this.overview);
		overviewPane.setPreferredHeight(this.voidArtwork.getIconHeight());

		this.fileList = new WebList(fileListModel);
		this.fileList.setBackground(Colors.BACKGROUND_INFO);
		this.fileList.setCellRenderer(new CellRenderer());
		this.fileList.setDragEnabled(false);
		this.fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		WebScrollPane listPane = UIUtils.makeScrollPane(this.fileList, WebScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, WebScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listPane.setPreferredWidth(Dimensions.getTvSerieSeasonHandlerSize().width / 6);
		listPane.setPreferredHeight(this.voidArtwork.getIconHeight());

		this.contentPane = UIUtils.makeStandardPane(new BorderLayout(20, 0));
		this.contentPane.add(this.artworkTransition, BorderLayout.WEST);
		this.contentPane.add(overviewPane, BorderLayout.CENTER);
		this.contentPane.add(listPane, BorderLayout.EAST);
	}

	private void makeDirectorsWritersPane() {
		this.directors = new PersonImageSlider(TvSerieEpisodeController.DIRECTORS_SLIDER_ID, this.controller);

		this.writers = new PersonImageSlider(TvSerieEpisodeController.WRITERS_SLIDER_ID, this.controller);

		this.directorsWritersPane = UIUtils.makeStandardPane(new GridLayout(1, 2));
		this.directorsWritersPane.add(UIUtils.makeSliderPane(Labels.DIRECTORS, this.directors));
		this.directorsWritersPane.add(UIUtils.makeSliderPane(Labels.WRITERS, this.writers));
	}

	private void makeGuestStarsPane() {
		this.guestStars = new PersonImageSlider(TvSerieEpisodeController.GUEST_STARS_SLIDER_ID, this.controller);

		this.guestStarsPane = UIUtils.makeSliderPane(Labels.GUEST_STARS, this.guestStars);
	}

	private static class CellRenderer implements ListCellRenderer<FileData> {

		private CellRenderer() {
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends FileData> list, FileData value, int index, boolean isSelected, boolean cellHasFocus) {
			WebLabel result = UIUtils.makeStandardLabel(value.getFile(), 12, new Insets(5, 5, 5, 5), null);

			if (isSelected || cellHasFocus) {
				result.setBackground(Colors.BACKGROUND_INFO);
				result.setOpaque(true);
			}

			if (value.isVideo()) {
				result.setIcon(ImageRetriever.retrieveEpisodeVideoFile());
			}
			else {
				result.setIcon(value.getLanguage().getLanguageFlag());
			}

			return result;
		}

	}

}
