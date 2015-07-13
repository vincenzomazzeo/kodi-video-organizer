package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.worker.TvSerieFetcher;
import it.ninjatech.kvo.worker.TvSerieFinder;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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

	public static String getTitle(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getName()) ?
				tvSeriePathEntity.getTvSerie().getName() : tvSeriePathEntity.getLabel();
	}

	public static String getYear(TvSeriePathEntity tvSeriePathEntity) {
		String result = "";

		if (tvSeriePathEntity.getTvSerie() != null && tvSeriePathEntity.getTvSerie().getFirstAired() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			result = sdf.format(tvSeriePathEntity.getTvSerie().getFirstAired());
		}

		return result;
	}

	public static String getRate(TvSeriePathEntity tvSeriePathEntity) {
		StringBuilder result = new StringBuilder();

		if (tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getRating())) {
			result.append(tvSeriePathEntity.getTvSerie().getRating());
			if (StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getRatingCount())) {
				result.append(" (").append(tvSeriePathEntity.getTvSerie().getRatingCount()).append(")");
			}
		}

		return result.toString();
	}

	public static String getGenre(TvSeriePathEntity tvSeriePathEntity) {
		String result = "";

		if (tvSeriePathEntity.getTvSerie() != null && !tvSeriePathEntity.getTvSerie().getGenres().isEmpty()) {
			result = StringUtils.join(tvSeriePathEntity.getTvSerie().getGenres(), ", ");
		}

		return result;
	}

	private TvSerieUtils() {
	}

}
