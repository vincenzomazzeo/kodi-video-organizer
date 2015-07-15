package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.transictioneffect.TransictionEffectExecutor;
import it.ninjatech.kvo.ui.tvserie.TvSerieController;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;

public class TvSerieWall extends WebFrame implements WindowListener {

	private static final long serialVersionUID = 4530104483604809939L;

	public static void main(String[] args) throws Exception {
		SettingsHandler.init();
		TransictionEffectExecutor.init();
		AsyncManager.init();
		EnhancedLocaleMap.init();
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApikey());
		
//		TvSerie tvSerie = new TvSerie("121361", "Il Trono di Spade", EnhancedLocaleMap.getByLanguage("it"));
		TvSerie tvSerie = new TvSerie("72158", "One Tree Hill", EnhancedLocaleMap.getByLanguage("it"));
		TheTvDbManager.getInstance().getData(tvSerie);
		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("/Users/Shared/Well/Multimedia/Video/TV Series")) ;
		tvSeriesPathEntity.addTvSerie(new File("/Users/Shared/Well/Multimedia/Video/TV Series/One Tree Hill"));
		TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
		tvSeriePathEntity.setTvSerie(tvSerie);
		
		TvSerieWall tvSerieWall = new TvSerieWall(tvSeriePathEntity);
		
		tvSerieWall.setVisible(true);
	}
	
	private TvSerieWall(TvSeriePathEntity tvSeriePathEntity) {
		super();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);
		
		init(tvSeriePathEntity);
		setSize(new Dimension(1380, 900));
		setLocationRelativeTo(null);
	}
	
	private void init(TvSeriePathEntity tvSeriePathEntity) {
		setLayout(new BorderLayout());
		
		TvSerieController controller = new TvSerieController(tvSeriePathEntity);
		WebScrollPane pane = new WebScrollPane(controller.getView());
		
		add(pane, BorderLayout.CENTER);
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
