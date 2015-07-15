package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.AbstractSlider;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.swing.ImageIcon;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.laf.panel.WebPanel;

public class TvSerieFanartSlider extends AbstractSlider implements HierarchyListener {

	private enum FanartType {
		Banner("Banner", Dimensions.getTvSerieFanartSliderBannerSize()),
		Character("Character", Dimensions.getTvSerieFanartSliderCharacterSize()),
		Clearart("Clearart", Dimensions.getTvSerieFanartSliderClearartSize()),
		Fanart("Fanart", Dimensions.getTvSerieFanartSliderFanartSize()),
		Landscape("Landscape", Dimensions.getTvSerieFanartSliderLandscapeSize()),
		Logo("Logo", Dimensions.getTvSerieFanartSliderLogoSize()),
		Poster("Poster", Dimensions.getTvSerieFanartSliderPosterSize());

		private final String name;
		private final Dimension size;
		private ImageIcon voidImage;

		private FanartType(String name, Dimension size) {
			this.name = name;
			this.size = size;
		}
	}

	private static final long serialVersionUID = 3976307574433882162L;

	private final TvSerieController controller;
	private final EnumMap<FanartType, WebPanel> panes;

	protected TvSerieFanartSlider(TvSerieController controller) {
		super();

		this.controller = controller;
		this.panes = new EnumMap<>(FanartType.class);

		addHierarchyListener(this);
		
		init();
	}
	
	@Override
	public void hierarchyChanged(HierarchyEvent event) {
		if ((event.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED && getParent().isShowing()) {
			this.controller.loadLocalFanart(FanartType.Banner.size, 
			                                FanartType.Character.size, 
			                                FanartType.Clearart.size, 
			                                FanartType.Fanart.size, 
			                                FanartType.Landscape.size, 
			                                FanartType.Logo.size, 
			                                FanartType.Poster.size);
		}
	}

	@Override
	protected List<WebPanel> getPanes() {
		return new ArrayList<>(this.panes.values());
	}
	
	protected void setFanart(Image banner, Image character, Image clearart, Image fanart, Image landscape, Image logo, Image poster) {
		if (banner != null) {
			((WebDecoratedImage)this.panes.get(FanartType.Banner).getComponent(0)).setIcon(new ImageIcon(banner), true);
		}
		if (character != null) {
			((WebDecoratedImage)this.panes.get(FanartType.Character).getComponent(0)).setIcon(new ImageIcon(character), true);
		}
		if (clearart != null) {
			((WebDecoratedImage)this.panes.get(FanartType.Clearart).getComponent(0)).setIcon(new ImageIcon(clearart), true);
		}
		if (fanart != null) {
			((WebDecoratedImage)this.panes.get(FanartType.Fanart).getComponent(0)).setIcon(new ImageIcon(fanart), true);
		}
		if (landscape != null) {
			((WebDecoratedImage)this.panes.get(FanartType.Landscape).getComponent(0)).setIcon(new ImageIcon(landscape), true);
		}
		if (logo != null) {
			((WebDecoratedImage)this.panes.get(FanartType.Logo).getComponent(0)).setIcon(new ImageIcon(logo), true);
		}
		if (poster != null) {
			((WebDecoratedImage)this.panes.get(FanartType.Poster).getComponent(0)).setIcon(new ImageIcon(poster), true);
		}
	}

	private void init() {
		setBackground(Colors.BACKGROUND_INFO);
		
		this.panes.put(FanartType.Banner, makeFanartPane(FanartType.Banner));
		addPane(this.panes.get(FanartType.Banner));

		this.panes.put(FanartType.Character, makeFanartPane(FanartType.Character));
		addPane(this.panes.get(FanartType.Character));

		this.panes.put(FanartType.Clearart, makeFanartPane(FanartType.Clearart));
		addPane(this.panes.get(FanartType.Clearart));

		this.panes.put(FanartType.Fanart, makeFanartPane(FanartType.Fanart));
		addPane(this.panes.get(FanartType.Fanart));

		this.panes.put(FanartType.Landscape, makeFanartPane(FanartType.Landscape));
		addPane(this.panes.get(FanartType.Landscape));

		this.panes.put(FanartType.Logo, makeFanartPane(FanartType.Logo));
		addPane(this.panes.get(FanartType.Logo));

		this.panes.put(FanartType.Poster, makeFanartPane(FanartType.Poster));
		addPane(this.panes.get(FanartType.Poster));
	}

	private WebPanel makeFanartPane(FanartType fanartType) {
		WebPanel result = null;

		if (fanartType.voidImage == null) {
			fanartType.voidImage = UIUtils.makeEmptyIcon(fanartType.size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}

		result = makeFanartPane(fanartType.voidImage, fanartType.size, fanartType.name);

		return result;
	}

}
