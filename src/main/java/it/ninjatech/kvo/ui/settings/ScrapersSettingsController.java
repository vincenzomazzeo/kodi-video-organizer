package it.ninjatech.kvo.ui.settings;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.utils.EnhancedLocaleMap;
import it.ninjatech.kvo.worker.AbstractWorker;

import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;

import com.alee.laf.optionpane.WebOptionPane;

public class ScrapersSettingsController {

	private final ScrapersSettingsView view;

	public ScrapersSettingsController(ScrapersSettingsView view) {
		this.view = view;

		this.view.setController(this);

		TheTvDbManager theTvDbManager = TheTvDbManager.getInstance();
		if (theTvDbManager.isActive()) {
			this.view.setTheTvDbApikey(SettingsHandler.getInstance().getSettings().getTheTvDbApikey());
			this.view.setTheTvDbLanguages(theTvDbManager.getLanguages());
			this.view.selectTheTvDbLanguage(EnhancedLocaleMap.getByLanguage(SettingsHandler.getInstance().getSettings().getTheTvDbPreferredLanguage()));
		}
	}

	protected void notifyConfirm() {
		ConfirmWorker confirmWorker = new ConfirmWorker(this.view.getTheTvDbApikey(), this.view.getTheTvDbLanguage());

		new IndeterminateProgressDialogWorker<>(confirmWorker, "Storing Scrapers Settings").start();

		this.view.setVisible(false);
	}

	protected void notifyTheTvDbSecret() {
		String apikey = (String)WebOptionPane.showInputDialog(this.view, "Insert TheTVDB API Key", "TheTVDB", JOptionPane.QUESTION_MESSAGE, ImageRetriever.retrieveApikey(), null, null);
		if (apikey != null) {
			if (StringUtils.isEmpty(apikey)) {
				this.view.clearTheTvDbApikey();
			}
			else {
				TheTvDbManagerWorker theTvDbManagerWorker = new TheTvDbManagerWorker(apikey);

				IndeterminateProgressDialogWorker<List<EnhancedLocale>> worker = new IndeterminateProgressDialogWorker<>(theTvDbManagerWorker, "Contacting TheTVDB");

				worker.start();
				try {
					List<EnhancedLocale> languages = worker.get();
					if (languages != null) {
						this.view.setTheTvDbApikey(apikey);
						this.view.setTheTvDbLanguages(languages);
					}
					else {
						WebOptionPane.showMessageDialog(this.view, "Wrong API Key", "TheTVDB", WebOptionPane.INFORMATION_MESSAGE, ImageRetriever.retrieveApikey());
					}
				}
				catch (Exception e) {
					// TODO gestire
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

	private static final class ConfirmWorker extends AbstractWorker<Void> {

		private final String theTvDbApikey;
		private final EnhancedLocale theTvDbPreferredLanguage;

		protected ConfirmWorker(String theTvDbApikey, EnhancedLocale theTvDbPreferredLanguage) {
			this.theTvDbApikey = theTvDbApikey;
			this.theTvDbPreferredLanguage = theTvDbPreferredLanguage;
		}

		@Override
		public Void work() throws Exception {
			if (StringUtils.isNotBlank(this.theTvDbApikey)) {
				SettingsHandler.getInstance().getSettings().setTheTvDbApiKey(this.theTvDbApikey);
				SettingsHandler.getInstance().getSettings().setTheTvDbPreferredLanguage(this.theTvDbPreferredLanguage.getLanguageCode());
				SettingsHandler.getInstance().store();

				TheTvDbManager.getInstance().setApiKey(this.theTvDbApikey);
			}
			else {
				SettingsHandler.getInstance().getSettings().setTheTvDbApiKey(null);
				SettingsHandler.getInstance().getSettings().setTheTvDbPreferredLanguage(null);
				SettingsHandler.getInstance().store();
				
				TheTvDbManager.getInstance().deactivate();
			}

			return null;
		}

	}

}
