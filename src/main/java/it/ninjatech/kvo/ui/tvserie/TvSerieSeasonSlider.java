package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.AbstractSlider;
import it.ninjatech.kvo.ui.component.SliderPane;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

public class TvSerieSeasonSlider extends AbstractSlider {

	private static final long serialVersionUID = 8674772929291186163L;

	private static ImageIcon voidImage;
	
	private final TvSerieController controller;
	private final Map<TvSerieSeason, SliderPane> panes;
	private final Dimension size;
	
	protected TvSerieSeasonSlider(TvSerieController controller) {
		super();

		this.controller = controller;
		this.panes = new LinkedHashMap<>();
		this.size = Dimensions.getTvSerieSeasonSliderSize();

		init();
	}

	@Override
	protected List<SliderPane> getPanes() {
		return new ArrayList<>(this.panes.values());
	}

	protected Dimension getSeasonSize() {
		return this.size;
	}
	
	protected void setSeason(TvSerieSeason season, Image image) {
		if (image != null) {
			((SliderPane)this.panes.get(season)).setImage(SliderPane.makeImagePane(new ImageIcon(image), this.size));
		}
	}

	protected void fill(TvSeriePathEntity tvSeriePathEntity) {
		for (TvSerieSeason season : tvSeriePathEntity.getTvSerie().getSeasons()) {
			SliderPane pane = makeSeasonPane(String.format("Season %d", season.getNumber()));
			this.panes.put(season, pane);
			addPane(pane);
		}
	}
	
	protected void dispose() {
	}
	
	private void init() {
		setBackground(Colors.BACKGROUND_INFO);
	}
	
	private SliderPane makeSeasonPane(String name) {
		SliderPane result = null;

		if (voidImage == null) {
			voidImage = UIUtils.makeEmptyIcon(this.size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}

		result = new SliderPane(voidImage, this.size, makeTitlePane(name));

		return result;
	}
	
}
