package it.ninjatech.kvo;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.utils.Utils;

import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

public class KodiVideoOrganizer {

	public static void main(String[] args) {
		try {
			if (!Utils.getWorkingDirectory().exists()) {
				if (!Utils.getWorkingDirectory().mkdirs()) {
					// TODO gestire?
					throw new Exception("Failed to create data directory");
				}
			}
			
			SettingsHandler.init();
			
			ConnectionHandler.init();

			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					WebLookAndFeel.install();

					UI.build().setVisible(true);
				}
			});
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
