package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.transictioneffect.TransictionEffectExecutor;
import it.ninjatech.kvo.ui.tvserie.TvSerieFanartChoiceController;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;

public class TvSerieFanartChoice /*extends WebFrame implements WindowListener*/ {

	private static final long serialVersionUID = 2497031401907408168L;

	public static void main(String[] args) throws Exception {
		WebLookAndFeel.install();
		SettingsHandler.init();
		TransictionEffectExecutor.init();
		AsyncManager.init();
		EnhancedLocaleMap.init();
		TheTvDbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getTheTvDbEnabled());
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApiKey());
		FanarttvManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getFanarttvEnabled());
		FanarttvManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getFanarttvApiKey());
		ImdbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getImdbEnabled());
		
//		TvSerie tvSerie = new TvSerie("121361", "Il Trono di Spade", EnhancedLocaleMap.getByLanguage("it"));
//		TvSerie tvSerie = new TvSerie("72158", "One Tree Hill", EnhancedLocaleMap.getByLanguage("it"));
		TvSerie tvSerie = new TvSerie("75682", "Bones", EnhancedLocaleMap.getByLanguage("it"));
		TheTvDbManager.getInstance().getData(tvSerie);
		FanarttvManager.getInstance().getData(tvSerie);
//		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("/Users/Shared/Well/Multimedia/Video/TV Series")) ;
//		tvSeriesPathEntity.addTvSerie(new File("/Users/Shared/Well/Multimedia/Video/TV Series/Bones"));
		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("D:/GitHubRepository/Test")) ;
		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/Ciccio"));
		TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
		tvSeriePathEntity.setTvSerie(tvSerie);
		
		TvSerieFanartChoiceController controller = new TvSerieFanartChoiceController(tvSeriePathEntity, TvSerieFanart.Fanart);
		controller.start();
		controller.getView().setVisible(true);
		System.exit(0);
//		TvSerieFanartChoice vvSerieFanartChoice = new TvSerieFanartChoice(tvSeriePathEntity);
//		
//		vvSerieFanartChoice.setVisible(true);
	}
	
	/*
	private TvSerieFanartChoice(TvSeriePathEntity tvSeriePathEntity) {
		super();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);
		
		init(tvSeriePathEntity);
		pack();
		setLocationRelativeTo(null);
	}
	
	private void init(TvSeriePathEntity tvSeriePathEntity) {
		setLayout(new BorderLayout());
		
		TvSerieFanartChoiceController controller = new TvSerieFanartChoiceController(tvSeriePathEntity, TvSerieFanart.Fanart);
		WebScrollPane pane = new WebScrollPane(controller.getView());
		controller.start();
		
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
	*/
	
}
