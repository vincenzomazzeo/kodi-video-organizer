package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.myapifilms.MyApiFilmsManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.tvserie.TvSerieFetchController;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.MemoryUtils;
import it.ninjatech.kvo.util.PeopleManager;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alee.laf.WebLookAndFeel;

public class TvSerieSearchLauncher {

	public static void main(String[] args) throws Exception {
		MemoryUtils.printMemory("Start");
		WebLookAndFeel.install();
		SettingsHandler.init();
		AsyncManager.init();
		ConnectionHandler.init();
		PeopleManager.init();
		EnhancedLocaleMap.init();
		TvSerieManager.init();
		TheTvDbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getTheTvDbEnabled());
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApiKey());
		FanarttvManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getFanarttvEnabled());
		FanarttvManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getFanarttvApiKey());
		ImdbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getImdbEnabled());
		MyApiFilmsManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getMyApiFilmsEnabled());

		TvSeriesPathEntity tvSeriesPathEntity = TvSerieManager.getInstance().addTvSeriesPathEntity(new File("D:/GitHubRepository/Test"));
//		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("D:/GitHubRepository/Test")) ;
		
		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/24"));
		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/Ciccio"));
		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/La spada della verità"));
		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/The Following"));
		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/Flash"));
		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/Rezartina"));
		
		Set<TvSeriePathEntity> entities = new HashSet<>(tvSeriesPathEntity.getTvSeries());
		
		TvSerieFetchController controller = new TvSerieFetchController(entities);
		Map<TvSeriePathEntity, Boolean> searchResult = controller.search();
		
		System.out.println(searchResult.size());
		
		System.exit(0);
	}
	
}
