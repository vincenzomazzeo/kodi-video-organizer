package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;

import java.awt.BorderLayout;
import java.awt.Image;

import com.alee.extended.image.WebImage;
import com.alee.laf.panel.WebPanel;

public class SimpleFullImagePane extends WebPanel {

	private static final long serialVersionUID = -6444234979222736004L;

	public SimpleFullImagePane(Image image) {
		super(new BorderLayout());

		init(image);
	}

	private void init(Image image) {
		setBackground(Colors.BACKGROUND_INFO);
		
		if (image != null) {
			add(new WebImage(image), BorderLayout.CENTER);
		}
	}
	
}
