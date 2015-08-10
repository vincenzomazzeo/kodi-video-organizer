package it.ninjatech.kvo.test;

import it.ninjatech.kvo.async.AsyncManager;
import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.connector.fanarttv.FanarttvManager;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.myapifilms.MyApiFilmsManager;
import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.ui.component.MessageDialog;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebDialog;

public class DialogLauncher {

	public static void main(String[] args) throws Exception {
		WebLookAndFeel.install();
		SettingsHandler.init();
		AsyncManager.init();
		EnhancedLocaleMap.init();
		TheTvDbManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getTheTvDbApiKey());
		FanarttvManager.getInstance().setApiKey(SettingsHandler.getInstance().getSettings().getFanarttvApiKey());
		ImdbManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getImdbEnabled());
		MyApiFilmsManager.getInstance().setEnabled(SettingsHandler.getInstance().getSettings().getMyApiFilmsEnabled());
		
//		ScrapersSettingsController controller = new ScrapersSettingsController();
//		ScrapersSettingsView view = controller.getView();
		
//		TvSerieSearchView view = new TvSerieSearchView();
//		TvSerieSearchController controller = new TvSerieSearchController(view, null);
		
//		List<TvSerie> tvSeries = TheTvDbManager.getInstance().search("highlander", EnhancedLocaleMap.getEmptyLocale());
//		TvSerieSearchMultiResultController controller = new TvSerieSearchMultiResultController(tvSeries, null);
//		TvSerieSearchMultiResultView view = controller.getView();
		
//		ExceptionConsoleView view = new ExceptionConsoleView();
//		ExceptionConsoleController controller = new ExceptionConsoleController(view);
		
//		ProgressDialog view = ProgressDialog.getInstance("Tv Serie");
		MessageDialog view = MessageDialog.getInstance("Prova", "Ciccio", MessageDialog.Type.Question);
		
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
		System.out.println(view.isConfirmed());
		
		System.exit(0);
	}
	
}
