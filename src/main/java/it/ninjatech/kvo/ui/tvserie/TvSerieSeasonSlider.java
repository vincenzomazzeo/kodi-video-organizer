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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
// TODO aggiungere tooltip
//TODO UIUtils
//TODO sistemare con abstractimageslider
public class TvSerieSeasonSlider extends AbstractSlider implements MouseListener {

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
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event)) {
			this.controller.notifySeasonLeftClick((TvSerieSeason)((SliderPane)event.getSource()).getData());
		}
		else if (SwingUtilities.isRightMouseButton(event)) {
			this.controller.notifySeasonRightClick((TvSerieSeason)((SliderPane)event.getSource()).getData());
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
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
			((SliderPane)this.panes.get(season)).setImage(UIUtils.makeImagePane(new ImageIcon(image), this.size));
		}
	}

	protected void fill(TvSeriePathEntity tvSeriePathEntity) {
		for (TvSerieSeason season : tvSeriePathEntity.getTvSerie().getSeasons()) {
			SliderPane pane = makeSeasonPane(season);
			this.panes.put(season, pane);
			addPane(pane);
		}
	}
	
	protected void dispose() {
	}
	
	private void init() {
		setBackground(Colors.BACKGROUND_INFO);
	}
	
	private SliderPane makeSeasonPane(TvSerieSeason season) {
		SliderPane result = null;

		if (voidImage == null) {
			voidImage = UIUtils.makeEmptyIcon(this.size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}

		result = new SliderPane(voidImage, this.size, makeTitlePane(String.format("Season %d", season.getNumber())));
		result.setData(season);
		result.addMouseListener(this);

		return result;
	}
	
}
