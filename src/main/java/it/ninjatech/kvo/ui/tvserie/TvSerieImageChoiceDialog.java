package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.AbstractTvSerieImage;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieImageLoaderAsyncJobHandler.TvSerieImageLoaderListener;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.Logger;
import it.ninjatech.kvo.util.MemoryUtils;

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
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.utils.SwingUtils;

public class TvSerieImageChoiceDialog extends WebDialog implements WindowListener, TvSerieImageLoaderListener {

	private static TvSerieImageChoiceDialog self;
	
	public static TvSerieImageChoiceDialog getInstance(ImageChoiceController controller, String title, Set<? extends AbstractTvSerieImage> images, Dimension imageSize) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new TvSerieImageChoiceDialog();
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
		}
		
		self.set(controller, title, images, imageSize);
		
		return self;
	}
	
	private static WebOverlay makeImage(ImageIcon image, ImageIcon logo) {
		WebOverlay result = null; 
		
		WebDecoratedImage background = UIUtils.makeImagePane(image, new Dimension(image.getIconWidth(), image.getIconHeight()));
		WebImage foreground = new WebImage(logo);
		
		result = new WebOverlay(background, foreground, SwingConstants.RIGHT, SwingConstants.BOTTOM);
		result.setOverlayMargin(10);
		result.setBackground(Colors.TRANSPARENT);

		return result;
	}

	private static final long serialVersionUID = 8622387193399679715L;

	private final Map<ImageProvider, ImageIcon> providerLogos;
	private final Map<String, ImageChoicePane> panes;
	private ImageChoiceController controller;
	private Set<? extends AbstractTvSerieImage> images;
	private Dimension imageSize;
	private ImageIcon voidImage;

	private TvSerieImageChoiceDialog() {
		super(UI.get(), true);

		this.providerLogos = new EnumMap<>(ImageProvider.class);
		this.panes = new HashMap<>();
		
		this.providerLogos.put(ImageProvider.Fanarttv, ImageRetriever.retrieveFanartChoiceFanarttvLogo());
		this.providerLogos.put(ImageProvider.TheTvDb, ImageRetriever.retrieveFanartChoiceTheTvDbLogo());

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent event) {
	}

	@Override
	public void windowClosing(WindowEvent event) {
		release();
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

	public void release() {
		Logger.log("*** TvSerieImageChoiceDialog -> release ***\n");
		MemoryUtils.printMemory("Before TvSerieImageChoiceDialog release");
		this.controller.notifyImageChoiceClosing();
		for (ImageChoicePane pane : this.panes.values()) {
			pane.destroy();
		}
		this.panes.clear();
		this.voidImage = null;
		setContentPane(new WebPanel());
		MemoryUtils.printMemory("After TvSerieImageChoiceDialog release");
	}

	private void set(ImageChoiceController controller, String title, Set<? extends AbstractTvSerieImage> images, Dimension imageSize) {
		setTitle(title);
		
		this.controller = controller;
		this.images = images;
		this.imageSize = imageSize;
		
		init();

		pack();
		setLocationRelativeTo(UI.get());
	}
	
	private void init() {
		WebPanel content = UIUtils.makeStandardPane(new VerticalFlowLayout());
		setContentPane(content);

		content.setOpaque(false);
		content.setBackground(Colors.BACKGROUND_INFO);

		this.voidImage = UIUtils.makeEmptyIcon(this.imageSize, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);

		WebScrollPane gallery = UIUtils.makeScrollPane(makeGalleryPane(), WebScrollPane.VERTICAL_SCROLLBAR_ALWAYS, WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gallery.setPreferredHeight(Dimensions.getImageChoiceAvailableHeight());

		content.add(gallery);
	}

	private WebPanel makeGalleryPane() {
		WebPanel result = UIUtils.makeStandardPane(new FlowLayout());

		result.setOpaque(true);
		result.setBackground(Colors.BACKGROUND_INFO);

		int availableWidth = Dimensions.getImageChoiceAvailableWidth();
		int paneCount = availableWidth / this.voidImage.getIconWidth();
		WebPanel[] panels = new WebPanel[paneCount];

		for (int i = 0; i < panels.length; i++) {
			panels[i] = UIUtils.makeStandardPane(new VerticalFlowLayout(0, 10));
			result.add(panels[i]);
		}

		int i = 0;
		for (AbstractTvSerieImage image : this.images) {
			ImageChoicePane pane = new ImageChoicePane(this, image, image.getLanguage(), image.getRating(), image.getRatingCount());
			panels[i++ % paneCount].add(pane);
			this.panes.put(image.getId(), pane);
		}

		SwingUtils.equalizeComponentsSize(panels);

		return result;
	}

	private static class ImageChoicePane extends WebPanel implements MouseListener {

		private static final long serialVersionUID = 8567489733354643481L;

		private final TvSerieImageChoiceDialog owner;
		private final AbstractTvSerieImage image;
		private ComponentTransition imageTransition;
		private WebLabel ratingCount;
		private WebLabel rating;

		public ImageChoicePane(TvSerieImageChoiceDialog owner, AbstractTvSerieImage image,
							   EnhancedLocale language, String rating, String ratingCount) {
			super(new VerticalFlowLayout());

			this.owner = owner;
			this.image = image;

			init(language, rating, ratingCount);
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			if (SwingUtilities.isLeftMouseButton(event)) {
				this.owner.controller.notifyImageChoiceLeftClick(this.image);
				this.owner.setVisible(false);
				this.owner.release();
			}
			else if (SwingUtilities.isRightMouseButton(event)) {
				this.owner.controller.notifyImageChoiceRightClick(this.image);
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
				TooltipManager.addTooltip(this.imageTransition, null, Labels.TOOLTIP_IMAGE_SELECT_FULL, TooltipWay.down, (int)TimeUnit.SECONDS.toMillis(2));
				this.imageTransition.addMouseListener(this);
				this.imageTransition.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		}

		private void destroy() {
			TooltipManager.removeTooltips(this.imageTransition);
			this.imageTransition.removeMouseListener(this);
		}

		private void init(EnhancedLocale language, String rating, String ratingCount) {
			setOpaque(false);

			this.imageTransition = new ComponentTransition(makeImage(this.owner.voidImage, this.owner.providerLogos.get(this.image.getProvider())), 
			                                               new FadeTransitionEffect());
			add(this.imageTransition);
			this.imageTransition.setOpaque(false);

			this.rating = new WebLabel();
			if (StringUtils.isNotBlank(rating)) {
				this.rating.setText(rating);
			}
			this.ratingCount = new WebLabel("0");
			if (StringUtils.isNotBlank(ratingCount)) {
				this.ratingCount.setText(ratingCount);
			}
			else {
				this.ratingCount.setForeground(Colors.TRANSPARENT);
				this.ratingCount.setDrawShade(false);
			}
			add(UIUtils.makeFlowLayoutPane(FlowLayout.CENTER, 10, 0, 
			                           new WebImage(language.getLanguageFlag()),
			                           UIUtils.makeRatingPane(this.rating, this.ratingCount)));
		}

	}

	public static interface ImageChoiceController {

		public void notifyImageChoiceClosing();

		public void notifyImageChoiceLeftClick(AbstractTvSerieImage image);

		public void notifyImageChoiceRightClick(AbstractTvSerieImage image);

	}

}
