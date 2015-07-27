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

import com.alee.laf.WebLookAndFeel;

public class TvSerieFanartChoiceLauncher {

	public static void main(String[] args) throws Exception {
		MemoryUtils.printMemory("Start");
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
		MemoryUtils.printMemory("After");
		
		MemoryUtils.printMemory("Before controller");
		TvSerieFanartChoiceController controller = new TvSerieFanartChoiceController(tvSeriePathEntity, TvSerieFanart.Fanart);
		controller.start();
		MemoryUtils.printMemory("After controller");
		System.in.read();
		System.in.read();
		MemoryUtils.printMemory("After input");
		controller.getView().setVisible(true);
		MemoryUtils.printMemory("After visible");
		System.in.read();
		System.in.read();
		controller = null;
		MemoryUtils.printMemory("After controller = null");
		System.in.read();
		System.in.read();
		
		System.exit(0);
	}
	
}
