package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieActor;
import it.ninjatech.kvo.model.TvSeriePathEntity;
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
import javax.swing.SwingConstants;

import com.alee.extended.image.WebDecoratedImageStyle;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.BorderPainter;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

public class TvSerieActorSlider extends AbstractSlider {

	private static final long serialVersionUID = 8890476225892576613L;

	private static ImageIcon voidImage;

	private final TvSerieController controller;
	private final Map<TvSerieActor, SliderPane> panes;
	private final Dimension size;

	protected TvSerieActorSlider(TvSerieController controller) {
		super();

		this.controller = controller;
		this.panes = new LinkedHashMap<>();
		this.size = Dimensions.getTvSerieActorSliderSize();

		init();
	}

	@Override
	protected List<SliderPane> getPanes() {
		return new ArrayList<>(this.panes.values());
	}

	protected Dimension getActorSize() {
		return this.size;
	}
	
	protected void fill(TvSeriePathEntity tvSeriePathEntity) {
		for (TvSerieActor actor : tvSeriePathEntity.getTvSerie().getActors()) {
			SliderPane pane = makeSeasonPane(actor);
			this.panes.put(actor, pane);
			addPane(pane);
		}
	}
	
	protected void setActor(TvSerieActor actor, Image image) {
		((SliderPane)this.panes.get(actor)).setImage(SliderPane.makeImagePane(new ImageIcon(image), this.size));
	}

	private void init() {
		setBackground(Colors.BACKGROUND_INFO);
	}

	private SliderPane makeSeasonPane(TvSerieActor actor) {
		SliderPane result = null;

		if (voidImage == null) {
			voidImage = UIUtils.makeEmptyIcon(this.size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}

		result = new SliderPane(voidImage, this.size, makeTitlePane(actor));

		return result;
	}
	
	private WebPanel makeTitlePane(TvSerieActor actor) {
		WebPanel result = new WebPanel(new VerticalFlowLayout(0, 0));

		result.setOpaque(false);
		result.setMargin(2, 3, 2, 3);
		
		BorderPainter<WebPanel> borderPainter = new BorderPainter<>();
		borderPainter.setRound(10);
		borderPainter.setWidth(1);
		borderPainter.setColor(WebDecoratedImageStyle.borderColor);
		result.setPainter(borderPainter);

		WebLabel name = new WebLabel(actor.getName());
		result.add(name);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setForeground(Colors.FOREGROUND_STANDARD);
		name.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		name.setDrawShade(true);
		
		WebLabel as = new WebLabel("as");
		result.add(as);
		as.setHorizontalAlignment(SwingConstants.CENTER);
		as.setForeground(Colors.FOREGROUND_STANDARD);
		as.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		as.setDrawShade(true);
		
		WebLabel role = new WebLabel(actor.getRole());
		result.add(role);
		role.setHorizontalAlignment(SwingConstants.CENTER);
		role.setForeground(Colors.FOREGROUND_STANDARD);
		role.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		role.setDrawShade(true);
		
		return result;
	}

}
