package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Insets;

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
	private WebLabel title;

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
		TooltipManager.removeTooltips(this.title);
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
			background.setBackground(Colors.BACKGROUND_MISSING_IMAGE_ALPHA);
		}
		else {
			background.add(new WebImage(this.fanart), BorderLayout.CENTER);
		}

		WebDecoratedImage poster = new WebDecoratedImage(this.poster);
		poster.setPreferredSize(posterSize);
		poster.setRound(2);

		WebPanel info = makeInfoPane(size, posterSize, posterMargin, title, year, rate, genre);

		WebOverlay backOverlay = new WebOverlay(background, info, SwingUtilities.CENTER, SwingUtilities.BOTTOM);

		WebOverlay frontOverlay = new WebOverlay(backOverlay, poster);
		frontOverlay.setOverlayMargin(posterTopMargin, posterMargin, posterMargin, posterRightMargin);

		add(frontOverlay, BorderLayout.CENTER);
	}

	private WebPanel makeInfoPane(Dimension size, Dimension posterSize, int posterMargin, String title, String year, String rate, String genre) {
		WebPanel result = UIUtils.makeFlowLayoutPane(FlowLayout.LEFT, 0, 0);
		result.setOpaque(true);
		result.setBackground(Colors.BACKGROUND_INFO_ALPHA);
	    
		int infoWidth = size.width - posterMargin - posterSize.width;
		int infoHeight = Dimensions.getExplorerTileInfoHeight();
		int topHeight = infoHeight / 5 * 3;
		int bottomHeight = infoHeight - topHeight;
		
		result.setPreferredSize(new Dimension(size.width, infoHeight));
		result.add(UIUtils.makeHorizontalFillerPane(posterMargin + posterSize.width, true));

		WebPanel infoTop = UIUtils.makeStandardPane(new BorderLayout());
		infoTop.setPreferredSize(new Dimension(infoWidth, topHeight));

		this.title = UIUtils.makeStandardLabel(title, 16, new Insets(5, 5, 5, 5), null);
		infoTop.add(this.title, BorderLayout.CENTER);
		TooltipManager.setTooltip(this.title, title, TooltipWay.up, 0);
		this.title.setForeground(Colors.FOREGROUND_TITLE);
		this.title.setShadeColor(Colors.FOREGROUND_SHADE_TITLE);

		WebLabel yearL = UIUtils.makeStandardLabel(year, 14, new Insets(5, 5, 5, 5), SwingConstants.RIGHT);
		infoTop.add(yearL, BorderLayout.EAST);

		WebPanel infoBottom = UIUtils.makeStandardPane(new BorderLayout());
		infoBottom.setPreferredSize(new Dimension(infoWidth, bottomHeight));

		WebLabel rateL = UIUtils.makeStandardLabel(rate, 12, new Insets(5, 5, 5, 5), null);
		infoBottom.add(rateL, BorderLayout.WEST);
		rateL.setItalicFont();

		WebLabel genreL = UIUtils.makeStandardLabel(genre, 12, new Insets(5, 5, 5, 5), SwingConstants.RIGHT);
		infoBottom.add(genreL, BorderLayout.CENTER);
		genreL.setItalicFont();
		genreL.setForeground(Colors.FOREGROUND_ALTERNATIVE);
		genreL.setShadeColor(Colors.FOREGROUND_SHADE_ALTERNATIVE);

		result.add(new TwoSidesPanel(SwingUtilities.VERTICAL, infoTop, infoBottom));

		return result;
	}

}
