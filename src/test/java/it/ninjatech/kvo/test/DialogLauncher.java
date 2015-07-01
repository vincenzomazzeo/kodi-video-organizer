package it.ninjatech.kvo.test;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.ui.tvserie.SearchTvSerieController;
import it.ninjatech.kvo.ui.tvserie.SearchTvSerieView;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

public class DialogLauncher {

	public static void main(String[] args) throws Exception {
		SettingsHandler.init();
		EnhancedLocaleMap.init();
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApikey());
		
//		ScrapersSettingsView view = new ScrapersSettingsView();
//		ScrapersSettingsController controller = new ScrapersSettingsController(view);
		
		SearchTvSerieView view = new SearchTvSerieView();
		SearchTvSerieController controller = new SearchTvSerieController(view);
		
//		ExceptionConsoleView view = new ExceptionConsoleView();
//		ExceptionConsoleController controller = new ExceptionConsoleController(view);
//		
//		view.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
//		
//		for (int i = 0; i < 50; i++) {
//    		try {
//    			String a = null;
//    			a.length();
//    		}
//    		catch (Exception e) {
//    			controller.notifyException(e);
//    		}
//		}
		
		view.setVisible(true);
	}
	
}
