package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSerieImage;
import it.ninjatech.kvo.model.TvSerieImageProvider;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.TvSerieUtils;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.FanartChoicePane;
import it.ninjatech.kvo.ui.component.FanartChoicePane.FanartChoicePaneListener;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.utils.SwingUtils;

public class TvSerieFanartChoiceDialog extends WebDialog implements FanartChoicePaneListener, WindowListener {

	private static final long serialVersionUID = -5271160094897583185L;
	
	private final TvSerieFanartChoiceController controller;
	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieFanart fanart;
	private final Map<TvSerieImageProvider, ImageIcon> providerLogos;
	private final Map<String, FanartChoicePane> panes;
	private ImageIcon voidImage;
	private ComponentTransition currentTransition;
	private FanartChoicePane chosenPane;
	
	protected TvSerieFanartChoiceDialog(TvSerieFanartChoiceController controller, TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart) {
		super(UI.get(), String.format("%s - %s", TvSerieUtils.getTitle(tvSeriePathEntity), fanart.getName()), true);
		
		this.controller = controller;
		this.tvSeriePathEntity = tvSeriePathEntity;
		this.fanart = fanart;
		this.providerLogos = new EnumMap<>(TvSerieImageProvider.class);
		this.panes = new HashMap<>();
		
		this.providerLogos.put(TvSerieImageProvider.Fanarttv, ImageRetriever.retrieveFanartChoiceFanarttvLogo());
		this.providerLogos.put(TvSerieImageProvider.TheTvDb, ImageRetriever.retrieveFanartChoiceTheTvDbLogo());
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		init();
		
		pack();
		setLocationRelativeTo(UI.get());
	}
	
	@Override
	public void fanartChoicePaneSingleClick(FanartChoicePane pane) {
		if (pane != this.chosenPane) {
			TvSerieImage tvSerieImage = (TvSerieImage)pane.getData();
			Image image = pane.getImage();
			this.chosenPane.setPane(tvSerieImage, image, 
			                        tvSerieImage.getLanguage(), this.providerLogos.get(tvSerieImage.getProvider()), 
			                        tvSerieImage.getRating(), tvSerieImage.getRatingCount());
		}
	}

	@Override
	public void fanartChoicePaneDoubleClick(FanartChoicePane pane) {
		TvSerieImage tvSerieImage = (TvSerieImage)pane.getData();
		if (tvSerieImage != null) {
			this.controller.notifyFanartDoubleClick(tvSerieImage.getId());
		}
	}
	
	@Override
	public void windowOpened(WindowEvent event) {
	}

	@Override
	public void windowClosing(WindowEvent event) {
		this.controller.notifyClosing();
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
	
	protected void setCurrentFanart(Image currentFanart) {
		if (currentFanart != null) {
			this.currentTransition.performTransition(makeImage(new ImageIcon(currentFanart)));
		}
	}
	
	protected void setFanart(String id, Image fanart) {
		this.panes.get(id).setImage(fanart);
	}

	protected Dimension getImageSize() {
		return new Dimension(this.voidImage.getIconWidth(), this.voidImage.getIconHeight());
	}
	
	private void init() {
		WebPanel content = new WebPanel(new VerticalFlowLayout());
		setContentPane(content);

		content.setBackground(Colors.BACKGROUND_INFO);
		
		this.voidImage = UIUtils.makeEmptyIcon(Dimensions.getTvSerieFanartChooserSize(this.fanart), Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		
		WebPanel headerPane = makeHeaderPane();
		
		WebScrollPane gallery = new WebScrollPane(makeGalleryPane(), false, false);
		gallery.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gallery.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		gallery.setBackground(Colors.BACKGROUND_INFO);
		gallery.setPreferredHeight(Dimensions.getFanartChoiceAvailableHeight() - headerPane.getPreferredSize().height);
		gallery.getVerticalScrollBar().setBlockIncrement(30);
		gallery.getVerticalScrollBar().setUnitIncrement(30);
		
		content.add(headerPane);
		content.add(WebSeparator.createHorizontal());
		content.add(gallery);
	}
	
	private WebPanel makeHeaderPane() {
		WebPanel result = new WebPanel(new FlowLayout());
		
		result.setOpaque(false);
		result.setMargin(10, 10, 10, 30);
		
		WebPanel current = makeCurrentPane();
		WebPanel chosen = makeChosenPanel();
		
		SwingUtils.equalizeComponentsHeights(current, chosen);
		
		result.add(current);
		result.add(chosen);
		
		return result;
	}
	
	private WebPanel makeCurrentPane() {
		WebPanel result = new WebPanel(new VerticalFlowLayout());

		result.setOpaque(false);
		
		WebPanel container = new WebPanel(new VerticalFlowLayout());
		result.add(container, BorderLayout.NORTH);
		container.setOpaque(false);
		
		WebLabel title = new WebLabel("Current");
		container.add(title);
		title.setFontSize(16);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(Colors.FOREGROUND_STANDARD);
		title.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		title.setDrawShade(true);
		
		this.currentTransition = new ComponentTransition(makeImage(UIUtils.makeEmptyIcon(Dimensions.getTvSerieFanartChooserSize(this.fanart), Colors.BACKGROUND_MISSING_IMAGE_ALPHA)), new FadeTransitionEffect());
		container.add(this.currentTransition);
		this.currentTransition.setOpaque(false);
		
		return result;
	}
	
	private WebPanel makeChosenPanel() {
		WebPanel result = new WebPanel(new VerticalFlowLayout());

		result.setOpaque(false);
		
		WebLabel title = new WebLabel("Chosen");
		result.add(title);
		title.setFontSize(16);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(Colors.FOREGROUND_STANDARD);
		title.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		title.setDrawShade(true);
		
		this.chosenPane = new FanartChoicePane(null, this, true, this.voidImage, EnhancedLocaleMap.getEmptyLocale(), 
		                                       UIUtils.makeEmptyIcon(new Dimension(this.providerLogos.get(TvSerieImageProvider.TheTvDb).getIconWidth(), 1), Colors.BACKGROUND_INFO), null, null);
		result.add(this.chosenPane);
		
		return result;
	}
	
	private WebPanel makeGalleryPane() {
		WebPanel result = new WebPanel(new FlowLayout());
		
		result.setBackground(Colors.BACKGROUND_INFO);
		
		int availableWidth = Dimensions.getFanartChoiceAvailableWidth();
		int paneCount = availableWidth / this.voidImage.getIconWidth();
		WebPanel[] panels = new WebPanel[paneCount];
		
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new WebPanel(new VerticalFlowLayout(0, 10));
			result.add(panels[i]);
			panels[i].setOpaque(false);
		}
		
		int i = 0;
		
		Set<TvSerieImage> theTvDbFanarts = TvSerieUtils.getTheTvDbFanarts(this.tvSeriePathEntity, this.fanart);
		for (TvSerieImage theTvDbFanart : theTvDbFanarts) {
			FanartChoicePane pane = new FanartChoicePane(theTvDbFanart, this, false, this.voidImage, theTvDbFanart.getLanguage(), 
			                                             this.providerLogos.get(theTvDbFanart.getProvider()), theTvDbFanart.getRating(), theTvDbFanart.getRatingCount());
			panels[i++ % paneCount].add(pane);
			this.panes.put(theTvDbFanart.getId(), pane);
		}
		
		Set<TvSerieImage> fanarttvFanarts = TvSerieUtils.getFanarttvFanarts(this.tvSeriePathEntity, this.fanart);
		for (TvSerieImage fanarttvFanart : fanarttvFanarts) {
			FanartChoicePane pane = new FanartChoicePane(fanarttvFanart, this, false, this.voidImage, fanarttvFanart.getLanguage(), 
			                                             this.providerLogos.get(fanarttvFanart.getProvider()), fanarttvFanart.getRating(), null);
			panels[i++ % paneCount].add(pane);
			this.panes.put(fanarttvFanart.getId(), pane);
		}
		
		SwingUtils.equalizeComponentsSize(panels);
		
		return result;
	}
	
	private WebDecoratedImage makeImage(ImageIcon image) {
		WebDecoratedImage result = null;
		
		Dimension imageSize = Dimensions.getTvSerieFanartChooserSize(this.fanart);
		
		result = new WebDecoratedImage(UIUtils.makeEmptyIcon(imageSize, Colors.BACKGROUND_MISSING_IMAGE_ALPHA));
		result.setMinimumSize(imageSize);
		result.setShadeWidth(5);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setDrawGlassLayer(false);
		
		return result;
	}

}
