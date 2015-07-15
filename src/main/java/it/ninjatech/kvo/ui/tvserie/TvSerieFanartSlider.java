package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.ui.transictioneffect.HorizontalScrollTransictionEffect;
import it.ninjatech.kvo.ui.transictioneffect.TransictionEffectExecutor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebDecoratedImageStyle;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.BorderPainter;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.style.skin.web.WebLabelPainter;

public class TvSerieFanartSlider extends WebPanel implements ActionListener {

	private enum FanartType {
		Banner("Banner", Dimensions.getTvSerieFanartSliderBannerSize()),
		Character("Character", Dimensions.getTvSerieFanartSliderCharacterSize()),
		Clearart("Clearart", Dimensions.getTvSerieFanartSliderClearartSize()),
		Fanart("Fanart", Dimensions.getTvSerieFanartSliderFanartSize()),
		Landscape("Landscape", Dimensions.getTvSerieFanartSliderLandscapeSize()),
		Logo("Logo", Dimensions.getTvSerieFanartSliderLogoSize()),
		Poster("Poster", Dimensions.getTvSerieFanartSliderPosterSize());

		private final String name;
		private final Dimension size;
		private ImageIcon voidImage;

		private FanartType(String name, Dimension size) {
			this.name = name;
			this.size = size;
		}
	}

	private static final long serialVersionUID = 3976307574433882162L;
	private static final int HGAP = 20;

	private final EnumMap<FanartType, WebPanel> panes;
	private JButton left;
	private JButton right;
	private WebScrollPane slider;

	protected TvSerieFanartSlider() {
		super(new BorderLayout(10, 0));

		this.panes = new EnumMap<>(FanartType.class);

		init();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if (source == this.left) {
			final Rectangle visibleRect = this.slider.getViewport().getViewRect();

			List<WebPanel> panes = new ArrayList<>(this.panes.values());
			Collections.reverse(panes);
			for (WebPanel pane : panes) {
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

			for (WebPanel pane : this.panes.values()) {
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
		setBackground(Colors.BACKGROUND_INFO);

		WebPanel leftPane = new WebPanel(new VerticalFlowLayout(VerticalFlowLayout.MIDDLE));
		add(leftPane, BorderLayout.WEST);
		leftPane.setOpaque(false);
		this.left = new JButton(ImageRetriever.retrieveWallArrowLeft());
		leftPane.add(this.left);
		this.left.addActionListener(this);
		this.left.setOpaque(false);
		this.left.setContentAreaFilled(false);
		this.left.setBorderPainted(false);
		this.left.setFocusable(false);
		this.left.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		WebPanel rightPane = new WebPanel(new VerticalFlowLayout(VerticalFlowLayout.MIDDLE));
		add(rightPane, BorderLayout.EAST);
		rightPane.setOpaque(false);
		this.right = new JButton(ImageRetriever.retrieveWallArrowRight());
		rightPane.add(this.right);
		this.right.addActionListener(this);
		this.right.setOpaque(false);
		this.right.setContentAreaFilled(false);
		this.right.setBorderPainted(false);
		this.right.setFocusable(false);
		this.right.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		WebPanel viewport = new WebPanel(new FlowLayout(FlowLayout.LEFT, HGAP, 10));
		viewport.setBackground(Colors.BACKGROUND_INFO);

		this.slider = new WebScrollPane(viewport, false, false);
		add(this.slider, BorderLayout.CENTER);
		this.slider.setHorizontalScrollBarPolicy(WebScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.slider.setVerticalScrollBarPolicy(WebScrollPane.VERTICAL_SCROLLBAR_NEVER);

		this.panes.put(FanartType.Banner, makeFanartPane(FanartType.Banner));
		viewport.add(this.panes.get(FanartType.Banner));

		this.panes.put(FanartType.Character, makeFanartPane(FanartType.Character));
		viewport.add(this.panes.get(FanartType.Character));

		this.panes.put(FanartType.Clearart, makeFanartPane(FanartType.Clearart));
		viewport.add(this.panes.get(FanartType.Clearart));

		this.panes.put(FanartType.Fanart, makeFanartPane(FanartType.Fanart));
		viewport.add(this.panes.get(FanartType.Fanart));

		this.panes.put(FanartType.Landscape, makeFanartPane(FanartType.Landscape));
		viewport.add(this.panes.get(FanartType.Landscape));

		this.panes.put(FanartType.Logo, makeFanartPane(FanartType.Logo));
		viewport.add(this.panes.get(FanartType.Logo));

		this.panes.put(FanartType.Poster, makeFanartPane(FanartType.Poster));
		viewport.add(this.panes.get(FanartType.Poster));
	}

	private WebPanel makeFanartPane(FanartType fanartType) {
		WebPanel result = new WebPanel(new VerticalFlowLayout(0, 0));
		result.setOpaque(false);

		if (fanartType.voidImage == null) {
			fanartType.voidImage = UIUtils.makeEmptyIcon(fanartType.size, Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}

		WebDecoratedImage image = new WebDecoratedImage(fanartType.voidImage);
		result.add(image);
		image.setMinimumSize(fanartType.size);
		image.setShadeWidth(5);
		image.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		image.setDrawGlassLayer(false);

		result.add(UIUtils.makeVerticalFillerPane(10, false));

		WebPanel namePane = new WebPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		result.add(namePane);
		namePane.setOpaque(false);

		WebLabel name = new WebLabel(fanartType.name);
		namePane.add(name);
		BorderPainter<WebLabel> borderPainter = new BorderPainter<>();
		borderPainter.setRound(10);
		borderPainter.setWidth(1);
		borderPainter.setColor(WebDecoratedImageStyle.borderColor);
		name.setPainter(new WebLabelPainter<>(borderPainter));
		name.setPreferredWidth(100);
		name.setMargin(2);
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setForeground(Colors.FOREGROUND_STANDARD);
		name.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		name.setDrawShade(true);

		return result;
	}

}
