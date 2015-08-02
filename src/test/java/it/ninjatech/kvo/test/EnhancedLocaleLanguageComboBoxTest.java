package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.component.EnhancedLocaleLanguageComboBox;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.MemoryUtils;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebDialog;

public class EnhancedLocaleLanguageComboBoxTest extends WebDialog {

	private static final long serialVersionUID = -7942619983449449617L;

	public static void main(String[] args) throws Exception {
		MemoryUtils.printMemory("Start");
		WebLookAndFeel.install();
		SettingsHandler.init();
		AsyncManager.init();
		EnhancedLocaleMap.init();
		
		EnhancedLocaleLanguageComboBoxTest f = new EnhancedLocaleLanguageComboBoxTest();
		f.setVisible(true);
		MemoryUtils.printMemory("Closed");
		
		System.exit(0);
	}
	
	private EnhancedLocaleLanguageComboBoxTest() throws Exception {
		super();

		setModal(true);
		
		init();

		setSize(400, 100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void init() throws Exception {
		getContentPane().setBackground(Colors.BACKGROUND_INFO);
		
		EnhancedLocaleLanguageComboBox cb = new EnhancedLocaleLanguageComboBox(null);

		add(cb);
	}
	
}
