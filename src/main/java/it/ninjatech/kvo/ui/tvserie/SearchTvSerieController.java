package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.configuration.SettingsHandler;
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

public class SearchTvSerieController {

	private final SearchTvSerieView view;

	public SearchTvSerieController(SearchTvSerieView view) {
		this.view = view;

		this.view.setController(this);
	}

	protected void notifySearch() {
//		ConfirmWorker confirmWorker = new ConfirmWorker(this.view.getTheTvDbApikey(), this.view.getTheTvDbLanguage());
//
//		new IndeterminateProgressDialogWorker<>(confirmWorker, "Storing Scrapers Settings").start();
//
//		this.view.setVisible(false);
	}

//	private static final class TheTvDbManagerWorker extends AbstractWorker<List<EnhancedLocale>> {
//
//		private final String apikey;
//
//		protected TheTvDbManagerWorker(String apikey) {
//			this.apikey = apikey;
//		}
//
//		@Override
//		public List<EnhancedLocale> work() throws Exception {
//			List<EnhancedLocale> result = TheTvDbManager.getInstance().checkApiKey(this.apikey);
//
//			if (result != null) {
//				Collections.sort(result, EnhancedLocale.languageComparator());
//			}
//
//			return result;
//		}
//
//	}
//
//	private static final class ConfirmWorker extends AbstractWorker<Void> {
//
//		private final String theTvDbApikey;
//		private final EnhancedLocale theTvDbPreferredLanguage;
//
//		protected ConfirmWorker(String theTvDbApikey, EnhancedLocale theTvDbPreferredLanguage) {
//			this.theTvDbApikey = theTvDbApikey;
//			this.theTvDbPreferredLanguage = theTvDbPreferredLanguage;
//		}
//
//		@Override
//		public Void work() throws Exception {
//			if (StringUtils.isNotBlank(this.theTvDbApikey)) {
//				SettingsHandler.getInstance().getSettings().setTheTvDbApiKey(this.theTvDbApikey);
//				SettingsHandler.getInstance().getSettings().setTheTvDbPreferredLanguage(this.theTvDbPreferredLanguage.getLanguageCode());
//				SettingsHandler.getInstance().store();
//
//				TheTvDbManager.getInstance().setApiKey(this.theTvDbApikey);
//			}
//			else {
//				SettingsHandler.getInstance().getSettings().setTheTvDbApiKey(null);
//				SettingsHandler.getInstance().getSettings().setTheTvDbPreferredLanguage(null);
//				SettingsHandler.getInstance().store();
//				
//				TheTvDbManager.getInstance().deactivate();
//			}
//
//			return null;
//		}
//
//	}

}
