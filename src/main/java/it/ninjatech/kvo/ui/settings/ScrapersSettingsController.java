package it.ninjatech.kvo.ui.settings;

import it.ninjatech.kvo.configuration.Settings;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.worker.AbstractWorker;

import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.alee.laf.optionpane.WebOptionPane;

public class ScrapersSettingsController {

	private final ScrapersSettingsView view;

	public ScrapersSettingsController() {
		this.view = new ScrapersSettingsView(this);

		Settings settings = SettingsHandler.getInstance().getSettings();
		
		this.view.setTheTvDbEnabled(settings.getTheTvDbEnabled());
		TheTvDbManager theTvDbManager = TheTvDbManager.getInstance();
		if (StringUtils.isNotBlank(settings.getTheTvDbApiKey())) {
			this.view.setTheTvDbApiKey(settings.getTheTvDbApiKey());
			this.view.setTheTvDbLanguages(theTvDbManager.getLanguages());
			this.view.selectTheTvDbLanguage(EnhancedLocaleMap.getByLanguage(settings.getTheTvDbPreferredLanguage()));
		}
		
		this.view.setFanarttvEnabled(settings.getFanarttvEnabled());
		if (StringUtils.isNotBlank(settings.getFanarttvApiKey())) {
			this.view.setFanarttvApiKey(settings.getFanarttvApiKey());
		}
		
		this.view.setImdbEnabled(settings.getImdbEnabled());
	}

	public ScrapersSettingsView getView() {
		return this.view;
	}

	protected void notifyConfirm() {
		ConfirmWorker confirmWorker = new ConfirmWorker(this.view.isTheTvDbEnabled(), this.view.getTheTvDbApiKey(), this.view.getTheTvDbLanguage(),
		                                                this.view.isFanarttvEnabled(), this.view.getFanarttvApiKey(),
		                                                this.view.isImdbEnabled());

		new IndeterminateProgressDialogWorker<>(confirmWorker, "Storing Scrapers Settings").start();

		this.view.setVisible(false);
	}

	protected void notifyTheTvDbSecret() {
		String apikey = (String)WebOptionPane.showInputDialog(this.view, "Insert TheTVDB API Key", "TheTVDB", JOptionPane.QUESTION_MESSAGE, ImageRetriever.retrieveApikey(), null, null);
		if (apikey != null) {
			if (StringUtils.isEmpty(apikey)) {
				this.view.clearTheTvDbApiKey();
			}
			else {
				TheTvDbManagerWorker theTvDbManagerWorker = new TheTvDbManagerWorker(apikey);

				IndeterminateProgressDialogWorker<List<EnhancedLocale>> worker = new IndeterminateProgressDialogWorker<>(theTvDbManagerWorker, "Contacting TheTVDB");

				worker.start();
				try {
					List<EnhancedLocale> languages = worker.get();
					if (languages != null) {
						this.view.setTheTvDbApiKey(apikey);
						this.view.setTheTvDbLanguages(languages);
					}
					else {
						WebOptionPane.showMessageDialog(this.view, "Wrong API Key", "TheTVDB", WebOptionPane.INFORMATION_MESSAGE, ImageRetriever.retrieveApikey());
					}
				}
				catch (Exception e) {
					UI.get().notifyException(e);
				}
			}
		}
	}
	
	protected void notifyFanarttvSecret() {
		String apikey = (String)WebOptionPane.showInputDialog(this.view, "Insert Fanart.tv API Key", "Fanart.tv", JOptionPane.QUESTION_MESSAGE, ImageRetriever.retrieveApikey(), null, null);
		if (apikey != null) {
			if (StringUtils.isEmpty(apikey)) {
				this.view.clearTheTvDbApiKey();
			}
			else {
				FanarttvManagerWorker fanarttvManagerWorker = new FanarttvManagerWorker(apikey);

				IndeterminateProgressDialogWorker<Boolean> worker = new IndeterminateProgressDialogWorker<>(fanarttvManagerWorker, "Contacting Fanart.tv");

				worker.start();
				try {
					Boolean checked = worker.get();
					if (checked) {
						this.view.setFanarttvApiKey(apikey);
					}
					else {
						WebOptionPane.showMessageDialog(this.view, "Wrong API Key", "Fanart.tv", WebOptionPane.INFORMATION_MESSAGE, ImageRetriever.retrieveApikey());
					}
				}
				catch (Exception e) {
					UI.get().notifyException(e);
				}
			}
		}
	}
	
	private static final class TheTvDbManagerWorker extends AbstractWorker<List<EnhancedLocale>> {

		private final String apikey;

		protected TheTvDbManagerWorker(String apikey) {
			this.apikey = apikey;
		}

		@Override
		public List<EnhancedLocale> work() throws Exception {
			List<EnhancedLocale> result = TheTvDbManager.getInstance().checkApiKey(this.apikey);

			if (result != null) {
				Collections.sort(result, EnhancedLocale.languageComparator());
			}

			return result;
		}

	}
	
	private static final class FanarttvManagerWorker extends AbstractWorker<Boolean> {

		private final String apikey;

		protected FanarttvManagerWorker(String apikey) {
			this.apikey = apikey;
		}

		@Override
		public Boolean work() throws Exception {
			return FanarttvManager.getInstance().checkApiKey(this.apikey);
		}

	}

	private static final class ConfirmWorker extends AbstractWorker<Void> {

		private final boolean theTvDbEnabled;
		private final String theTvDbApiKey;
		private final EnhancedLocale theTvDbPreferredLanguage;
		private final boolean fanarttvEnabled;
		private final String fanarttvApiKey;
		private final boolean imdbEnabled;

		protected ConfirmWorker(boolean theTvDbEnabled, String theTvDbApiKey, EnhancedLocale theTvDbPreferredLanguage,
		                        boolean fanarttvEnabled, String fanarttvApiKey,
		                        boolean imdbEnabled) {
			this.theTvDbEnabled = theTvDbEnabled;
			this.theTvDbApiKey = theTvDbApiKey;
			this.theTvDbPreferredLanguage = theTvDbPreferredLanguage;
			this.fanarttvEnabled = fanarttvEnabled;
			this.fanarttvApiKey = fanarttvApiKey;
			this.imdbEnabled = imdbEnabled;
		}

		@Override
		public Void work() throws Exception {
			Settings settings = SettingsHandler.getInstance().getSettings();
			
			settings.setTheTvDbEnabled(this.theTvDbEnabled);
			if (StringUtils.isNotBlank(this.theTvDbApiKey)) {
				settings.setTheTvDbApiKey(this.theTvDbApiKey);
				settings.setTheTvDbPreferredLanguage(this.theTvDbPreferredLanguage.getLanguageCode());

				TheTvDbManager.getInstance().setApiKey(this.theTvDbApiKey);
			}
			else {
				settings.setTheTvDbApiKey(null);
				settings.setTheTvDbPreferredLanguage(null);
				
				TheTvDbManager.getInstance().deactivate();
			}
			
			settings.setFanarttvEnabled(this.fanarttvEnabled);
			if (StringUtils.isNotBlank(this.fanarttvApiKey)) {
				settings.setFanarttvApiKey(this.fanarttvApiKey);

				FanarttvManager.getInstance().setApiKey(this.fanarttvApiKey);
			}
			else {
				settings.setFanarttvApiKey(null);
				
				FanarttvManager.getInstance().deactivate();
			}
			
			settings.setImdbEnabled(this.imdbEnabled);
			ImdbManager.getInstance().setEnabled(this.imdbEnabled);

			SettingsHandler.getInstance().store();

			return null;
		}

	}

}
