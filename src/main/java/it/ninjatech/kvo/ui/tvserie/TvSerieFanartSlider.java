package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerieFanart;
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
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

public class TvSerieFanartSlider extends AbstractSlider implements MouseListener {

	protected enum FanartType {
		Banner(TvSerieFanart.Banner, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Banner)),
		Character(TvSerieFanart.Character, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Character)),
		Clearart(TvSerieFanart.Clearart, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Clearart)),
		Fanart(TvSerieFanart.Fanart, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Fanart)),
		Landscape(TvSerieFanart.Landscape, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Landscape)),
		Logo(TvSerieFanart.Logo, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Logo)),
		Poster(TvSerieFanart.Poster, Dimensions.getTvSerieFanartSliderSize(TvSerieFanart.Poster));

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
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event)) {
			this.controller.notifyFanartLeftClick((FanartType)((SliderPane)event.getSource()).getData());
		}
		else if (SwingUtilities.isRightMouseButton(event)) {
			this.controller.notifyFanartRightClick((FanartType)((SliderPane)event.getSource()).getData());
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
	
	protected void setFanart(TvSerieFanart fanart, Image image) {
		if (image != null) {
			for (FanartType fanartType : FanartType.values()) {
				if (fanartType.fanart == fanart) {
					SliderPane pane = (SliderPane)this.panes.get(fanartType);
					pane.setImage(SliderPane.makeImagePane(new ImageIcon(image), fanartType.size));
					TooltipManager.removeTooltips(pane);
					TooltipManager.addTooltip(pane, null, "<html><div align='center'>Left click to search for more<br />Right click for full size image</div></html>", TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
					break;
				}
			}
		}
	}
	
	// TODO Check memory leak solved
	protected void dispose() {
		for (SliderPane pane : this.panes.values()) {
			TooltipManager.removeTooltips(pane);
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
		result.setData(fanartType);
		result.addMouseListener(this);
		TooltipManager.addTooltip(result, null, "Left click to search for more", TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));

		return result;
	}

}
