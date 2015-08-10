package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.Labels;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.alee.laf.panel.WebPanel;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

public abstract class AbstractImageSlider<T> extends AbstractSlider implements MouseListener {

	private static final long serialVersionUID = 2136380817097751631L;

	private final String id;
	private final ImageSliderListener listener;
	protected final Map<T, SliderPane> panes;
	protected final Map<T, ImageIcon> voidImages;
	protected ImageIcon voidImage;
	private final boolean leftClickEnabled;
	private final boolean rightClickEnabled;
	private final boolean tooltipOnVoid;
	private final boolean fixedImageSize;
	
	protected AbstractImageSlider(String id, ImageSliderListener listener, boolean leftClickEnabled, boolean rightClickEnabled, boolean tooltipOnVoid, boolean fixedImageSize) {
		super();

		this.id = id;
		this.listener = listener;
		this.panes = new LinkedHashMap<>();
		this.voidImages = new HashMap<>();
		this.leftClickEnabled = leftClickEnabled;
		this.rightClickEnabled = rightClickEnabled;
		this.tooltipOnVoid = tooltipOnVoid;
		this.fixedImageSize = fixedImageSize;

		init();
	}
	
	public abstract Dimension getImageSize(T data);
	
	protected abstract WebPanel makeTitle(T data);
	
	@Override
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event) && this.leftClickEnabled) {
			this.listener.notifyImageSliderLeftClick(this.id, ((SliderPane)event.getSource()).getData());
		}
		else if (SwingUtilities.isRightMouseButton(event) && this.rightClickEnabled) {
			this.listener.notifyImageSliderRightClick(this.id, ((SliderPane)event.getSource()).getData());
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
	
	public void fill(Collection<T> datas) {
		destroy();
		removePanes();
		for (T data : datas) {
			SliderPane pane = makePane(data);
			if (this.tooltipOnVoid) {
				String tooltip = getTooltip(data);
				if (tooltip != null) {
					TooltipManager.addTooltip(pane, null, tooltip, TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
				}
			}
			this.panes.put(data, pane);
			addPane(pane);
		}
	}
	
	public void setImage(T data, Image image) {
		if (image != null) {
			SliderPane pane = (SliderPane)this.panes.get(data);
			TooltipManager.removeTooltips(pane);
			pane.setImage(UIUtils.makeImagePane(image, getImageSize(data)));
			String tooltip = getTooltip(data);
			if (tooltip != null) {
				TooltipManager.addTooltip(pane, null, tooltip, TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
			}
		}
	}

	public void destroy() {
		for (SliderPane pane : this.panes.values()) {
			TooltipManager.removeTooltips(pane);
		}
		this.panes.clear();
		this.voidImages.clear();
	}
	
	protected String getTooltip(T data) {
		String result = null;
		
		if (this.leftClickEnabled || this.rightClickEnabled) {
			if (this.leftClickEnabled && this.rightClickEnabled) {
				result = Labels.TOOLTIP_IMAGE_CHANGE_FULL;
			}
			else if (this.leftClickEnabled) {
				result = Labels.TOOLTIP_IMAGE_CHANGE;
			}
			else if (this.rightClickEnabled) {
				result = Labels.TOOLTIP_IMAGE_FULL;
			}
		}
		
		return result;
	}
	
	protected SliderPane makePane(T data, ImageIcon voidImage, Dimension size, WebPanel titlePane) {
		return new SliderPane(data, voidImage, size, titlePane);
	}
	
	private void init() {
		setBackground(Colors.BACKGROUND_INFO);
	}
	
	private SliderPane makePane(T data) {
		SliderPane result = null;

		ImageIcon voidImage = this.fixedImageSize ? this.voidImage : this.voidImages.get(data);
		if (voidImage == null) {
			voidImage = UIUtils.makeEmptyIcon(getImageSize(data), Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
			if (fixedImageSize) {
				this.voidImage = voidImage;
			}
			else {
				this.voidImages.put(data, voidImage);
			}
		}

		result = makePane(data, voidImage, getImageSize(data), makeTitle(data));
		result.addMouseListener(this);
		
		return result;
	}
	
	public static interface ImageSliderListener {
		
		public void notifyImageSliderLeftClick(String id, Object data);
		
		public void notifyImageSliderRightClick(String id, Object data);
		
	}
	
}
