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

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.utils.SwingUtils;

public class TvSerieFanartChoiceDialog extends WebDialog implements FanartChoicePaneListener, WindowListener {

	private static final long serialVersionUID = -5271160094897583185L;
	
	private final TvSerieFanartChoiceController controller;
	private final TvSeriePathEntity tvSeriePathEntity;
	private final TvSerieFanart fanart;
	private final Map<TvSerieImageProvider, ImageIcon> providerLogos;
	private final Map<String, FanartChoicePane> panes;
	private ImageIcon voidImage;
	
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
		TvSerieImage tvSerieImage = (TvSerieImage)pane.getData();
		if (tvSerieImage != null) {
			this.controller.notifyFanartSingleClick(tvSerieImage.getId());
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
		
		WebScrollPane gallery = new WebScrollPane(makeGalleryPane(), false, false);
		gallery.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		gallery.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		gallery.setBackground(Colors.BACKGROUND_INFO);
		gallery.setPreferredHeight(Dimensions.getFanartChoiceAvailableHeight());
		gallery.getVerticalScrollBar().setBlockIncrement(30);
		gallery.getVerticalScrollBar().setUnitIncrement(30);
		
		content.add(gallery);
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
			FanartChoicePane pane = new FanartChoicePane(theTvDbFanart, this, this.voidImage, theTvDbFanart.getLanguage(), 
			                                             this.providerLogos.get(theTvDbFanart.getProvider()), theTvDbFanart.getRating(), theTvDbFanart.getRatingCount());
			panels[i++ % paneCount].add(pane);
			this.panes.put(theTvDbFanart.getId(), pane);
		}
		
		Set<TvSerieImage> fanarttvFanarts = TvSerieUtils.getFanarttvFanarts(this.tvSeriePathEntity, this.fanart);
		for (TvSerieImage fanarttvFanart : fanarttvFanarts) {
			FanartChoicePane pane = new FanartChoicePane(fanarttvFanart, this, this.voidImage, fanarttvFanart.getLanguage(), 
			                                             this.providerLogos.get(fanarttvFanart.getProvider()), fanarttvFanart.getRating(), null);
			panels[i++ % paneCount].add(pane);
			this.panes.put(fanarttvFanart.getId(), pane);
		}
		
		SwingUtils.equalizeComponentsSize(panels);
		
		return result;
	}

}
