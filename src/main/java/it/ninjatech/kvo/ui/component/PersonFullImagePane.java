package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.image.WebImage;
import com.alee.extended.panel.WebOverlay;
import com.alee.laf.panel.WebPanel;

public class PersonFullImagePane extends WebPanel {

	private static final long serialVersionUID = -4595757402190395567L;

	public PersonFullImagePane(Image image, String imdbId) {
		super(new BorderLayout());

		init(image, imdbId);
	}

	private void init(Image image, String imdbId) {
		JComponent content = null;
		
		
		if (image != null && StringUtils.isNotBlank(imdbId)) {
			content = new WebOverlay(new WebImage(image), UIUtils.makeImdbLink(imdbId), SwingUtilities.RIGHT, SwingUtilities.BOTTOM);
		}
		else if (image != null) {
			content = new WebImage(image);
		}
		else {
			WebPanel pane = new WebPanel();
			pane.setBackground(Colors.BACKGROUND_INFO);
			pane.setPreferredSize(Dimensions.ACTOR_FULL_SIZE);
			
			if (StringUtils.isNotBlank(imdbId)) {
				content = new WebOverlay(pane, UIUtils.makeImdbLink(imdbId), SwingUtilities.RIGHT, SwingUtilities.BOTTOM);
			}
			else {
				content = pane;
			}
		}
		
		add(content, BorderLayout.CENTER);
	}
	
}
