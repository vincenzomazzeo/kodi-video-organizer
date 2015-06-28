package it.ninjatech.kvo.test;

import com.alee.laf.rootpane.WebDialog;

import it.ninjatech.kvo.ui.settings.ScrapersSettingsController;
import it.ninjatech.kvo.ui.settings.ScrapersSettingsView;
import it.ninjatech.kvo.utils.EnhancedLocaleMap;

public class DialogLauncher {

	public static void main(String[] args) throws Exception {
		EnhancedLocaleMap.init();
		
		ScrapersSettingsView view = new ScrapersSettingsView();
		ScrapersSettingsController controller = new ScrapersSettingsController(view);
		
		view.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		
		view.setVisible(true);
	}
	
}
