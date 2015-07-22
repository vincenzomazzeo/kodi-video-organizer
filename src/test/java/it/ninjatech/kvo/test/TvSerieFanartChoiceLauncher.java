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
import it.ninjatech.kvo.ui.tvserie.TvSerieFanartChoiceController;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.io.File;
import java.text.DecimalFormat;

import com.alee.laf.WebLookAndFeel;

public class TvSerieFanartChoiceLauncher {

	private static final long serialVersionUID = 2497031401907408168L;

	public static void main(String[] args) throws Exception {
		printMemory("Start");
		WebLookAndFeel.install();
		SettingsHandler.init();
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
		printMemory("After");
		
		printMemory("Before controller");
		TvSerieFanartChoiceController controller = new TvSerieFanartChoiceController(tvSeriePathEntity, TvSerieFanart.Fanart);
		controller.start();
		printMemory("After controller");
		System.in.read();
		System.in.read();
		printMemory("After input");
		controller.getView().setVisible(true);
		printMemory("After visible");
//		System.in.read();
//		System.in.read();
//		controller.getView().dispose();
		printMemory("After dispose");
		System.in.read();
		System.in.read();
		controller = null;
		printMemory("After controller = null");
		System.in.read();
		System.in.read();
		
		System.out.println();
		
		printMemory("Before controller 2");
		controller = new TvSerieFanartChoiceController(tvSeriePathEntity, TvSerieFanart.Banner);
		controller.start();
		printMemory("After controller 2");
		System.in.read();
		System.in.read();
		printMemory("After input 2");
		controller.getView().setVisible(true);
		printMemory("After visible 2");
//		System.in.read();
//		System.in.read();
//		controller.getView().dispose();
		printMemory("After dispose 2");
		System.in.read();
		System.in.read();
		controller = null;
		printMemory("After controller 2 = null");
		System.in.read();
		System.in.read();
		
		System.exit(0);
		
//		TvSerieFanartChoice vvSerieFanartChoice = new TvSerieFanartChoice(tvSeriePathEntity);
//		
//		vvSerieFanartChoice.setVisible(true);
	}
	
	private static long lastFree = 0l;
	
	public static void printMemory(String message) {
		printMemory(message, true);
	}
	
	public static void printMemory(String message, boolean gc) {
		Runtime runtime = Runtime.getRuntime();
		if (gc) {
			runtime.gc();
		}
		DecimalFormat format = new DecimalFormat("###,###,###");
		long total = runtime.totalMemory();
		long max = runtime.totalMemory();
		long free = runtime.freeMemory();
		System.out.printf("[Total: %s - Max: %s - Occupied: %s - Delta: %s] - %s\n", 
		                  format.format(total), 
		                  format.format(max), 
		                  format.format(total - free), 
		                  format.format(lastFree - free), 
		                  message);
		lastFree = free;
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
