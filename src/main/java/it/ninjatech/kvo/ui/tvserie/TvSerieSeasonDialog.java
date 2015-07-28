package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler.TvSerieImageLoaderListener;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.tvserie.TvSerieSeasonController.VideoSubtitleTransferHandler;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.WebOverlay;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.global.StyleConstants;
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

public class TvSerieSeasonDialog extends WebDialog implements WindowListener, ActionListener, MouseListener, TvSerieImageLoaderListener {

	private static final long serialVersionUID = -2012464643608233002L;

	private static WebDecoratedImage makeImagePane(ImageIcon image, Dimension size) {
		WebDecoratedImage result = new WebDecoratedImage(image);

		result.setMinimumSize(size);
		result.setShadeWidth(5);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setDrawGlassLayer(false);

		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static WebPanel makeFilesPane(WebList list, VideoSubtitleTransferHandler transferHandler, String title, int width) {
		WebPanel result = new WebPanel(new BorderLayout());
		
		result.setOpaque(false);
		
		WebLabel titleL = new WebLabel(title);
		result.add(titleL, BorderLayout.NORTH);
		titleL.setPreferredWidth(width - 10);
		titleL.setMargin(5, 5, 5, 5);
		titleL.setFontSize(20);
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setForeground(Colors.FOREGROUND_STANDARD);
		titleL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		titleL.setDrawShade(true);
		
		list.setBackground(Colors.BACKGROUND_INFO);
		list.setCellRenderer(new CellRenderer());
		list.setDragEnabled(true);
		list.setTransferHandler(transferHandler);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		WebScrollPane listPane = new WebScrollPane(list, false, false);
		result.add(listPane, BorderLayout.CENTER);
		listPane.getVerticalScrollBar().setBlockIncrement(30);
		listPane.getVerticalScrollBar().setUnitIncrement(30);
		listPane.getHorizontalScrollBar().setBlockIncrement(30);
		listPane.getHorizontalScrollBar().setUnitIncrement(30);
		
		return result;
	}
	
	private final TvSerieSeasonController controller;
	private final Map<String, TvSerieEpisodeTile> tileMap;
	private Dimension seasonImageSize;
	private Dimension episodeImageSize;
	private WebButton confirm;
	private WebButton cancel;
	private ComponentTransition transition;
	private WebList videoFileList;
	private WebList subtitleFileList;

	protected TvSerieSeasonDialog(TvSerieSeasonController controller, TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season, TvSerieSeasonListModel videoFileListModel, TvSerieSeasonListModel subtitleFileListModel) {
		super(UI.get(), String.format("%s - Season %s", TvSerieUtils.getTitle(tvSeriePathEntity), season.getNumber()), true);

		this.controller = controller;
		this.tileMap = new HashMap<>();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);

		init(tvSeriePathEntity, season, videoFileListModel, subtitleFileListModel);

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
			this.controller.notifySeasonLeftClick();
		}
		else if (SwingUtilities.isRightMouseButton(event)) {
			this.controller.notifySeasonRightClick();
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
			TooltipManager.removeTooltips(this.transition);
			this.transition.performTransition(makeImagePane(new ImageIcon(image), this.seasonImageSize));
			TooltipManager.addTooltip(this.transition, null, "<html><div align='center'>Left click to change<br />Right click for full size image</html>", TooltipWay.down, (int)TimeUnit.SECONDS.toMillis(2));
		}
	}

	protected void destroy() {
		System.out.println("*** TvSerieSeasonDialog -> destory ***");
		TooltipManager.removeTooltips(this.transition);
		for (TvSerieEpisodeTile tile : this.tileMap.values()) {
			tile.dispose();
		}
		this.tileMap.clear();
	}
	
	protected Dimension getSeasonImageSize() {
		return this.seasonImageSize;
	}
	
	protected Dimension getEpisodeImageSize() {
		return this.episodeImageSize;
	}

	private void init(TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season, TvSerieSeasonListModel videoFileListModel, TvSerieSeasonListModel subtitleFileListModel) {
		WebPanel content = new WebPanel(new VerticalFlowLayout());
		setContentPane(content);

		content.setBackground(Colors.BACKGROUND_INFO);

		Dimension size = Dimensions.getTvSerieSeasonHandlerSize();
		int contentPaneColumnWidth = size.width / 5;
		WebPanel headerPane = makeHeaderPane(season);
		WebSplitPane contentPane = makeContentPane(season, videoFileListModel, subtitleFileListModel, contentPaneColumnWidth);
		contentPane.setPreferredSize(new Dimension(size.width, size.height - this.seasonImageSize.height - 20));

		content.add(headerPane);
		content.add(WebSeparator.createHorizontal());
		content.add(contentPane);
	}

	private WebPanel makeHeaderPane(TvSerieSeason season) {
		WebPanel result = new WebPanel(new BorderLayout());
		
		result.setOpaque(false);
		
		WebPanel leftPane = new WebPanel(new FlowLayout(FlowLayout.LEFT));
		result.add(leftPane, BorderLayout.WEST);

		leftPane.setOpaque(false);
		leftPane.setMargin(5, 10, 5, 10);

		this.seasonImageSize = Dimensions.getTvSerieSeasonHandlerPosterSize();
		ImageIcon voidImage = UIUtils.makeEmptyIcon(this.seasonImageSize, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		WebDecoratedImage image = makeImagePane(voidImage, this.seasonImageSize);
		this.transition = new ComponentTransition(image, new FadeTransitionEffect());
		leftPane.add(this.transition);
		this.transition.setOpaque(false);
		this.transition.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if (season.hasImages()) {
			TooltipManager.addTooltip(this.transition, null, "<html><div align='center'>Left click to change</html>", TooltipWay.down, (int)TimeUnit.SECONDS.toMillis(2));
			this.transition.addMouseListener(this);
		}

		WebPanel dataPane = new WebPanel(new VerticalFlowLayout());
		leftPane.add(dataPane);
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

		WebPanel rightPane = new WebPanel(new FlowLayout(FlowLayout.RIGHT));
		result.add(rightPane, BorderLayout.EAST);

		rightPane.setOpaque(false);
		rightPane.setMargin(5, 10, 5, 10);

		this.confirm = WebButton.createIconWebButton(ImageRetriever.retrieveTvSerieSeasonDialogConfirm(), StyleConstants.smallRound, true);
		this.confirm.addActionListener(this);
		rightPane.add(this.confirm);
		
		rightPane.add(UIUtils.makeHorizontalFillerPane(5, false));
		
		this.cancel = WebButton.createIconWebButton(ImageRetriever.retrieveTvSerieSeasonDialogCancel(), StyleConstants.smallRound, true);
		this.cancel.addActionListener(this);
		rightPane.add(this.cancel);
		
		return result;
	}

	private WebSplitPane makeContentPane(TvSerieSeason season, TvSerieSeasonListModel videoFileListModel, TvSerieSeasonListModel subtitleFileListModel, int columnWidth) {
		WebSplitPane result = null;

		WebPanel operationPane = new WebPanel(new BorderLayout());
		operationPane.setOpaque(false);
		operationPane.add(makeEpisodesPane(season, columnWidth), BorderLayout.WEST);
		operationPane.add(makeFilesPane(videoFileListModel, subtitleFileListModel, columnWidth), BorderLayout.CENTER);

		result = new WebSplitPane(WebSplitPane.HORIZONTAL_SPLIT, operationPane, makeDetailPane());
		result.setDividerLocation(columnWidth * 2);
		result.setContinuousLayout(true);

		return result;
	}

	private WebScrollPane makeEpisodesPane(TvSerieSeason season, int width) {
		WebScrollPane result = null;

		WebPanel content = new WebPanel(new VerticalFlowLayout());
		result = new WebScrollPane(content, false, false);
		result.getVerticalScrollBar().setBlockIncrement(30);
		result.getVerticalScrollBar().setUnitIncrement(30);
		result.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		result.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		content.setPreferredWidth(width);
		content.setBackground(Colors.BACKGROUND_INFO);

		this.episodeImageSize = Dimensions.getTvSerieSeasonEpisodeImageSize();
		ImageIcon voidImage = UIUtils.makeEmptyIcon(this.episodeImageSize, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		ImageIcon voidVideoFileImage = UIUtils.makeEmptyIcon(new Dimension(ImageRetriever.EPISODE_TILE_VIDEO_FILE_SIZE, ImageRetriever.EPISODE_TILE_VIDEO_FILE_SIZE), Colors.BACKGROUND_INFO);
		for (TvSerieEpisode episode : season.getEpisodes()) {
			TvSerieEpisodeTile tile = new TvSerieEpisodeTile(this.controller, episode, voidImage, voidVideoFileImage, width);
			this.tileMap.put(episode.getId(), tile);
			content.add(tile);
			content.add(WebSeparator.createHorizontal());
		}

		return result;
	}

	private WebPanel makeFilesPane(TvSerieSeasonListModel videoFileListModel, TvSerieSeasonListModel subtitleFileListModel, int width) {
		WebPanel result = new WebPanel(new GridLayout(2, 1));
		
		result.setOpaque(false);
		result.setPreferredWidth(width);

		this.videoFileList = new WebList(videoFileListModel);
		result.add(makeFilesPane(this.videoFileList, this.controller.makeVideoDragTransferHandler(), "Video Files", width));
		
		this.subtitleFileList = new WebList(subtitleFileListModel);
		result.add(makeFilesPane(this.subtitleFileList, this.controller.makeSubtitleDragTransferHandler(), "Subtitle Files", width));
		
		return result;
	}
	
	private WebPanel makeDetailPane() {
		WebPanel result = new WebPanel();

		result.setOpaque(false);

		return result;
	}

	private static class CellRenderer implements ListCellRenderer<String> {

		private CellRenderer() {}
		
		@Override
		public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
			WebLabel result = new WebLabel(value);
			
			result.setMargin(5, 5, 5, 5);
			result.setFontSize(14);
			result.setForeground(Colors.FOREGROUND_STANDARD);
			result.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
			result.setDrawShade(true);
			if (isSelected) {
				result.setBackground(Colors.BACKGROUND_INFO);
				result.setOpaque(true);
			}
			
			return result;
		}

	}
	
}
