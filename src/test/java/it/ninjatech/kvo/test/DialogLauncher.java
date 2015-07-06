package it.ninjatech.kvo.test;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchMultiResultController;
import it.ninjatech.kvo.ui.tvserie.TvSerieSearchMultiResultView;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.util.List;

import com.alee.laf.rootpane.WebDialog;

public class DialogLauncher {

	public static void main(String[] args) throws Exception {
		SettingsHandler.init();
		EnhancedLocaleMap.init();
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApikey());
		
//		ScrapersSettingsView view = new ScrapersSettingsView();
//		ScrapersSettingsController controller = new ScrapersSettingsController(view);
		
//		TvSerieSearchView view = new TvSerieSearchView();
//		TvSerieSearchController controller = new TvSerieSearchController(view, null);
		
		List<TvSerie> tvSeries = TheTvDbManager.getInstance().search("highlander", EnhancedLocaleMap.getEmptyLocale());
		TvSerieSearchMultiResultController controller = new TvSerieSearchMultiResultController(tvSeries, null);
		TvSerieSearchMultiResultView view = controller.getView();
		
//		ExceptionConsoleView view = new ExceptionConsoleView();
//		ExceptionConsoleController controller = new ExceptionConsoleController(view);
		
		view.setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
		
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
