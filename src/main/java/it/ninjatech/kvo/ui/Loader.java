package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.ui.progressdialogworker.Progress;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.Utils;
import it.ninjatech.kvo.worker.AbstractWorker;
import it.ninjatech.kvo.worker.WorkerProgressListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
			this.loaderSwingWorker.get();
			setVisible(false);
			dispose();
			
			UI.build().setVisible(true);
			
			SwingUtilities.invokeLater(new PostStartNotificator());
		}
		catch (Exception e) {
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

	private static class LoaderWorker extends AbstractWorker<Void> {

		@Override
		public Void work() throws Exception {
			notifyUpdate("Checking working directory", 0);

			if (!Utils.getWorkingDirectory().exists()) {
				if (!Utils.getWorkingDirectory().mkdirs()) {
					throw new Exception("Failed to create working directory. Program will exit.");
				}
			}

			notifyUpdate("Reading settings", 10);
			SettingsHandler.init();

			notifyUpdate("Initializing Async Manager", 15);
			AsyncManager.init();
			
			notifyUpdate("Connecting to DB", 20);
			ConnectionHandler.init();

			notifyUpdate("Loading enhanced locale map", 50);
			EnhancedLocaleMap.init();

			if (StringUtils.isNotBlank(SettingsHandler.getInstance().getSettings().getTheTvDbApikey())) {
				notifyUpdate("Contacting TheTVDB", 70);
				TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApikey());
			}
			
			notifyUpdate("Done", 100);

			return null;
		}

	}

	private static class LoaderSwingWorker extends SwingWorker<Void, Progress> implements WorkerProgressListener {

		private final LoaderWorker loaderWorker;
		private final Loader loader;

		private LoaderSwingWorker(Loader loader) {
			this.loaderWorker = new LoaderWorker();
			this.loader = loader;

			this.loaderWorker.addWorkerProgressListener(this);
		}

		@Override
		public void workerInit(String message, Integer value) {
			publish(new Progress(Progress.Type.Init, message, value));
		}

		@Override
		public void workerUpdate(String message, Integer value) {
			publish(new Progress(Progress.Type.Update, message, value));
		}

		@Override
		protected void done() {
			super.done();

			this.loader.notifyLoaded();
		}

		@Override
		protected Void doInBackground() throws Exception {
			this.loaderWorker.work();

			return null;
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

}
