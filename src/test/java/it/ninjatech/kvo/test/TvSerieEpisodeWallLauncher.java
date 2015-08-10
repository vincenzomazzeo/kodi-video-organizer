package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.myapifilms.MyApiFilmsManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.tvserie.TvSerieEpisodeController;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.MemoryUtils;
import it.ninjatech.kvo.util.PeopleManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;

public class TvSerieEpisodeWallLauncher extends WebDialog implements HierarchyListener {

	private static final long serialVersionUID = 4530104483604809939L;

	public static void main(String[] args) throws Exception {
		MemoryUtils.printMemory("Start");
		WebLookAndFeel.install();
		SettingsHandler.init();
		AsyncManager.init();
		PeopleManager.init();
		EnhancedLocaleMap.init();
		TheTvDbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getTheTvDbEnabled());
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApiKey());
		FanarttvManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getFanarttvEnabled());
		FanarttvManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getFanarttvApiKey());
		ImdbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getImdbEnabled());
		MyApiFilmsManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getMyApiFilmsEnabled());
		
//		TvSerie tvSerie = new TvSerie("121361", "Il Trono di Spade", EnhancedLocaleMap.getByLanguage("it"));
		TvSerie tvSerie = new TvSerie("72158", "One Tree Hill", EnhancedLocaleMap.getByLanguage("it"));
		TheTvDbManager.getInstance().getData(tvSerie);
		FanarttvManager.getInstance().getData(tvSerie);
		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("/Users/Shared/Well/Multimedia/Video/TV Series")) ;
		tvSeriesPathEntity.addTvSerie(new File("/Users/Shared/Well/Multimedia/Video/TV Series/One Tree Hill"));
//		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("D:/GitHubRepository/Test")) ;
//		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/Ciccio"));
		TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
		tvSeriePathEntity.setTvSerie(tvSerie);
		MemoryUtils.printMemory("After start");
		
		List<TvSerieSeason> seasons = new ArrayList<>(tvSerie.getSeasons());
		TvSerieEpisode episode = seasons.get(3).getEpisodes().iterator().next();
		episode.setFilename("test");
		episode.setSubtitleFilenames(new HashSet<String>(Arrays.asList("test.en.srt", "test.2.en.srt", "test.it.srt", "test.3.en.srt")));
		TvSerieEpisodeWallLauncher wall = new TvSerieEpisodeWallLauncher(episode);
		MemoryUtils.printMemory("Opening");
		wall.setVisible(true);
		wall.controller.destroy();
		MemoryUtils.printMemory("Closing");
		
		System.exit(0);
	}
	
	private final TvSerieEpisode episode;
	private TvSerieEpisodeController controller;
	
	private TvSerieEpisodeWallLauncher(TvSerieEpisode episode) {
		super();
		
		setModal(true);
		
		this.episode = episode;
		
		addHierarchyListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		init(episode);
		setSize(new Dimension(1380, 900));
		setLocationRelativeTo(null);
	}
	
	private void init(TvSerieEpisode episode) {
		setLayout(new BorderLayout());
		
		this.controller = new TvSerieEpisodeController();
		WebScrollPane pane = new WebScrollPane(this.controller.getView());
		
		add(pane, BorderLayout.CENTER);
	}

	@Override
	public void hierarchyChanged(HierarchyEvent event) {
		if ((event.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED && isShowing()) {
			this.controller.showTvSerieEpisode(this.episode, null, null);
		}
	}
	
}
