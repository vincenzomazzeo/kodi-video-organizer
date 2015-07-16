package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.AbstractSlider;
import it.ninjatech.kvo.ui.component.SliderPane;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.swing.ImageIcon;

public class TvSerieFanartSlider extends AbstractSlider {

	protected enum FanartType {
		Banner(TvSerieFanart.Banner, Dimensions.getTvSerieFanartSliderBannerSize()),
		Character(TvSerieFanart.Character, Dimensions.getTvSerieFanartSliderCharacterSize()),
		Clearart(TvSerieFanart.Clearart, Dimensions.getTvSerieFanartSliderClearartSize()),
		Fanart(TvSerieFanart.Fanart, Dimensions.getTvSerieFanartSliderFanartSize()),
		Landscape(TvSerieFanart.Landscape, Dimensions.getTvSerieFanartSliderLandscapeSize()),
		Logo(TvSerieFanart.Logo, Dimensions.getTvSerieFanartSliderLogoSize()),
		Poster(TvSerieFanart.Poster, Dimensions.getTvSerieFanartSliderPosterSize());

		private final TvSerieFanart fanart;
		private final Dimension size;
		private ImageIcon voidImage;

		private FanartType(TvSerieFanart fanart, Dimension size) {
			this.fanart = fanart;
			this.size = size;
		}

		protected TvSerieFanart getFanart() {
			return this.fanart;
		}
		
		protected Dimension getSize() {
			return this.size;
		}
		
	}

	private static final long serialVersionUID = 3976307574433882162L;

	private final TvSerieController controller;
	private final EnumMap<FanartType, SliderPane> panes;

	protected TvSerieFanartSlider(TvSerieController controller) {
		super();

		this.controller = controller;
		this.panes = new EnumMap<>(FanartType.class);

		init();
	}
	
	@Override
	protected List<SliderPane> getPanes() {
		return new ArrayList<>(this.panes.values());
	}
	
	protected void setFanart(TvSerieFanart fanart, Image image) {
		if (image != null) {
			for (FanartType fanartType : FanartType.values()) {
				if (fanartType.fanart == fanart) {
					((SliderPane)this.panes.get(fanartType)).setImage(SliderPane.makeImagePane(new ImageIcon(image), fanartType.size));
					break;
				}
			}
		}
	}

	private void init() {
		setBackground(Colors.BACKGROUND_INFO);
		
		for (FanartType fanart : FanartType.values()) {
			this.panes.put(fanart, makeFanartPane(fanart));
			addPane(this.panes.get(fanart));
		}
	}

	private SliderPane makeFanartPane(FanartType fanartType) {
		SliderPane result = null;

		if (fanartType.voidImage == null) {
			fanartType.voidImage = UIUtils.makeEmptyIcon(fanartType.size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}

		result = new SliderPane(fanartType.voidImage, fanartType.size, makeTitlePane(fanartType.fanart.getName()));

		return result;
	}

}
