package it.ninjatech.kvo;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.ui.PostStartNotificator;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.utils.EnhancedLocaleMap;
import it.ninjatech.kvo.utils.Utils;

import javax.swing.SwingUtilities;

import org.apache.commons.lang3.StringUtils;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.optionpane.WebOptionPane;

public class KodiVideoOrganizer {

	public static void main(String[] args) {
		try {
			if (!Utils.getWorkingDirectory().exists()) {
				if (!Utils.getWorkingDirectory().mkdirs()) {
					WebOptionPane.showMessageDialog(null, "Failed to create working directory. Program will exit.", "Error", WebOptionPane.ERROR_MESSAGE);
					System.exit(-1);
				}
			}

			SettingsHandler.init();

			ConnectionHandler.init();

			EnhancedLocaleMap.init();

			if (StringUtils.isNotBlank(SettingsHandler.getInstance().getSettings().getTheTvDbApikey())) {
				TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApikey());
			}

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					WebLookAndFeel.install();

					UI.build().setVisible(true);
				}
			});
			
			SwingUtilities.invokeLater(new PostStartNotificator());
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void exit() {
		ConnectionHandler.shutdown();

		System.exit(0);
	}

	private KodiVideoOrganizer() {
	}

}
