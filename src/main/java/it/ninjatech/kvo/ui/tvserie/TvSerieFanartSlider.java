package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.component.AbstractImageSlider;

import java.awt.Dimension;
import java.util.EnumMap;

import com.alee.laf.panel.WebPanel;

public class TvSerieFanartSlider extends AbstractImageSlider<TvSerieFanart> {

	private static final EnumMap<TvSerieFanart, Dimension> FANART_DIMENSION_MAP = new EnumMap<>(TvSerieFanart.class);

	static {
		FANART_DIMENSION_MAP.put(TvSerieFanart.Banner, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Banner));
		FANART_DIMENSION_MAP.put(TvSerieFanart.Character, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Character));
		FANART_DIMENSION_MAP.put(TvSerieFanart.Clearart, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Clearart));
		FANART_DIMENSION_MAP.put(TvSerieFanart.Fanart, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Fanart));
		FANART_DIMENSION_MAP.put(TvSerieFanart.Landscape, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Landscape));
		FANART_DIMENSION_MAP.put(TvSerieFanart.Logo, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Logo));
		FANART_DIMENSION_MAP.put(TvSerieFanart.Poster, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Poster));
	}

	private static final long serialVersionUID = 3976307574433882162L;

	protected TvSerieFanartSlider(String id, ImageSliderListener listener) {
		super(id, listener, true, true, true, false);
	}

	@Override
	public Dimension getImageSize(TvSerieFanart data) {
		return FANART_DIMENSION_MAP.get(data);
	}

	@Override
	protected WebPanel makeTitle(TvSerieFanart data) {
		return makeTitlePane(data.getName());
	}

}
