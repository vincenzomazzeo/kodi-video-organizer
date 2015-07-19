package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.transictioneffect.HorizontalScrollTransictionEffect;
import it.ninjatech.kvo.ui.transictioneffect.TransictionEffectExecutor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.SwingConstants;

import com.alee.extended.image.WebDecoratedImageStyle;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.BorderPainter;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.style.skin.web.WebLabelPainter;

public abstract class AbstractSlider extends WebPanel implements ActionListener {

	private static final long serialVersionUID = -2999333376641121483L;
	private static final int HGAP = 20;
	
	protected static WebPanel makeTitlePane(String title) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		result.setOpaque(false);

		WebLabel titleL = new WebLabel(title);
		result.add(titleL);
		BorderPainter<WebLabel> borderPainter = new BorderPainter<>();
		borderPainter.setRound(10);
		borderPainter.setWidth(1);
		borderPainter.setColor(WebDecoratedImageStyle.borderColor);
		titleL.setPainter(new WebLabelPainter<>(borderPainter));
		titleL.setPreferredWidth(100);
		titleL.setMargin(2);
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setForeground(Colors.FOREGROUND_STANDARD);
		titleL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		titleL.setDrawShade(true);
		
		return result;
	}
	
	private WebButton left;
	private WebButton right;
	private WebScrollPane slider;

	protected AbstractSlider() {
		super(new BorderLayout(10, 0));

		init();
	}
	
	protected abstract List<SliderPane> getPanes();
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == this.left) {
			final Rectangle visibleRect = this.slider.getViewport().getViewRect();

			List<SliderPane> panes = getPanes();
			for (int i =  panes.size() - 1; i >= 0; i--) {
				WebPanel pane = panes.get(i);
				Rectangle paneBounds = pane.getBounds();
				if (paneBounds.x < visibleRect.x) {
					visibleRect.x = -(visibleRect.x - paneBounds.x + HGAP - 1);
					HorizontalScrollTransictionEffect transictionEffect = new HorizontalScrollTransictionEffect(this.slider, visibleRect.x);
					TransictionEffectExecutor.getInstance().execute(transictionEffect);
					break;
				}
			}
		}
		else if (source == this.right) {
			final Rectangle visibleRect = this.slider.getViewport().getViewRect();
			int viewportVisibleRightMargin = visibleRect.x + visibleRect.width;

			for (SliderPane pane : getPanes()) {
				Rectangle paneBounds = pane.getBounds();
				if ((paneBounds.x + paneBounds.width) > viewportVisibleRightMargin) {
					visibleRect.x = paneBounds.x + paneBounds.width - visibleRect.x - visibleRect.width + HGAP - 1;
					HorizontalScrollTransictionEffect transictionEffect = new HorizontalScrollTransictionEffect(this.slider, visibleRect.x);
					TransictionEffectExecutor.getInstance().execute(transictionEffect);
					break;
				}
			}
		}
	}

	protected void addPane(SliderPane pane) {
		((WebPanel)this.slider.getViewport().getView()).add(pane);
	}
	
	private void init() {
		WebPanel leftPane = new WebPanel(new VerticalFlowLayout(VerticalFlowLayout.MIDDLE));
		add(leftPane, BorderLayout.WEST);
		leftPane.setOpaque(false);
		this.left = new WebButton(ImageRetriever.retrieveWallArrowLeft());
		leftPane.add(this.left);
		this.left.setUndecorated(true);
		this.left.addActionListener(this);
		this.left.setOpaque(false);
		this.left.setFocusable(false);
		this.left.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		WebPanel rightPane = new WebPanel(new VerticalFlowLayout(VerticalFlowLayout.MIDDLE));
		add(rightPane, BorderLayout.EAST);
		rightPane.setOpaque(false);
		this.right = new WebButton(ImageRetriever.retrieveWallArrowRight());
		rightPane.add(this.right);
		this.right.setUndecorated(true);
		this.right.addActionListener(this);
		this.right.setOpaque(false);
		this.right.setFocusable(false);
		this.right.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		WebPanel viewport = new WebPanel(new FlowLayout(FlowLayout.CENTER, HGAP, 10));
		viewport.setBackground(Colors.BACKGROUND_INFO);

		this.slider = new WebScrollPane(viewport, false, false);
		add(this.slider, BorderLayout.CENTER);
		this.slider.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.slider.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_NEVER);
	}
	
}
