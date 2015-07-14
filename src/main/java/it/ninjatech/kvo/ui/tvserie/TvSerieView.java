package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.SwingConstants;

import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.panel.WebOverlay;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

public class TvSerieView extends WebPanel {

	private static final long serialVersionUID = -8219959298613920784L;

	public TvSerieView() {
		super();
		
		init();
	}
	
	private void init() {
		setLayout(new VerticalFlowLayout());
		
		add(makeTitlePane());
		add(makeGenresPane());
		add(makeInfoPane());
		add(new TvSerieFanartSlider());
	}
	
	private WebPanel makeTitlePane() {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		
		result.setBackground(Colors.BACKGROUND_INFO);
		
		EnhancedLocale language = EnhancedLocaleMap.getByLanguage("it");
		String title = "Il trono di spade";
		String status = "Continuing";
		String rating = "8.8";
		String ratingCount = "34293";
		
		WebImage languageI = new WebImage(language.getLanguageFlag());
		result.add(languageI);
		
		WebLabel titleL = new WebLabel(title);
		result.add(titleL);
		titleL.setMargin(2, 10, 5, 10);
		titleL.setFontSize(30);
		titleL.setForeground(Colors.FOREGROUND_TITLE);
		titleL.setShadeColor(Colors.FOREGROUND_SHADE_TITLE);
		titleL.setDrawShade(true);
		
		WebLabel statusL = new WebLabel(String.format("(%s)", status));
		result.add(statusL);
		statusL.setFontSize(20);
		statusL.setForeground(Colors.FOREGROUND_STANDARD);
		statusL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		statusL.setDrawShade(true);
		
		WebPanel ratingPane = new WebPanel(new VerticalFlowLayout());
		result.add(ratingPane);
		ratingPane.setOpaque(false);
		
		WebImage star = new WebImage(ImageRetriever.retrieveWallStar());
		
		WebLabel ratingL = new WebLabel(rating);
		ratingL.setFontSize(14);
		ratingL.setForeground(Colors.FOREGROUND_STANDARD);
		ratingL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		ratingL.setDrawShade(true);
		
		WebOverlay starOverlay = new WebOverlay(star, ratingL, SwingConstants.CENTER, SwingConstants.CENTER);
		ratingPane.add(starOverlay);
		starOverlay.setBackground(new Color(0, 0, 0, 0));
		
		WebLabel ratingCountL = new WebLabel(ratingCount);
		ratingPane.add(ratingCountL);
		ratingCountL.setHorizontalAlignment(SwingConstants.CENTER);
		ratingCountL.setFontSize(10);
		ratingCountL.setForeground(Colors.FOREGROUND_STANDARD);
		ratingCountL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		ratingCountL.setDrawShade(true);
		
		return result;
	}
	
	private WebPanel makeGenresPane() {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		result.setBackground(Colors.BACKGROUND_INFO);
		
		String genres = "Adventure, Drama, Fantasy, Variety";
		
		WebLabel genresL = new WebLabel(genres);
		result.add(genresL);
		genresL.setMargin(0, 5, 0, 5);
		genresL.setFontSize(14);
		genresL.setForeground(Colors.FOREGROUND_STANDARD);
		genresL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		genresL.setDrawShade(true);
		
		return result;
	}
	
	private WebPanel makeInfoPane() {
		WebPanel result = new WebPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
		
		result.setBackground(Colors.BACKGROUND_INFO);
		
		String firstAired = "10/12/2015";
		String network = "HBO";
		String contentRating = "TV-MA";
		String imdbId = "tt0944947";
		
		WebLabel firstAiredL = new WebLabel(String.format("First Aired: %s", firstAired));
		result.add(firstAiredL);
		firstAiredL.setFontSize(12);
		firstAiredL.setForeground(Colors.FOREGROUND_STANDARD);
		firstAiredL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		firstAiredL.setDrawShade(true);
		
		WebLabel networkL = new WebLabel(String.format("Network: %s", network));
		result.add(networkL);
		networkL.setFontSize(12);
		networkL.setForeground(Colors.FOREGROUND_STANDARD);
		networkL.setShadeColor(Colors.FOREGROUND_SHADE_STANDARD);
		networkL.setDrawShade(true);
		
		WebImage contentRatingI = new WebImage(UIUtils.getContentRatingWallIcon(contentRating));
		result.add(contentRatingI);
		
		WebLinkLabel imdbL = new WebLinkLabel(ImageRetriever.retrieveWallIMDb());
		result.add(imdbL);
		imdbL.setLink(String.format("http://www.imdb.com/title/%s", imdbId), false);
		
		return result;
	}
	
}
