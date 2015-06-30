package it.ninjatech.kvo.test;

import it.ninjatech.kvo.ui.exception.ExceptionController;
import it.ninjatech.kvo.ui.exception.ExceptionView;

import com.alee.laf.rootpane.WebDialog;

public class DialogLauncher {

	public static void main(String[] args) throws Exception {
//		EnhancedLocaleMap.init();
//		
//		ScrapersSettingsView view = new ScrapersSettingsView();
//		ScrapersSettingsController controller = new ScrapersSettingsController(view);
		
		ExceptionView view = new ExceptionView();
		ExceptionController controller = new ExceptionController(view);
		
		view.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		
		for (int i = 0; i < 50; i++) {
    		try {
    			String a = null;
    			a.length();
    		}
    		catch (Exception e) {
    			controller.notifyException(e);
    		}
		}
		
		view.setVisible(true);
	}
	
}
