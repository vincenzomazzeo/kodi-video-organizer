package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.tvserie.TvSerieSeasonController;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.MemoryUtils;
import it.ninjatech.kvo.worker.TvSerieFileScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alee.laf.WebLookAndFeel;

public class TvSerieSeasonLauncher {

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
		
		TvSerie tvSerie = new TvSerie("72158", "One Tree Hill", EnhancedLocaleMap.getByLanguage("it"));
		TheTvDbManager.getInstance().getData(tvSerie);
		FanarttvManager.getInstance().getData(tvSerie);
//		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("/Users/Shared/Well/Multimedia/Video/TV Series")) ;
//		tvSeriesPathEntity.addTvSerie(new File("/Users/Shared/Well/Multimedia/Video/TV Series/Arrow"));
		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("D:/GitHubRepository/Test")) ;
		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/Ciccio"));
		TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
		tvSeriePathEntity.setTvSerie(tvSerie);
		(new TvSerieFileScanner(tvSeriePathEntity)).work();
		MemoryUtils.printMemory("After start");
		
		List<TvSerieSeason> seasons = new ArrayList<>(tvSerie.getSeasons());
		TvSerieSeasonController controller = new TvSerieSeasonController(tvSeriePathEntity, seasons.get(7));
		controller.start();
		MemoryUtils.printMemory("Controller started");
		controller.getView().setVisible(true);
		MemoryUtils.printMemory("Closing");
		
		System.exit(0);
	}
	
}
