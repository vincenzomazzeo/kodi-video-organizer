package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.KodiVideoOrganizer;
import it.ninjatech.kvo.ui.component.ToolBar;
import it.ninjatech.kvo.ui.exception.ExceptionController;
import it.ninjatech.kvo.ui.exception.ExceptionView;
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

	private ToolBar toolBar;
	private ExceptionController exceptionController;

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

	public void notifyException(Throwable exception) {
		int toRead = this.exceptionController.notifyException(exception);
		this.toolBar.notifyExceptionsToRead(toRead);
	}
	
	public void refreshExceptionsToRead() {
		this.toolBar.notifyExceptionsToRead(this.exceptionController.getToRead());
	}
	
	public ToolBar getToolBar() {
		return this.toolBar;
	}

	private void init() {
		initialize();

		Dimension startupDimension = UIUtils.getStartupDimension();

		setSize(startupDimension);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addWindowListener(this);

		ExceptionView exceptionView = new ExceptionView();
		this.exceptionController = new ExceptionController(exceptionView);
		
		this.toolBar = new ToolBar(exceptionView);
		add(this.toolBar, BorderLayout.NORTH);

		ExplorerView explorer = new ExplorerView(startupDimension.width);
		add(explorer, BorderLayout.LINE_START);
	}

}
