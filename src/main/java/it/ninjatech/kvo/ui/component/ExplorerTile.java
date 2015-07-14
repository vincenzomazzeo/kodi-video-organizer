package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.image.WebImage;
import com.alee.extended.panel.TwoSidesPanel;
import com.alee.extended.panel.WebOverlay;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.language.data.TooltipWay;
import com.alee.managers.tooltip.TooltipManager;

public class ExplorerTile extends WebPanel {

	private static final long serialVersionUID = 4129515407391265281L;

	private Image fanart;
	private Image poster;

	public ExplorerTile(Image fanart, Image poster, String title, String year, String rate, String genre) {
		super();

		this.fanart = fanart;
		this.poster = poster;

		init(title, year, rate, genre);
	}

	public void destroy() {
		removeAll();

		this.fanart = null;
		this.poster = null;
	}

	private void init(String title, String year, String rate, String genre) {
		Dimension size = Dimensions.getExplorerTileSize();
		Dimension posterSize = Dimensions.getExplorerTilePosterSize();
		int posterMargin = Dimensions.getExplorerTilePosterMargin();
		int posterTopMargin = size.height - posterSize.height - posterMargin;
		int posterRightMargin = size.width - posterSize.width - posterMargin;

		setPreferredSize(size);
		setLayout(new BorderLayout());

		WebPanel background = new WebPanel(new BorderLayout());
		if (this.fanart == null) {
			background.setBackground(Colors.BACKGROUND_MISSING_IMAGE);
		}
		else {
			background.add(new WebImage(this.fanart), BorderLayout.CENTER);
		}

		WebDecoratedImage poster = new WebDecoratedImage(this.poster);
		poster.setPreferredSize(posterSize);
		poster.setRound(2);

		WebPanel info = makeInfoPane(size, posterSize, posterMargin, title, year, rate, genre);
		info.setBackground(Colors.BACKGROUND_INFO);

		WebOverlay backOverlay = new WebOverlay(background, info, SwingUtilities.CENTER, SwingUtilities.BOTTOM);

		WebOverlay frontOverlay = new WebOverlay(backOverlay, poster);
		frontOverlay.setOverlayMargin(posterTopMargin, posterMargin, posterMargin, posterRightMargin);

		add(frontOverlay, BorderLayout.CENTER);
	}

	private WebPanel makeInfoPane(Dimension size, Dimension posterSize, int posterMargin, String title, String year, String rate, String genre) {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

		int infoWidth = size.width - posterMargin - posterSize.width;
		int infoHeight = Dimensions.getExplorerTileInfoHeight();
		int topHeight = infoHeight / 5 * 3;
		int bottomHeight = infoHeight - topHeight;
		
		result.setPreferredSize(new Dimension(size.width, infoHeight));
		result.add(UIUtils.makeHorizontalFillerPane(posterMargin + posterSize.width));

		WebPanel infoTop = new WebPanel(new BorderLayout());
		infoTop.setOpaque(false);
		infoTop.setPreferredSize(new Dimension(infoWidth, topHeight));

		WebLabel titleL = new WebLabel(title);
		infoTop.add(titleL, BorderLayout.CENTER);
		TooltipManager.setTooltip(titleL, title, TooltipWay.up, 0);
		titleL.setMargin(5);
		titleL.setFontSize(16);
		titleL.setForeground(Colors.FOREGROUND_TITLE);
		titleL.setShadeColor(Colors.FOREGROUND_SHADE_TITLE);
		titleL.setDrawShade(true);

		WebLabel yearL = new WebLabel(year);
		infoTop.add(yearL, BorderLayout.EAST);
		yearL.setHorizontalAlignment(SwingConstants.RIGHT);
		yearL.setMargin(5);
		yearL.setFontSize(14);
		yearL.setForeground(Colors.FOREGROUND_STANDARD);
		yearL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		yearL.setDrawShade(true);

		WebPanel infoBottom = new WebPanel();
		infoBottom.setOpaque(false);
		infoBottom.setPreferredSize(new Dimension(infoWidth, bottomHeight));

		WebLabel rateL = new WebLabel(rate);
		infoBottom.add(rateL, BorderLayout.WEST);
		rateL.setMargin(5);
		rateL.setFontSize(12);
		rateL.setItalicFont();
		rateL.setForeground(Colors.FOREGROUND_STANDARD);
		rateL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		rateL.setDrawShade(true);

		WebLabel genreL = new WebLabel(genre);
		infoBottom.add(genreL, BorderLayout.CENTER);
		genreL.setHorizontalAlignment(SwingConstants.RIGHT);
		genreL.setMargin(5);
		genreL.setFontSize(12);
		genreL.setItalicFont();
		genreL.setForeground(Colors.FOREGROUND_ALTERNATIVE);
		genreL.setShadeColor(Colors.FOREGROUND_SHADE_ALTERNATIVE);
		genreL.setDrawShade(true);

		result.add(new TwoSidesPanel(SwingUtilities.VERTICAL, infoTop, infoBottom));

		return result;
	}

}
