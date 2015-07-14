package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.transictioneffect.HorizontalScrollTransictionEffect;
import it.ninjatech.kvo.ui.transictioneffect.TransictionEffectExecutor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

public class TvSerieFanartSlider extends WebPanel implements ActionListener {

	private static final long serialVersionUID = 3976307574433882162L;
	private static final int HGAP = 20;

	private final WebPanel[] panes;
	private WebButton left;
	private WebButton right;
	private WebScrollPane slider;
	
	protected TvSerieFanartSlider() {
		super(new BorderLayout());

		this.panes = new WebPanel[7];
		
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == this.left) {
			final Rectangle visibleRect = this.slider.getViewport().getViewRect();
			
			for (int i = this.panes.length - 1; i >= 0; i--) {
				WebPanel pane = this.panes[i];
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
			
			for (WebPanel pane : this.panes) {
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

	private void init() {
		setBackground(Colors.BACKGROUND_MISSING_IMAGE);
		
		this.left = new WebButton("<");
		add(this.left, BorderLayout.WEST);
		this.left.addActionListener(this);
		this.right = new WebButton(">");
		add(this.right, BorderLayout.EAST);
		this.right.addActionListener(this);
		
		WebPanel viewport = new WebPanel(new FlowLayout(FlowLayout.LEFT, HGAP, 10));
		viewport.setBackground(Colors.BACKGROUND_MISSING_IMAGE);
		
		this.panes[0] = new WebPanel(new BorderLayout());
		viewport.add(this.panes[0]);
		this.panes[0].setPreferredSize(Dimensions.getTvSerieFanartSliderBannerSize());
		this.panes[0].add(new WebLabel("banner"));
		
		this.panes[1] = new WebPanel(new BorderLayout());
		viewport.add(this.panes[1]);
		this.panes[1].setPreferredSize(Dimensions.getTvSerieFanartSliderCharacterSize());
		this.panes[1].add(new WebLabel("character"));
		
		this.panes[2] = new WebPanel(new BorderLayout());
		viewport.add(this.panes[2]);
		this.panes[2].setPreferredSize(Dimensions.getTvSerieFanartSliderClearartSize());
		this.panes[2].add(new WebLabel("clearart"));
		
		this.panes[3] = new WebPanel(new BorderLayout());
		viewport.add(this.panes[3]);
		this.panes[3].setPreferredSize(Dimensions.getTvSerieFanartSliderFanartSize());
		this.panes[3].add(new WebLabel("fanart"));
		
		this.panes[4] = new WebPanel(new BorderLayout());
		viewport.add(this.panes[4]);
		this.panes[4].setPreferredSize(Dimensions.getTvSerieFanartSliderLandscapeSize());
		this.panes[4].add(new WebLabel("landscape"));
		
		this.panes[5] = new WebPanel(new BorderLayout());
		viewport.add(this.panes[5]);
		this.panes[5].setPreferredSize(Dimensions.getTvSerieFanartSliderLogoSize());
		this.panes[5].add(new WebLabel("logo"));
		
		this.panes[6] = new WebPanel(new BorderLayout());
		viewport.add(this.panes[6]);
		this.panes[6].setPreferredSize(Dimensions.getTvSerieFanartSliderPosterSize());
		this.panes[6].add(new WebLabel("poster"));
		
		this.slider = new WebScrollPane(viewport);
		add(this.slider, BorderLayout.CENTER);
		this.slider.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.slider.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_NEVER);
	}
	
}
