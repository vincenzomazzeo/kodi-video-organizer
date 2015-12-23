package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler.TvSerieImageLoaderListener;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.tvserie.TvSerieSeasonController.TvSerieSeasonListModel;
import it.ninjatech.kvo.ui.tvserie.TvSerieSeasonController.VideoSubtitleTransferHandler;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.Logger;
import it.ninjatech.kvo.util.MemoryUtils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.utils.SwingUtils;

public class TvSerieSeasonDialog extends WebDialog implements WindowListener, ActionListener, MouseListener, TvSerieImageLoaderListener {

	private static final long serialVersionUID = -2012464643608233002L;
	private static TvSerieSeasonDialog self;

	protected static TvSerieSeasonDialog getInstance(TvSerieSeasonController controller, TvSerieSeason season,
													 TvSerieSeasonListModel videoFileListModel, TvSerieSeasonListModel subtitleFileListModel,
													 boolean addTvSerieTitleClick, boolean addTvSerieSeasonTitleClick) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new TvSerieSeasonDialog();
			self.setShowCloseButton(false);
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
		}

		self.set(controller, season, videoFileListModel, subtitleFileListModel, addTvSerieTitleClick, addTvSerieSeasonTitleClick);

		return self;
	}

	private static WebPanel makeTvSerieTitlePane(TvSerie tvSerie, WebLabel tvSerieTitle) {
		WebPanel result = null;

		String tvSerieStatus = TvSerieHelper.getStatus(tvSerie);
		String tvSerieRating = TvSerieHelper.getRating(tvSerie);
		String tvSerieRatingCount = TvSerieHelper.getRatingCount(tvSerie);

		List<Component> components = new ArrayList<>();

		components.add(new WebImage(tvSerie.getLanguage().getLanguageFlag()));
		components.add(tvSerieTitle);
		if (StringUtils.isNotEmpty(tvSerieStatus)) {
			components.add(UIUtils.makeStandardLabel(String.format("(%s)", tvSerieStatus), 20, null, null));
		}
		if (StringUtils.isNotEmpty(tvSerieRating)) {
			components.add(UIUtils.makeRatingPane(new WebLabel(tvSerieRating), StringUtils.isNotEmpty(tvSerieRatingCount) ? new WebLabel(tvSerieRatingCount) : new WebLabel()));
		}

		result = UIUtils.makeFlowLayoutPane(FlowLayout.CENTER, 5, 0, components);

		return result;
	}

	@SuppressWarnings("unchecked")
	private static WebPanel makeFilesPane(WebList list, VideoSubtitleTransferHandler transferHandler, String title, int width) {
		WebPanel result = UIUtils.makeStandardPane(new BorderLayout());

		WebLabel titleL = UIUtils.makeStandardLabel(title, 20, new Insets(5, 5, 5, 5), SwingConstants.CENTER);
		result.add(titleL, BorderLayout.NORTH);
		titleL.setPreferredWidth(width - 10);

		list.setBackground(Colors.BACKGROUND_INFO);
		list.setCellRenderer(new CellRenderer());
		list.setDragEnabled(true);
		list.setTransferHandler(transferHandler);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		WebScrollPane listPane = UIUtils.makeScrollPane(list, WebScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, WebScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		result.add(listPane, BorderLayout.CENTER);

		return result;
	}

	private final Map<String, TvSerieEpisodeTile> tileMap;
	private TvSerieSeasonController controller;
	private WebLabel tvSerieTitle;
	private WebLabel tvSerieSeasonTitle;
	private Dimension seasonImageSize;
	private Dimension episodeImageSize;
	private WebButton confirm;
	private WebButton cancel;
	private ComponentTransition seasonImageTransition;
	private WebList videoFileList;
	private WebList subtitleFileList;
	private ComponentTransition detailTransition;

	private TvSerieSeasonDialog() {
		super(UI.get(), true);

		this.tileMap = new LinkedHashMap<>();

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		addWindowListener(this);
	}

	private void set(TvSerieSeasonController controller, TvSerieSeason season,
					 TvSerieSeasonListModel videoFileListModel, TvSerieSeasonListModel subtitleFileListModel,
					 boolean addTvSerieTitleClick, boolean addTvSerieSeasonTitleClick) {
		setTitle(Labels.getTvSerieTitleSeason(season));

		this.controller = controller;

		init(season, videoFileListModel, subtitleFileListModel, addTvSerieTitleClick, addTvSerieSeasonTitleClick);

		pack();
		setLocationRelativeTo(UI.get());
	}

	@Override
	public void windowOpened(WindowEvent event) {
	}

	@Override
	public void windowClosing(WindowEvent event) {
		this.controller.notifyCancel();
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

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.confirm) {
			this.controller.notifyConfirm();
		}
		else if (event.getSource() == this.cancel) {
			this.controller.notifyCancel();
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event)) {
			if (event.getSource() == this.tvSerieTitle) {
				this.controller.notifyTvSerieTitleLeftClick();
			}
			else if (event.getSource() == this.tvSerieSeasonTitle) {
				this.controller.notifyTvSerieSeasonTitleLeftClick();
			}
			else {
				this.controller.notifySeasonLeftClick();
			}
		}
		else if (SwingUtilities.isRightMouseButton(event)) {
		    if (event.getSource() == this.videoFileList) {
		        this.controller.notifyVideoFileRightClick(this.videoFileList.locationToIndex(event.getPoint()));
		    }
		    else if (event.getSource() == this.subtitleFileList) {
		        this.controller.notifySubtitleFileRightClick(this.subtitleFileList.locationToIndex(event.getPoint()));
		    }
		    else {
		        this.controller.notifySeasonRightClick();
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
		if (this.tileMap.containsKey(id)) {
			this.tileMap.get(id).setImage(image);
		}
		else {
			TooltipManager.removeTooltips(this.seasonImageTransition);
			this.seasonImageTransition.performTransition(UIUtils.makeImagePane(image, this.seasonImageSize));
			TooltipManager.addTooltip(this.seasonImageTransition, null, Labels.TOOLTIP_IMAGE_CHANGE_FULL, TooltipWay.down, (int)TimeUnit.SECONDS.toMillis(2));
		}
	}

    protected void release() {
		Logger.log("*** TvSerieSeasonDialog -> release ***\n");
		MemoryUtils.printMemory("Before TvSerieSeasonDialog release");
		TooltipManager.removeTooltips(this.seasonImageTransition);
		TooltipManager.removeTooltips(this.tvSerieTitle);
		TooltipManager.removeTooltips(this.tvSerieSeasonTitle);
		for (TvSerieEpisodeTile tile : this.tileMap.values()) {
			tile.destroy();
		}
		this.tileMap.clear();
		setContentPane(new WebPanel());
		MemoryUtils.printMemory("After TvSerieSeasonDialog release");
	}

	protected Dimension getSeasonImageSize() {
		return this.seasonImageSize;
	}

	protected Dimension getEpisodeImageSize() {
		return this.episodeImageSize;
	}

	protected void setEpisodeView(TvSerieEpisodeView view) {
		this.detailTransition.performTransition(view);
	}

	protected void setEpisodeVideoFile(TvSerieEpisode episode, String filename, boolean removable) {
		TvSerieEpisodeTile tile = this.tileMap.get(episode.getId());
		tile.setVideoFile(filename, removable);
	}
	
	protected void addEpisodeSubtitle(TvSerieEpisode episode, EnhancedLocale language, String filename, boolean removable) {
		TvSerieEpisodeTile tile = this.tileMap.get(episode.getId());
		tile.addSubtitle(language, filename, removable);
	}
	
	private void init(TvSerieSeason season, TvSerieSeasonListModel videoFileListModel, TvSerieSeasonListModel subtitleFileListModel, boolean addTvSerieTitleClick, boolean addTvSerieSeasonTitleClick) {
		WebPanel content = new WebPanel(new VerticalFlowLayout());
		setContentPane(content);

		content.setBackground(Colors.BACKGROUND_INFO);

		Dimension size = Dimensions.getTvSerieSeasonHandlerSize();
		int contentPaneColumnWidth = size.width / 5;
		WebPanel headerPane = makeHeaderPane(season, addTvSerieTitleClick, addTvSerieSeasonTitleClick);
		WebSplitPane contentPane = makeContentPane(season, videoFileListModel, subtitleFileListModel, contentPaneColumnWidth);
		contentPane.setPreferredSize(new Dimension(size.width, size.height - this.seasonImageSize.height - 20));

		content.add(headerPane);
		content.add(WebSeparator.createHorizontal());
		content.add(contentPane);
	}

	private WebPanel makeHeaderPane(TvSerieSeason season, boolean addTvSerieTitleClick, boolean addTvSerieSeasonTitleClick) {
		WebPanel result = UIUtils.makeStandardPane(new BorderLayout());

		// Season title
		this.seasonImageSize = Dimensions.getTvSerieSeasonHandlerPosterSize();
		WebDecoratedImage image = UIUtils.makeImagePane(this.seasonImageSize);
		this.seasonImageTransition = UIUtils.makeClickableTransition(image);
		if (season.hasImages()) {
			TooltipManager.addTooltip(this.seasonImageTransition, null, Labels.TOOLTIP_IMAGE_CHANGE, TooltipWay.down, (int)TimeUnit.SECONDS.toMillis(2));
			this.seasonImageTransition.addMouseListener(this);
		}

		this.tvSerieSeasonTitle = UIUtils.makeTitleLabel(Labels.getTvSerieSeason(season), 30, new Insets(2, 10, 5, 10));
		WebPanel dataPane = UIUtils.makeStandardPane(new VerticalFlowLayout());
		dataPane.add(UIUtils.makeFlowLayoutPane(FlowLayout.CENTER, 0, 0,
												this.tvSerieSeasonTitle));
		dataPane.add(UIUtils.makeFlowLayoutPane(FlowLayout.CENTER, 15, 0,
												UIUtils.makeStandardLabel(Labels.getTvSerieSeasonEpisodeCount(season), 20, null, null),
												UIUtils.makeRatingPane(new WebLabel(season.getAverageRating()), new WebLabel())));

		WebPanel leftPane = UIUtils.makeFlowLayoutPane(FlowLayout.LEFT, 5, 5,
													   this.seasonImageTransition,
													   dataPane);
		leftPane.setMargin(5, 10, 5, 10);
		result.add(leftPane, BorderLayout.WEST);

		// Serie title
		this.tvSerieTitle = UIUtils.makeTitleLabel(TvSerieHelper.getTitle(season.getTvSerie().getTvSeriePathEntity()), 30, new Insets(2, 10, 5, 10));
		result.add(makeTvSerieTitlePane(season.getTvSerie(), this.tvSerieTitle), BorderLayout.CENTER);

		// Buttons
		this.confirm = UIUtils.makeButton(ImageRetriever.retrieveTvSerieSeasonDialogConfirm(), this);
		this.cancel = UIUtils.makeButton(ImageRetriever.retrieveTvSerieSeasonDialogCancel(), this);
		WebPanel rightPane = UIUtils.makeFlowLayoutPane(FlowLayout.RIGHT, 5, 5, this.confirm, UIUtils.makeHorizontalFillerPane(5, false), this.cancel);
		rightPane.setMargin(5, 10, 5, 10);
		result.add(rightPane, BorderLayout.EAST);

		SwingUtils.equalizeComponentsWidths(leftPane, rightPane);

		if (addTvSerieTitleClick) {
			this.tvSerieTitle.addMouseListener(this);
			TooltipManager.addTooltip(this.tvSerieTitle, null, Labels.CLICK_TO_OPEN_IN_SYSTEM_EXPLORER, TooltipWay.down, (int)TimeUnit.SECONDS.toMillis(2));
			this.tvSerieTitle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		if (addTvSerieSeasonTitleClick) {
			this.tvSerieSeasonTitle.addMouseListener(this);
			TooltipManager.addTooltip(this.tvSerieSeasonTitle, null, Labels.CLICK_TO_OPEN_IN_SYSTEM_EXPLORER, TooltipWay.down, (int)TimeUnit.SECONDS.toMillis(2));
			this.tvSerieSeasonTitle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		return result;
	}

	private WebSplitPane makeContentPane(TvSerieSeason season, TvSerieSeasonListModel videoFileListModel, TvSerieSeasonListModel subtitleFileListModel, int columnWidth) {
		WebSplitPane result = null;

		WebPanel operationPane = UIUtils.makeStandardPane(new BorderLayout());
		operationPane.add(makeEpisodesPane(season, columnWidth), BorderLayout.WEST);
		operationPane.add(makeFilesPane(videoFileListModel, subtitleFileListModel, columnWidth), BorderLayout.CENTER);

		makeDetailTransition();

		result = new WebSplitPane(WebSplitPane.HORIZONTAL_SPLIT, operationPane, this.detailTransition);
		result.setDividerLocation(columnWidth * 2);
		result.setContinuousLayout(true);

		return result;
	}

	private WebScrollPane makeEpisodesPane(TvSerieSeason season, int width) {
		WebScrollPane result = null;

		WebPanel content = UIUtils.makeStandardPane(new VerticalFlowLayout());
		content.setPreferredWidth(width);
		content.setBackground(Colors.BACKGROUND_INFO);
		content.setOpaque(true);

		result = UIUtils.makeScrollPane(content, WebScrollPane.VERTICAL_SCROLLBAR_ALWAYS, WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.episodeImageSize = Dimensions.getTvSerieSeasonHandlerEpisodeImageSize();
		ImageIcon voidImage = UIUtils.makeEmptyIcon(this.episodeImageSize, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		ImageIcon voidVideoFileImage = UIUtils.makeEmptyIcon(new Dimension(ImageRetriever.EPISODE_TILE_VIDEO_FILE_ICON_SIZE, ImageRetriever.EPISODE_TILE_VIDEO_FILE_ICON_SIZE), Colors.BACKGROUND_INFO);
		for (TvSerieEpisode episode : season.getEpisodes()) {
			TvSerieEpisodeTile tile = new TvSerieEpisodeTile(this.controller, episode, voidImage, voidVideoFileImage, width);
			this.tileMap.put(episode.getId(), tile);
			content.add(tile);
			content.add(WebSeparator.createHorizontal());
		}

		return result;
	}

	private WebPanel makeFilesPane(TvSerieSeasonListModel videoFileListModel, TvSerieSeasonListModel subtitleFileListModel, int width) {
		WebPanel result = UIUtils.makeStandardPane(new GridLayout(2, 1));

		result.setPreferredWidth(width);

		this.videoFileList = new WebList(videoFileListModel);
		result.add(makeFilesPane(this.videoFileList, this.controller.makeVideoDragTransferHandler(), Labels.VIDEO_FILES, width));
		this.videoFileList.addMouseListener(this);

		this.subtitleFileList = new WebList(subtitleFileListModel);
		result.add(makeFilesPane(this.subtitleFileList, this.controller.makeSubtitleDragTransferHandler(), Labels.SUBTITLE_FILES, width));
		this.subtitleFileList.addMouseListener(this);

		return result;
	}

	private void makeDetailTransition() {
		this.detailTransition = new ComponentTransition(new FadeTransitionEffect());
		this.detailTransition.setOpaque(false);
	}

	private static class CellRenderer implements ListCellRenderer<String> {

		private CellRenderer() {
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
			WebLabel result = UIUtils.makeStandardLabel(value, 14, new Insets(5, 5, 5, 5), null);

			if (isSelected || cellHasFocus) {
				result.setBackground(Colors.BACKGROUND_INFO);
				result.setOpaque(true);
			}

			return result;
		}

	}

}
