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

		IndeterminateProgressDialogWorker<List<TvSerie>> worker = new IndeterminateProgressDialogWorker<>(tvSerieFinder, "Searching for TV Serie");

		worker.start();
		try {
			result = worker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		return result;
	}
	
	public static TvSerie fetch(TvSerie tvSerie) {
		TvSerie result = null;
		
		TvSerieFetcher tvSerieFetcher = new TvSerieFetcher(tvSerie);

		IndeterminateProgressDialogWorker<TvSerie> worker = new IndeterminateProgressDialogWorker<>(tvSerieFetcher, "Fetching data");

		worker.start();
		try {
			result = worker.get();
		}
		catch (Exception e) {
			UI.get().notifyException(e);
		}
		
		return result;
	}
	
	
	private TvSerieUtils() {}
	
}
