package it.ninjatech.kvo.ui.tvserie;

import java.awt.Dimension;

import it.ninjatech.kvo.model.TvSerieActor;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.component.AbstractImageSlider;

import javax.swing.SwingConstants;

import com.alee.extended.image.WebDecoratedImageStyle;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.BorderPainter;
import com.alee.laf.panel.WebPanel;

public class TvSerieActorImageSlider extends AbstractImageSlider<TvSerieActor> {

	private static final long serialVersionUID = 8890476225892576613L;

	protected TvSerieActorImageSlider(String id, ImageSliderListener listener) {
		super(id, listener, false, true, true, true);
	}
	
	@Override
	public Dimension getImageSize(TvSerieActor data) {
		return Dimensions.getActorSliderSize();
	}

	@Override
	protected WebPanel makeTitle(TvSerieActor actor) {
		WebPanel result = UIUtils.makeStandardPane(new VerticalFlowLayout(0, 0));
		result.setMargin(2, 3, 2, 3);
		
		BorderPainter<WebPanel> borderPainter = new BorderPainter<>();
		borderPainter.setRound(10);
		borderPainter.setWidth(1);
		borderPainter.setColor(WebDecoratedImageStyle.borderColor);
		result.setPainter(borderPainter);

		result.add(UIUtils.makeStandardLabel(actor.getName(), null, null, SwingConstants.CENTER));
		result.add(UIUtils.makeStandardLabel("as", null, null, SwingConstants.CENTER));
		result.add(UIUtils.makeStandardLabel(actor.getRole(), null, null, SwingConstants.CENTER));
		
		return result;
	}
	
}
