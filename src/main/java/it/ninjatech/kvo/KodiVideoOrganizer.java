package it.ninjatech.kvo;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.ui.UI;

import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

public class KodiVideoOrganizer {

	public static void main(String[] args) {
		try {
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
