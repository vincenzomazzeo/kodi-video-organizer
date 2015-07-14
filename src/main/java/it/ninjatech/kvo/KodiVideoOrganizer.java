package it.ninjatech.kvo;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.ui.Loader;
import it.ninjatech.kvo.ui.transictioneffect.TransictionEffectExecutor;

import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

public class KodiVideoOrganizer {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				WebLookAndFeel.install();
				
				Loader loader = new Loader();
				loader.load();
				loader.setVisible(true);
			}
		});
	}

	public static void exit() {
		ConnectionHandler.shutdown();
		TransictionEffectExecutor.shutdown();
		AsyncManager.shutdown();

		System.exit(0);
	}

	private KodiVideoOrganizer() {
	}

}
