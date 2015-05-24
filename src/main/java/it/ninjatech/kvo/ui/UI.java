package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.KodiVideoOrganizer;
import it.ninjatech.kvo.ui.explorer.ExplorerView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.WindowConstants;

import com.alee.laf.rootpane.WebFrame;

public class UI extends WebFrame implements WindowListener {

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
	
	@Override
	public void windowOpened(WindowEvent windowEvent) {
	}

	@Override
	public void windowClosing(WindowEvent windowEvent) {
		KodiVideoOrganizer.exit();
	}

	@Override
	public void windowClosed(WindowEvent windowEvent) {
	}

	@Override
	public void windowIconified(WindowEvent windowEvent) {
	}

	@Override
	public void windowDeiconified(WindowEvent windowEvent) {
	}

	@Override
	public void windowActivated(WindowEvent windowEvent) {
	}

	@Override
	public void windowDeactivated(WindowEvent windowEvent) {
	}

	private void init() {
		initialize();

		Dimension startupDimension = UIUtils.getStartupDimension();

		setSize(startupDimension);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addWindowListener(this);

		ExplorerView explorer = new ExplorerView(startupDimension.width);
		add(explorer, BorderLayout.LINE_START);
	}

}
