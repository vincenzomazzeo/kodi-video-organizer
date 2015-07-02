package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.worker.TvSerieFetcher;
import it.ninjatech.kvo.worker.TvSerieFinder;

import java.util.List;

public final class TvSerieUtils {

	public static List<TvSerie> searchFor(String name, EnhancedLocale language) {
		List<TvSerie> result = null;
		
		TvSerieFinder tvSerieFinder = new TvSerieFinder(name, language);

		IndeterminateProgressDialogWorker<List<TvSerie>> finder = new IndeterminateProgressDialogWorker<>(tvSerieFinder, "Searching for TV Serie");

		finder.start();
		try {
			result = finder.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		return result;
	}
	
	public static TvSerie fetch(TvSerie tvSerie) {
		TvSerie result = null;
		
		TvSerieFetcher tvSerieFetcher = new TvSerieFetcher(tvSerie);

		IndeterminateProgressDialogWorker<TvSerie> fetcher = new IndeterminateProgressDialogWorker<>(tvSerieFetcher, "Fetching data");

		fetcher.start();
		try {
			result = fetcher.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		return result;
	}
	
	
	private TvSerieUtils() {}
	
}
