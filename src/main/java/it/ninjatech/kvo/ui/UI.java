package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.ui.explorer.ExplorerView;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.WindowConstants;

import com.alee.laf.rootpane.WebFrame;

public class UI extends WebFrame {

	private static final long serialVersionUID = -8112321473328517789L;

	private static UI self;
	
	public static UI build() {
		self = self == null ? new UI() : self;
		
		return self;
	}
	
	public static UI get() {
		return self;
	}
	
	private UI() {
		super("Kodi Video Organizer");

		init();
	}

	private void init() {
		initialize();

		Dimension startupDimension = UIUtils.getStartupDimension();

		setSize(startupDimension);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		ExplorerView explorer = new ExplorerView(startupDimension.width);
		add(explorer, BorderLayout.LINE_START);

//		packAndCenter(true);
	}

}
