package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.AbstractSlider;
import it.ninjatech.kvo.ui.component.SliderPane;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class TvSerieSeasonSlider extends AbstractSlider {

	private static final long serialVersionUID = 8674772929291186163L;

	private static ImageIcon voidImage;
	
	private final TvSerieController controller;
	private final List<SliderPane> panes;
	
	protected TvSerieSeasonSlider(TvSerieController controller) {
		super();

		this.controller = controller;
		this.panes = new ArrayList<>();

		init();
	}

	@Override
	protected List<SliderPane> getPanes() {
		return new ArrayList<>(this.panes);
	}

	protected void fill(TvSeriePathEntity tvSeriePathEntity) {
		Dimension size = Dimensions.getTvSerieSeasonSliderSize();
		
		for (Integer seasonNumber : tvSeriePathEntity.getTvSerie().getSeasonNumbers()) {
			SliderPane pane = makeSeasonPane(size, String.format("Season %d", seasonNumber));
			this.panes.add(pane);
			addPane(pane);
		}
	}
	
	private void init() {
		setBackground(Colors.BACKGROUND_INFO);
	}
	
	private SliderPane makeSeasonPane(Dimension size, String name) {
		SliderPane result = null;

		if (voidImage == null) {
			voidImage = UIUtils.makeEmptyIcon(size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}

		result = new SliderPane(voidImage, size, makeTitlePane(name));

		return result;
	}
	
}
