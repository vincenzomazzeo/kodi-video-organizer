package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSerieImage;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.worker.TvSerieFetcher;
import it.ninjatech.kvo.worker.TvSerieFinder;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

	public static EnhancedLocale getLanguage(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && tvSeriePathEntity.getTvSerie().getLanguage() != null ? tvSeriePathEntity.getTvSerie().getLanguage() : EnhancedLocaleMap.getEmptyLocale();
	}
	
	public static String getTitle(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getName()) ?
				tvSeriePathEntity.getTvSerie().getName() : tvSeriePathEntity.getLabel();
	}

	public static String getFirstAired(TvSeriePathEntity tvSeriePathEntity) {
		String result = "";

		if (tvSeriePathEntity.getTvSerie() != null && tvSeriePathEntity.getTvSerie().getFirstAired() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			result = sdf.format(tvSeriePathEntity.getTvSerie().getFirstAired());
		}

		return result;
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
	
	public static String getOverview(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getOverview()) ? tvSeriePathEntity.getTvSerie().getOverview() : "";
	}
	
	public static String getStatus(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getStatus()) ? tvSeriePathEntity.getTvSerie().getStatus() : "";
	}
	
	public static String getRating(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getRating()) ? tvSeriePathEntity.getTvSerie().getRating() : "";
	}
	
	public static String getRatingCount(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getRatingCount()) ? tvSeriePathEntity.getTvSerie().getRatingCount() : "";
	}
	
	public static String getNetwork(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getNetwork()) ? tvSeriePathEntity.getTvSerie().getNetwork() : "";
	}

	public static String getContentRating(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getContentRating()) ? tvSeriePathEntity.getTvSerie().getContentRating() : "";
	}
	
	public static String getImdbId(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getImdbId()) ? tvSeriePathEntity.getTvSerie().getImdbId() : "";
	}
	
	public static boolean hasExtraFanarts(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null ? tvSeriePathEntity.getTvSerie().hasExtraFanarts() : false;
	}
	
	public static Set<String> getExtraFanarts(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null ? tvSeriePathEntity.getTvSerie().getExtraFanarts() : Collections.<String>emptySet();
	}
	
	public static Set<TvSerieImage> getTheTvDbFanarts(TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart) {
		return tvSeriePathEntity.getTvSerie() != null ? tvSeriePathEntity.getTvSerie().getTheTvDbFanart(fanart) : Collections.<TvSerieImage>emptySet();
	}
	
	public static Set<TvSerieImage> getFanarttvFanarts(TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart) {
		return tvSeriePathEntity.getTvSerie() != null ? tvSeriePathEntity.getTvSerie().getFanarttvFanart(fanart) : Collections.<TvSerieImage>emptySet();
	}

	private TvSerieUtils() {
	}

}

