package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.ui.transictioneffect.TransictionEffectExecutor;
import it.ninjatech.kvo.ui.tvserie.TvSerieView;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.alee.laf.rootpane.WebFrame;

public class TvSerieWall extends WebFrame implements WindowListener {

	private static final long serialVersionUID = 4530104483604809939L;

	public static void main(String[] args) throws Exception {
		SettingsHandler.init();
		TransictionEffectExecutor.init();
		AsyncManager.init();
		EnhancedLocaleMap.init();
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApikey());
		
		TvSerieWall tvSerieWall = new TvSerieWall();
		
		tvSerieWall.setVisible(true);
	}
	
	private TvSerieWall() {
		super();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);
		
		setResizable(false);
		
		init();
		setSize(new Dimension(1380, 900));
		setLocationRelativeTo(null);
	}
	
	private void init() {
		setLayout(new BorderLayout());
		
		add(new TvSerieView(), BorderLayout.CENTER);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
}
