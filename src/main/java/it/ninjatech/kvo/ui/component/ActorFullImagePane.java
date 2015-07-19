package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.panel.WebOverlay;
import com.alee.laf.panel.WebPanel;

public class ActorFullImagePane extends WebPanel {

	private static final long serialVersionUID = -4595757402190395567L;

	public ActorFullImagePane(Image image, String imdbId) {
		super(new BorderLayout());

		init(image, imdbId);
	}

	private void init(Image image, String imdbId) {
		JComponent content = null;
		
		if (image != null && StringUtils.isNotBlank(imdbId)) {
			content = new WebOverlay(new WebImage(image), makeImdbLink(imdbId), SwingUtilities.RIGHT, SwingUtilities.BOTTOM);
		}
		else if (image != null) {
			content = new WebImage(image);
		}
		else {
			WebPanel pane = new WebPanel();
			pane.setBackground(Colors.BACKGROUND_INFO);
			pane.setPreferredSize(Dimensions.ACTOR_FULL_SIZE);
			
			if (StringUtils.isNotBlank(imdbId)) {
				content = new WebOverlay(pane, makeImdbLink(imdbId), SwingUtilities.RIGHT, SwingUtilities.BOTTOM);
			}
			else {
				content = pane;
			}
		}
		
		add(content, BorderLayout.CENTER);
	}
	
	private WebLinkLabel makeImdbLink(String imdbId) {
		WebLinkLabel result = new WebLinkLabel();
		
		result.setToolTipText("Find out more on IMDb");
		result.setIcon(ImageRetriever.retrieveWallIMDb());
		result.setLink(ImdbManager.getNameUrl(imdbId), false);
		
		return result;
	}

}
