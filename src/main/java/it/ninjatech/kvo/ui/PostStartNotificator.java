package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.util.Labels;

public class PostStartNotificator implements Runnable {

	@Override
	public void run() {
		if (!TheTvDbManager.getInstance().isActive()) {
			UI.get().getToolBar().showNotificationForScraperSettings(Labels.NOTIFICATION_THE_TV_DB_NOT_CONFIGURED);
		}
	}

}
