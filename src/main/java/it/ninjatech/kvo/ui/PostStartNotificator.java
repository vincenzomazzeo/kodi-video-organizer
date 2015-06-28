package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;

public class PostStartNotificator implements Runnable {

	@Override
	public void run() {
		if (!TheTvDbManager.getInstance().isActive()) {
			UI.get().getToolBar().showNotificationForScraperSettings("<html><b>TheTVDB</b> scraper is <u>not</u> configured. Go to <b>Scrapers Settings</b> to configure it</html>");
		}
	}

}
