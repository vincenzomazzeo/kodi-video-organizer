package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Labels;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
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
	private final Map<T, SliderPane> panes;
	private final Dimension size;
	private final boolean leftClickEnabled;
	private final boolean rightClickEnabled;
	private ImageIcon voidImage;
	
	protected AbstractImageSlider(String id, ImageSliderListener listener, Dimension size, boolean leftClickEnabled, boolean rightClickEnabled) {
		super();

		this.id = id;
		this.listener = listener;
		this.panes = new LinkedHashMap<>();
		this.size = size;
		this.leftClickEnabled = leftClickEnabled;
		this.rightClickEnabled = rightClickEnabled;

		init();
	}
	
	protected abstract WebPanel makeTitle(T data);
	
	@Override
	public void mouseClicked(MouseEvent event) {
		if (SwingUtilities.isLeftMouseButton(event) && this.leftClickEnabled) {
			this.listener.notifyLeftClick(this.id, ((SliderPane)event.getSource()).getData());
		}
		else if (SwingUtilities.isRightMouseButton(event) && this.rightClickEnabled) {
			this.listener.notifyRightClick(this.id, ((SliderPane)event.getSource()).getData());
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
	
	public Dimension getImageSize() {
		return this.size;
	}

	public void fill(Collection<T> datas) {
		destroy();
		removePanes();
		for (T data : datas) {
			SliderPane pane = makePane(data);
			this.panes.put(data, pane);
			addPane(pane);
		}
	}
	
	public void setImage(T data, Image image) {
		if (image != null) {
			SliderPane pane = (SliderPane)this.panes.get(data);
			TooltipManager.removeTooltips(pane);
			pane.setImage(UIUtils.makeImagePane(image, this.size));
			if (this.leftClickEnabled || this.rightClickEnabled) {
				String tooltip = null;
				if (this.leftClickEnabled && this.rightClickEnabled) {
					tooltip = Labels.TOOLTIP_IMAGE_CHANGE_FULL;
				}
				else if (this.leftClickEnabled) {
					tooltip = Labels.TOOLTIP_IMAGE_CHANGE;
				}
				else if (this.rightClickEnabled) {
					tooltip = Labels.TOOLTIP_IMAGE_FULL;
				}
				TooltipManager.addTooltip(pane, null, tooltip, TooltipWay.up, (int)TimeUnit.SECONDS.toMillis(2));
			}
		}
	}

	public void destroy() {
		for (SliderPane pane : this.panes.values()) {
			TooltipManager.removeTooltips(pane);
		}
		this.panes.clear();
	}
	
	private void init() {
		setBackground(Colors.BACKGROUND_INFO);
	}
	
	private SliderPane makePane(T data) {
		SliderPane result = null;

		if (this.voidImage == null) {
			this.voidImage = UIUtils.makeEmptyIcon(this.size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}

		result = new SliderPane(this.voidImage, this.size, makeTitle(data));
		result.setData(data);
		result.addMouseListener(this);
		
		return result;
	}
	
	public static interface ImageSliderListener {
		
		public void notifyLeftClick(String id, Object data);
		
		public void notifyRightClick(String id, Object data);
		
	}
	
}
