package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.AbstractSlider;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

import com.alee.laf.panel.WebPanel;

public class TvSerieSeasonSlider extends AbstractSlider {

	private static final long serialVersionUID = 8674772929291186163L;

	private static ImageIcon voidImage;
	
	private final List<WebPanel> panes;
	
	protected TvSerieSeasonSlider() {
		super();

		this.panes = new ArrayList<>();

		init();
	}

	@Override
	protected List<WebPanel> getPanes() {
		return Collections.unmodifiableList(this.panes);
	}

	private void init() {
		setBackground(Colors.BACKGROUND_INFO);
		
		Dimension size = Dimensions.getTvSerieSeasonSliderSize();
		
		for (int i = 0; i < 10; i++) {
			WebPanel pane = makeSeasonPane(size, String.format("Season %d", i + 1));
			this.panes.add(pane);
			addPane(pane);
		}
	}
	
	private WebPanel makeSeasonPane(Dimension size, String name) {
		WebPanel result = null;

		if (voidImage == null) {
			voidImage = UIUtils.makeEmptyIcon(size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}

		result = makeFanartPane(voidImage, size, name);

		return result;
	}
	
}
