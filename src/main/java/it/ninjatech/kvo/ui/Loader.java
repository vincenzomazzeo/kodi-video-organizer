package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.Settings;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.myapifilms.MyApiFilmsManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.progressdialogworker.Progress;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.PeopleManager;
import it.ninjatech.kvo.util.Utils;
import it.ninjatech.kvo.worker.AbstractWorker;
import it.ninjatech.kvo.worker.WorkerProgressListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.apache.commons.lang3.StringUtils;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebFrame;

public class Loader extends WebFrame {

	private static final long serialVersionUID = 941416115676655434L;

	private final LoaderSwingWorker loaderSwingWorker;
	private WebLabel progressMessage;
	private WebProgressBar progressBar;

	public Loader() {
		super();

		this.loaderSwingWorker = new LoaderSwingWorker(this);

		setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);

		init();

		pack();
		setLocationRelativeTo(null);
	}

	public void load() {
		this.loaderSwingWorker.execute();
	}

	private void notifyLoaded() {
		try {
			Result result = this.loaderSwingWorker.get();
			setVisible(false);
			dispose();
			
			UI.build(result.tvSeriesPathEntities).setVisible(true);
			
			SwingUtilities.invokeLater(new PostStartNotificator());
		}
		catch (Exception e) {
			e.printStackTrace();
			WebOptionPane.showMessageDialog(null, e.getMessage(), "Error", WebOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

	private void init() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 200));
		setUndecorated(true);
		setResizable(false);

		// TODO rendere carino
		
		WebPanel southPane = new WebPanel(new VerticalFlowLayout(0, 5));
		add(southPane, BorderLayout.SOUTH);

		this.progressMessage = new WebLabel();
		southPane.add(this.progressMessage);
		this.progressMessage.setDrawShade(true);
		this.progressMessage.setFontSize(15);
		this.progressMessage.setHorizontalAlignment(SwingConstants.CENTER);

		this.progressBar = new WebProgressBar(0, 100);
		southPane.add(this.progressBar);
		this.progressBar.setValue(0);
		this.progressBar.setIndeterminate(false);
		this.progressBar.setStringPainted(true);
	}

	private static class LoaderWorker extends AbstractWorker<Result> {

		private enum WorkStep {
			
			CheckWorkingDirectory("Checking working directory", 2, "checkWorkingDirectory"),
			CleanCache("Cleaning cache", 3, "cleanCache"),
			ReadSettings("Reading settings", 2, "readSettings"),
			InitAsyncManager("Initializing Async Manager", 1, "initAsyncManager"),
			InitTvSerieManager("Initializing Tv Serie Manager", 1, "initTvSerieManager"),
			InitPeopleManager("Initializing People Manager", 1, "initPeopleManager"),
			InitEnhancedLocaleMap("Loading enhanced locale map", 5, "initEnhancedLocaleMap"),
			InitTheTvDbManager("Initializing TheTVDB Manager", 15, "initTheTvDbManager"),
			InitFanarttvManager("Initializing Fanart.tv Manager", 15, "initFanarttvManager"),
			InitImdbManager("Initializing IMDb Manager", 1, "initImdbManager"),
			InitMyApiFilmsManager("Initializing MyApiFilms Manager", 1, "initMyApiFilmsManager"),
			InitConnectionHandler("Connecting to DB", 20, "initConnectionHandler"),
			LoadTvSeries("Loading Tv Series", 15, "loadTvSeries"),
			LoadMovies("Loading Movies", 15, "loadMovies");
			
			private final String message;
			private final int step;
			private final String method;
			
			private WorkStep(String message, int step, String method) {
				this.message = message;
				this.step = step;
				this.method = method;
			}
			
		}
		
		private Settings settings;
		private List<TvSeriesPathEntity> tvSeriesPathEntities;
		
		@Override
		public Result work() throws Exception {
			Result result = null;
			
			int progress = 0;
			for (WorkStep workStep : WorkStep.values()) {
				notifyUpdate(workStep.message, null);
				
				LoaderWorker.class.getMethod(workStep.method).invoke(this);
				
				progress += workStep.step;
				notifyUpdate(null, progress);
			}
			
//			notifyUpdate("Checking working directory", 0);
//
//			if (!Utils.getWorkingDirectory().exists()) {
//				if (!Utils.getWorkingDirectory().mkdirs()) {
//					throw new Exception("Failed to create working directory. Program will exit.");
//				}
//			}
//			if (!Utils.getCacheDirectory().exists()) {
//				if (!Utils.getCacheDirectory().mkdirs()) {
//					throw new Exception("Failed to create cache directory. Program will exit.");
//				}
//			}
			
//			notifyUpdate("Cleaning cache", 2);
//			File[] cachedFiles = Utils.getCacheDirectory().listFiles();
//			for (File cachedFile : cachedFiles) {
//				cachedFile.delete();
//			}
			
//			notifyUpdate("Reading settings", 5);
//			SettingsHandler.init();
//			
//			Settings settings = SettingsHandler.getInstance().getSettings();

//			notifyUpdate("Initializing Async Manager", 7);
//			AsyncManager.init();
			
//			notifyUpdate("Initializing Tv Serie Manager", 8);
			
			
//			notifyUpdate("Initializing People Manager", 8);
//			PeopleManager.init();
			
//			notifyUpdate("Loading enhanced locale map", 9);
//			EnhancedLocaleMap.init();

//			if (StringUtils.isNotBlank(settings.getTheTvDbApiKey()) && settings.getTheTvDbEnabled()) {
//				notifyUpdate("Contacting TheTVDB", 10);
//				TheTvDbManager.getInstance().setApiKey(settings.getTheTvDbApiKey());
//			}
			
//			if (StringUtils.isNotBlank(settings.getFanarttvApiKey()) && settings.getFanarttvEnabled()) {
//				notifyUpdate("Contacting Fanart.tv", 25);
//				FanarttvManager.getInstance().setApiKey(settings.getFanarttvApiKey());
//			}
			
//			TheTvDbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getTheTvDbEnabled());
//			FanarttvManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getFanarttvEnabled());
//			ImdbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getImdbEnabled());
//			MyApiFilmsManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getMyApiFilmsEnabled());
			
//			notifyUpdate("Connecting to DB", 40);
//			ConnectionHandler.init();
			
//			notifyUpdate("Loading TV Series", 60);
//			List<TvSeriesPathEntity> tvSeriesPathEntities = (new TvSeriesPathEntityDbMapper()).find();
//			TvSerieDbMapper tvSerieDbMapper = new TvSerieDbMapper();
//			for (TvSeriesPathEntity tvSeriesPathEntity : tvSeriesPathEntities) {
//				List<TvSerie> tvSeries = tvSerieDbMapper.find(tvSeriesPathEntity.getId());
//				
//				tvSeriesPathEntity.addTvSerie(null)
//			}
			/*
		tvSeriesPathEntity.addTvSerie(new File("/Users/hawkeleon/Downloads/Workbench/Arrow"));
		TvSeriePathEntity tvSeriePathEntity = tvSeriesPathEntity.getTvSeries().iterator().next();
		tvSeriePathEntity.setTvSerie(tvSerie);
		(new TvSerieFileScanner(tvSerie)).work();
			 */
			
//			notifyUpdate("Loading Movies", 80);
			
			result = new Result(this.tvSeriesPathEntities);
			
			notifyUpdate("Done", 100);

			return result;
		}
		
		@SuppressWarnings("unused")
		public void checkWorkingDirectory() throws Exception {
			if (!Utils.getWorkingDirectory().exists()) {
				if (!Utils.getWorkingDirectory().mkdirs()) {
					throw new Exception("Failed to create working directory. Program will exit.");
				}
			}
			if (!Utils.getCacheDirectory().exists()) {
				if (!Utils.getCacheDirectory().mkdirs()) {
					throw new Exception("Failed to create cache directory. Program will exit.");
				}
			}
		}
		
		@SuppressWarnings("unused")
		public void cleanCache() {
			File[] cachedFiles = Utils.getCacheDirectory().listFiles();
			for (File cachedFile : cachedFiles) {
				cachedFile.delete();
			}
		}

		@SuppressWarnings("unused")
		public void readSettings() throws Exception {
			SettingsHandler.init();
			
			this.settings = SettingsHandler.getInstance().getSettings();
		}
		
		@SuppressWarnings("unused")
		public void initAsyncManager() {
			AsyncManager.init();
		}
		
		@SuppressWarnings("unused")
		public void initTvSerieManager() {
			TvSerieManager.init();
		}
		
		@SuppressWarnings("unused")
		public void initPeopleManager() {
			PeopleManager.init();
		}
		
		@SuppressWarnings("unused")
		public void initEnhancedLocaleMap() throws Exception {
			EnhancedLocaleMap.init();
		}
		
		@SuppressWarnings("unused")
		public void initTheTvDbManager() {
			if (StringUtils.isNotBlank(this.settings.getTheTvDbApiKey()) && this.settings.getTheTvDbEnabled()) {
				TheTvDbManager.getInstance().setApiKey(this.settings.getTheTvDbApiKey());
			}
			TheTvDbManager.getInstance().setEnabled(this.settings.getTheTvDbEnabled());
		}
		
		@SuppressWarnings("unused")
		public void initFanarttvManager() {
			if (StringUtils.isNotBlank(this.settings.getFanarttvApiKey()) && this.settings.getFanarttvEnabled()) {
				FanarttvManager.getInstance().setApiKey(this.settings.getFanarttvApiKey());
			}
			FanarttvManager.getInstance().setEnabled(this.settings.getFanarttvEnabled());
		}

		@SuppressWarnings("unused")
		public void initImdbManager() {
			ImdbManager.getInstance().setEnabled(this.settings.getImdbEnabled());
		}
		
		@SuppressWarnings("unused")
		public void initMyApiFilmsManager() {
			MyApiFilmsManager.getInstance().setEnabled(this.settings.getMyApiFilmsEnabled());
		}
		
		@SuppressWarnings("unused")
		public void initConnectionHandler() throws Exception {
			ConnectionHandler.init();
		}
		
		@SuppressWarnings("unused")
		public void loadTvSeries() {
			this.tvSeriesPathEntities = new ArrayList<>();
		}
		
		@SuppressWarnings("unused")
		public void loadMovies() {
			
		}
		
	}

	private static class LoaderSwingWorker extends SwingWorker<Result, Progress> implements WorkerProgressListener {

		private final LoaderWorker loaderWorker;
		private final Loader loader;

		private LoaderSwingWorker(Loader loader) {
			this.loaderWorker = new LoaderWorker();
			this.loader = loader;

			this.loaderWorker.addWorkerProgressListener(this);
		}

		@Override
		public void workerInit(String message, String submessage, Integer value) {
			publish(new Progress(Progress.Type.Init, message, submessage, value));
		}

		@Override
		public void workerUpdate(String message, String submessage, Integer value) {
			publish(new Progress(Progress.Type.Update, message, submessage, value));
		}

		@Override
		protected void done() {
			super.done();

			this.loader.notifyLoaded();
		}

		@Override
		protected Result doInBackground() throws Exception {
			return this.loaderWorker.work();
		}

		@Override
		protected void process(List<Progress> chunks) {
			for (Progress chunk : chunks) {
				if (chunk.getMessage() != null) {
					this.loader.progressMessage.setText(chunk.getMessage());
				}
				switch (chunk.getType()) {
				case Init:
					break;
				case Update:
					if (chunk.getValue() != null) {
						this.loader.progressBar.setValue(chunk.getValue());
					}
					break;
				}
			}
		}

	}

	private static class Result {
		
		private final List<TvSeriesPathEntity> tvSeriesPathEntities;

		private Result(List<TvSeriesPathEntity> tvSeriesPathEntities) {
			this.tvSeriesPathEntities = tvSeriesPathEntities;
		}
		
	}

}
