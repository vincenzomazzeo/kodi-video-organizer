package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.KodiVideoOrganizer;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.component.ToolBar;
import it.ninjatech.kvo.ui.exceptionconsole.ExceptionConsoleController;
import it.ninjatech.kvo.ui.explorer.ExplorerController;
import it.ninjatech.kvo.ui.wall.WallController;
import it.ninjatech.kvo.util.Labels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.WindowConstants;

import com.alee.extended.layout.ToolbarLayout;
import com.alee.extended.statusbar.WebMemoryBar;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;

public class UI extends WebFrame implements WindowListener {

	private static final long serialVersionUID = -8112321473328517789L;

	private static UI self;

	public static UI build(List<TvSeriesPathEntity> tvSeriesPathEntities) {
		self = self == null ? new UI(tvSeriesPathEntities) : self;

		return self;
	}

	public static UI get() {
		return self;
	}

	private final ExceptionConsoleController exceptionController;
	private final WallController wallController;
	private final ExplorerController explorerControler;
	private ToolBar toolBar;

	private UI(List<TvSeriesPathEntity> tvSeriesPathEntities) {
		super(Labels.APPLICATION_TITLE);

		this.exceptionController = new ExceptionConsoleController();
		this.wallController = new WallController();
		this.explorerControler = new ExplorerController(tvSeriesPathEntities, this.wallController);

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

		Dimension startupDimension = Dimensions.getStartupSize();

		setSize(startupDimension);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addWindowListener(this);

		WebPanel contentPane = UIUtils.makeStandardPane(new BorderLayout());
		setContentPane(contentPane);
		contentPane.setOpaque(true);
		contentPane.setBackground(Colors.BACKGROUND_INFO);

		this.toolBar = new ToolBar(this.exceptionController.getView());
		contentPane.add(this.toolBar, BorderLayout.NORTH);

		contentPane.add(this.explorerControler.getView(), BorderLayout.LINE_START);
		
		contentPane.add(this.wallController.getView(), BorderLayout.CENTER);

		WebStatusBar statusBar = new WebStatusBar();
		contentPane.add(statusBar, BorderLayout.SOUTH);

		WebMemoryBar memoryBar = new WebMemoryBar();
		memoryBar.setPreferredWidth(memoryBar.getPreferredSize().width + 20);
		statusBar.add(memoryBar, ToolbarLayout.END);
	}

}
