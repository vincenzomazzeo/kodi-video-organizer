package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.myapifilms.MyApiFilmsManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.tvserie.TvSerieController;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.MemoryUtils;
import it.ninjatech.kvo.util.PeopleManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;

public class TvSerieWallLauncher extends WebDialog implements WindowListener, HierarchyListener {

	private static final long serialVersionUID = 4530104483604809939L;

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
		
//		TvSerie tvSerie = new TvSerie("121361", "Il Trono di Spade", EnhancedLocaleMap.getByLanguage("it"));
//		TvSerie tvSerie = new TvSerie("72158", "One Tree Hill", EnhancedLocaleMap.getByLanguage("it"));
		TvSerie tvSerie = new TvSerie("257655", "Arrow", EnhancedLocaleMap.getByLanguage("it"));
		TheTvDbManager.getInstance().getData(tvSerie);
		FanarttvManager.getInstance().getData(tvSerie);
//		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("/Users/Shared/Well/Multimedia/Video/TV Series")) ;
//		tvSeriesPathEntity.addTvSerie(new File("/Users/Shared/Well/Multimedia/Video/TV Series/One Tree Hill"));
//		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("D:/GitHubRepository/Test")) ;
//		tvSeriesPathEntity.addTvSerie(new File("D:/GitHubRepository/Test/Ciccio"));
		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("/Users/hawkeleon/Downloads/Workbench")) ;
		tvSeriesPathEntity.addTvSerie(new File("/Users/hawkeleon/Downloads/Workbench/Arrow"));
		TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
		tvSeriePathEntity.setTvSerie(tvSerie);
//		TvSerieManager.getInstance().scan(tvSeriePathEntity);
		MemoryUtils.printMemory("After start");
		
		TvSerieWallLauncher tvSerieWall = new TvSerieWallLauncher(tvSerie);
		MemoryUtils.printMemory("Opening");
		tvSerieWall.setVisible(true);
		MemoryUtils.printMemory("Closing");
		
		System.exit(0);
	}
	
	private final TvSerie tvSerie;
	private TvSerieController controller;
	
	private TvSerieWallLauncher(TvSerie tvSerie) {
		super();
		
		setModal(true);
		
		this.tvSerie = tvSerie;
		
		addHierarchyListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addWindowListener(this);
		
		init(tvSerie);
		setSize(new Dimension(1380, 900));
		setLocationRelativeTo(null);
	}
	
	private void init(TvSerie tvSerie) {
		setLayout(new BorderLayout());
		
		this.controller = new TvSerieController();
		WebScrollPane pane = new WebScrollPane(this.controller.getView());
		
		add(pane, BorderLayout.CENTER);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.controller.destroy();
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
	
	@Override
	public void hierarchyChanged(HierarchyEvent event) {
		if ((event.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED && isShowing()) {
			this.controller.showTvSerie(this.tvSerie);
		}
	}
	
}
