package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.explorer.tvserie.ExplorerTvSerieController;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import com.alee.laf.rootpane.WebFrame;

public class TvSerieTiles extends WebFrame {

	private static final long serialVersionUID = 2234913674587205795L;

	public static void main(String[] args) throws Exception {
		SettingsHandler.init();
		AsyncManager.init();
		EnhancedLocaleMap.init();
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApiKey());
		
		List<Data> datas = new ArrayList<>();
		datas.add(new Data("121361", "Il Trono di Spade"));
		datas.add(new Data("80348", "Chuck"));
		datas.add(new Data("279121", "The Flash"));
		datas.add(new Data("257655", "Arrow"));
		datas.add(new Data("76290", "24"));
		datas.add(new Data("248742", "Person of Interest"));
		datas.add(new Data("72158", "One Tree Hill"));
		
//		TvSerie tvSerie = new TvSerie("121361", "Il Trono di Spade", EnhancedLocaleMap.getByLanguage("it"));
//		TheTvDbManager.getInstance().getData(tvSerie);
//		TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("d:/GitHubRepository/Test")) ;
//		tvSeriesPathEntity.addTvSerie(new File("d:/GitHubRepository/Test/Ciccio"));
//		TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
//		tvSeriePathEntity.setTvSerie(tvSerie);
		
		TvSerieTiles frame = new TvSerieTiles(/*tvSeriePathEntity*/);
		
		frame.setVisible(true);
		
		System.out.println("start thread");
		
		(new Thread(new Loader(datas, frame.controller))).start();
	}
	
	private final ExplorerTvSerieController controller;
	
	private TvSerieTiles(/*TvSeriePathEntity tvSeriePathEntity*/) {
		super();
		
		this.controller = new ExplorerTvSerieController();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setResizable(false);
		
		init(/*tvSeriePathEntity*/);
		setLocationRelativeTo(null);
	}

	private void init(/*TvSeriePathEntity tvSeriePathEntity*/) {
		Dimension startupDimension = Dimensions.getStartupSize();
		
		getContentPane().setLayout(new BorderLayout());
		setSize(startupDimension.width / 5 + 30, 500);
		
//		ExplorerTile tile = new ExplorerTile(null, ImageRetriever.retrieveExplorerTilePosterTvSerie().getImage(),
//		                                     TvSerieUtils.getTitle(tvSeriePathEntity), 
//		                                     TvSerieUtils.getYear(tvSeriePathEntity),
//		                                     TvSerieUtils.getRate(tvSeriePathEntity),
//		                                     TvSerieUtils.getGenre(tvSeriePathEntity));
//		add(tile, BorderLayout.CENTER);
//		pack();
//		ExplorerTvSerieController controller = new ExplorerTvSerieController();
		add(this.controller.getView(), BorderLayout.CENTER);
		
//		controller.addTile(tvSeriePathEntity);
	}
	
	private static class Data {
		
		private final String id;
		private final String title;
		
		public Data(String id, String title) {
			this.id = id;
			this.title = title;
		}
		
	}
	
	private static class Loader implements Runnable {

		private final List<Data> datas;
		private final ExplorerTvSerieController controller;
		
		public Loader(List<Data> datas, ExplorerTvSerieController controller) {
			this.datas = datas;
			this.controller = controller;
		}

		@Override
		public void run() {
			System.out.println("running");
			
			for (Data data : datas) {
				System.out.println(data.title);
				TvSerie tvSerie = new TvSerie(data.id, data.title, EnhancedLocaleMap.getByLanguage("it"));
				TheTvDbManager.getInstance().getData(tvSerie);
				TvSeriesPathEntity tvSeriesPathEntity = new TvSeriesPathEntity(new File("d:/GitHubRepository/Test")) ;
				tvSeriesPathEntity.addTvSerie(new File("d:/GitHubRepository/Test/Ciccio"));
				final TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
				tvSeriePathEntity.setTvSerie(tvSerie);
				System.out.println(data.title + " - " + tvSerie.getName() + " - " + tvSerie.getFanart() + " - " + tvSerie.getPoster());
				
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						System.out.println("Adding tile");
						controller.addTile(tvSeriePathEntity);
					}});
				
				try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(3));
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
