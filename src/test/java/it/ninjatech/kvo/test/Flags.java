package it.ninjatech.kvo.test;

import it.ninjatech.kvo.ui.FlagMap;

import java.awt.FlowLayout;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Flags {

	public static void main(String[] args) throws Exception {
		FlagMap.init();
		
		JFrame frame = new JFrame();
		JLabel lblimage = new JLabel(FlagMap.getFlag(Locale.CANADA.getCountry()));
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(lblimage);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}
