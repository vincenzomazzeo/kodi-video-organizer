package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.AbstractTvSerieImage;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerieImageProvider;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler.TvSerieImageLoaderListener;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

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
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.utils.SwingUtils;

public class TvSerieImageChoiceDialog<I extends AbstractTvSerieImage> extends WebDialog implements WindowListener, TvSerieImageLoaderListener {

	private static WebOverlay makeImage(ImageIcon image, ImageIcon logo) {
		WebOverlay result = null; 
		
		WebDecoratedImage background = new WebDecoratedImage(image);
		background.setMinimumSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
		background.setShadeWidth(5);
		background.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		background.setDrawGlassLayer(false);
		
		WebImage foreground = new WebImage(logo);
		
		result = new WebOverlay(background, foreground, SwingConstants.RIGHT, SwingConstants.BOTTOM);
		result.setOverlayMargin(10);
		result.setBackground(Colors.TRANSPARENT);

		return result;
	}

	private static final long serialVersionUID = 8622387193399679715L;

	private final String id;
	private final ImageChoiceController controller;
	private final Set<I> images;
	private final Dimension imageSize;
	private final Map<TvSerieImageProvider, ImageIcon> providerLogos;
	private final Map<String, ImageChoicePane<I>> panes;
	private ImageIcon voidImage;

	protected TvSerieImageChoiceDialog(String id, ImageChoiceController controller, String title, Set<I> images, Dimension imageSize) {
		super(UI.get(), title, true);

		this.id = id;
		this.controller = controller;
		this.images = images;
		this.imageSize = imageSize;
		this.providerLogos = new EnumMap<>(TvSerieImageProvider.class);
		this.panes = new HashMap<>();

		this.providerLogos.put(TvSerieImageProvider.Fanarttv, ImageRetriever.retrieveFanartChoiceFanarttvLogo());
		this.providerLogos.put(TvSerieImageProvider.TheTvDb, ImageRetriever.retrieveFanartChoiceTheTvDbLogo());

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);

		init();

		pack();
		setLocationRelativeTo(UI.get());
	}

	@Override
	public void windowOpened(WindowEvent event) {
	}

	@Override
	public void windowClosing(WindowEvent event) {
		close();
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
	public void notifyImageLoaded(String id, Image image, Object supportData) {
		this.panes.get(id).setImage(image);
	}

	private void close() {
		this.controller.notifyClosing(this.id);
		for (ImageChoicePane<I> pane : this.panes.values()) {
			pane.dispose();
		}
		this.providerLogos.clear();
		this.panes.clear();
		this.voidImage = null;
	}
	
	private void init() {
		WebPanel content = new WebPanel(new VerticalFlowLayout());
		setContentPane(content);

		content.setBackground(Colors.BACKGROUND_INFO);

		this.voidImage = UIUtils.makeEmptyIcon(this.imageSize, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);

		WebScrollPane gallery = new WebScrollPane(makeGalleryPane(), false, false);
		gallery.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gallery.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		gallery.setBackground(Colors.BACKGROUND_INFO);
		gallery.setPreferredHeight(Dimensions.getImageChoiceAvailableHeight());
		gallery.getVerticalScrollBar().setBlockIncrement(30);
		gallery.getVerticalScrollBar().setUnitIncrement(30);

		content.add(gallery);
	}

	private WebPanel makeGalleryPane() {
		WebPanel result = new WebPanel(new FlowLayout());

		result.setBackground(Colors.BACKGROUND_INFO);

		int availableWidth = Dimensions.getImageChoiceAvailableWidth();
		int paneCount = availableWidth / this.voidImage.getIconWidth();
		WebPanel[] panels = new WebPanel[paneCount];

		for (int i = 0; i < panels.length; i++) {
			panels[i] = new WebPanel(new VerticalFlowLayout(0, 10));
			result.add(panels[i]);
			panels[i].setOpaque(false);
		}

		int i = 0;
		for (I image : this.images) {
			ImageChoicePane<I> pane = new ImageChoicePane<>(this, image, image.getLanguage(), image.getRating(), image.getRatingCount());
			panels[i++ % paneCount].add(pane);
			this.panes.put(image.getId(), pane);
		}

		SwingUtils.equalizeComponentsSize(panels);

		return result;
	}

	private static class ImageChoicePane<I extends AbstractTvSerieImage> extends WebPanel implements MouseListener {

		private static final long serialVersionUID = 8567489733354643481L;

		private final TvSerieImageChoiceDialog<I> owner;
		private final I image;
		private ComponentTransition imageTransition;
		private WebLabel ratingCount;
		private WebLabel rating;

		public ImageChoicePane(TvSerieImageChoiceDialog<I> owner, I image,
							   EnhancedLocale language, String rating, String ratingCount) {
			super(new VerticalFlowLayout());

			this.owner = owner;
			this.image = image;

			init(language, rating, ratingCount);
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			if (SwingUtilities.isLeftMouseButton(event)) {
				this.owner.controller.notifyImageLeftClick(this.owner.id, this.image);
				this.owner.setVisible(false);
				this.owner.close();
				this.owner.dispose();
			}
			else if (SwingUtilities.isRightMouseButton(event)) {
				this.owner.controller.notifyImageRightClick(this.owner.id, this.image);
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

		private void setImage(Image image) {
			if (image != null) {
				TooltipManager.removeTooltips(this.imageTransition);
				this.imageTransition.performTransition(makeImage(new ImageIcon(image), this.owner.providerLogos.get(this.image.getProvider())));
				TooltipManager.addTooltip(this.imageTransition, null, "<html><div align='center'>Left click to select<br />Right click for full size image</div></html>", TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
				this.imageTransition.addMouseListener(this);
				this.imageTransition.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}

		private void dispose() {
			TooltipManager.removeTooltips(this.imageTransition);
		}

		private void init(EnhancedLocale language, String rating, String ratingCount) {
			setOpaque(false);

			this.imageTransition = new ComponentTransition(makeImage(this.owner.voidImage, this.owner.providerLogos.get(this.image.getProvider())), 
			                                               new FadeTransitionEffect());
			add(this.imageTransition);
			this.imageTransition.setOpaque(false);

			WebPanel bottomPane = new WebPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
			add(bottomPane);
			bottomPane.setOpaque(false);

			bottomPane.add(new WebImage(language.getLanguageFlag()));

			WebPanel ratingPane = new WebPanel(new VerticalFlowLayout());
			bottomPane.add(ratingPane);
			ratingPane.setOpaque(false);

			WebImage star = new WebImage(ImageRetriever.retrieveWallStar());

			this.rating = new WebLabel();
			this.rating.setFontSize(14);
			this.rating.setForeground(Colors.FOREGROUND_STANDARD);
			this.rating.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
			this.rating.setDrawShade(true);
			if (StringUtils.isNotBlank(rating)) {
				this.rating.setText(rating);
			}

			WebOverlay starOverlay = new WebOverlay(star, this.rating, SwingConstants.CENTER, SwingConstants.CENTER);
			ratingPane.add(starOverlay);
			starOverlay.setBackground(Colors.TRANSPARENT);

			this.ratingCount = new WebLabel("0");
			ratingPane.add(this.ratingCount);
			this.ratingCount.setHorizontalAlignment(SwingConstants.CENTER);
			this.ratingCount.setFontSize(10);
			this.ratingCount.setForeground(Colors.FOREGROUND_STANDARD);
			this.ratingCount.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
			if (StringUtils.isNotBlank(ratingCount)) {
				this.ratingCount.setText(ratingCount);
				this.ratingCount.setDrawShade(true);
			}
			else {
				this.ratingCount.setForeground(Colors.TRANSPARENT);
			}
		}

	}

	public static interface ImageChoiceController {

		public void notifyClosing(String id);

		public void notifyImageLeftClick(String id, AbstractTvSerieImage image);

		public void notifyImageRightClick(String id, AbstractTvSerieImage image);

	}

}
