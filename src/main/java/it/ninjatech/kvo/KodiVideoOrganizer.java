package it.ninjatech.kvo;

import it.ninjatech.kvo.ui.UI;

import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;

public class KodiVideoOrganizer {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				WebLookAndFeel.install();
				
				UI.build().setVisible(true);
			}});
	}
	
	private KodiVideoOrganizer() {}
	
}
