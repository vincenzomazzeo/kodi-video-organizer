package it.ninjatech.kvo.ui;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSerieImage;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.worker.TvSerieFetcher;
import it.ninjatech.kvo.worker.TvSerieFinder;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
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

	public static boolean existsLocalSeason(TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season) {
		boolean result = false;
		
		File seasonDirectory = getLocalSeasonFile(tvSeriePathEntity, season);
		result = seasonDirectory.exists() && seasonDirectory.isDirectory();
		
		return result;
	}
	
	public static File getLocalSeasonFile(TvSeriePathEntity tvSeriePathEntity, TvSerieSeason season) {
		return new File(tvSeriePathEntity.getPath(), String.format("Season %d", season.getNumber()));
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
	
	public static String getFirstAired(TvSerieEpisode tvSerieEpisode) {
		String result = "";

		if (tvSerieEpisode.getFirstAired() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			result = sdf.format(tvSerieEpisode.getFirstAired());
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
		return tvSeriePathEntity.hasExtraFanarts();
	}

	public static Set<String> getExtraFanarts(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getExtraFanarts();
	}

	public static Set<TvSerieImage> getFanarts(TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart) {
		Set<TvSerieImage> result = new LinkedHashSet<>();

		if (tvSeriePathEntity.getTvSerie() != null) {
			result.addAll(tvSeriePathEntity.getTvSerie().getTheTvDbFanart(fanart));
			result.addAll(tvSeriePathEntity.getTvSerie().getFanarttvFanart(fanart));
		}

		return result;
	}

	public static boolean hasFanarts(TvSeriePathEntity tvSeriePathEntity, TvSerieFanart fanart) {
		return tvSeriePathEntity.getTvSerie() != null ? tvSeriePathEntity.getTvSerie().hasFanarts(fanart) : false;
	}

	public static String getEpisodeName(TvSerieEpisode episode) {
		String result = null;

		DecimalFormat format = new DecimalFormat("00");
		result = String.format("%s - %s", format.format(episode.getNumber()), episode.getName());

		return result;
	}
	
	public static String getEpisodeRating(TvSerieEpisode tvSerieEpisode) {
		String result = null;
		
		result = tvSerieEpisode.getRating() != null ? tvSerieEpisode.getRating().toString() : "";
		
		return result;
	}
	
	public static String getEpisodeRatingCount(TvSerieEpisode tvSerieEpisode) {
		String result = null;
		
		result = tvSerieEpisode.getRatingCount() != null ? tvSerieEpisode.getRatingCount().toString() : "";
		
		return result;
	}
	
	public static Collection<String> getEpisodeDirectors(TvSerieEpisode tvSerieEpisode) {
		return tvSerieEpisode.getDirectors() != null ? tvSerieEpisode.getDirectors() : Collections.<String>emptySet();
	}
	
	public static Collection<String> getEpisodeWriters(TvSerieEpisode tvSerieEpisode) {
		return tvSerieEpisode.getWriters() != null ? tvSerieEpisode.getWriters() : Collections.<String>emptySet();
	}
	
	public static Collection<String> getEpisodeGuestStars(TvSerieEpisode tvSerieEpisode) {
		return tvSerieEpisode.getGuestStars() != null ? tvSerieEpisode.getGuestStars() : Collections.<String>emptySet();
	}

	public static Set<String> getVideoFiles(TvSeriePathEntity tvSeriePathEntity, Integer season) {
		return tvSeriePathEntity.getVideoFiles(season);
	}

	public static Set<String> getSubtitleFiles(TvSeriePathEntity tvSeriePathEntity, Integer season) {
		return tvSeriePathEntity.getSubtitleFiles(season);
	}

	private TvSerieUtils() {
	}

}
