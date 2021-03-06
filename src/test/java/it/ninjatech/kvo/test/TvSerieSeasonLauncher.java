package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.myapifilms.MyApiFilmsManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.tvserie.TvSerieSeasonController;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.MemoryUtils;
import it.ninjatech.kvo.util.PeopleManager;

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
		PeopleManager.init();
		EnhancedLocaleMap.init();
		TvSerieManager.init();
		TheTvDbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getTheTvDbEnabled());
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApiKey());
		FanarttvManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getFanarttvEnabled());
		FanarttvManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getFanarttvApiKey());
		ImdbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getImdbEnabled());
		MyApiFilmsManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getMyApiFilmsEnabled());
		
//		TvSerie tvSerie = new TvSerie("72158", "One Tree Hill", EnhancedLocaleMap.getByLanguage("it"));
		TvSerie tvSerie = new TvSerie("257655", "Arrow", EnhancedLocaleMap.getByLanguage("it"));
		TheTvDbManager.getInstance().getData(tvSerie);
		FanarttvManager.getInstance().getData(tvSerie);
		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("/Users/hawkeleon/Downloads/Workbench")) ;
		tvSeriesPathEntity.addTvSerie(new File("/Users/hawkeleon/Downloads/Workbench/Arrow"));
//		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("D:/GitHubRepository/Test")) ;
//		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/Ciccio"));
		TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
		tvSeriePathEntity.setTvSerie(tvSerie);
//		TvSerieManager.getInstance().scan(tvSeriePathEntity);
		MemoryUtils.printMemory("After start");
		
		List<TvSerieSeason> seasons = new ArrayList<>(tvSerie.getSeasons());
		TvSerieSeason season = seasons.get(3);
		TvSerieEpisode episode = season.getEpisodes().iterator().next();
		episode.setFilename("1.avi");
		episode.addSubtitleFilename("Arrow - 01 - Calma apparente.en.srt");
		TvSerieSeasonController controller = new TvSerieSeasonController(season);
		controller.start();
		MemoryUtils.printMemory("Controller started");
		controller.getView().setVisible(true);
		MemoryUtils.printMemory("Closing");
		
		System.exit(0);
	}
	
}
