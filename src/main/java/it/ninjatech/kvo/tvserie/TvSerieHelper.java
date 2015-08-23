package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerie;
import it.ninjatech.kvo.model.TvSerieActor;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSerieFanart;
import it.ninjatech.kvo.model.TvSerieImage;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.ui.progressdialogworker.IndeterminateProgressDialogWorker;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.Labels;
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

public final class TvSerieHelper {

	public static final String EXTRAFANART = "extrafanart";
	public static final String SEASON = "season";

	public static List<TvSerie> searchFor(String name, EnhancedLocale language) {
		List<TvSerie> result = null;

		TvSerieFinder tvSerieFinder = new TvSerieFinder(name, language);

		result = IndeterminateProgressDialogWorker.show(tvSerieFinder, Labels.SEARCHING_FOR_TV_SERIE);

		return result;
	}

	public static TvSerie fetch(TvSerie tvSerie) {
		TvSerie result = null;

		TvSerieFetcher tvSerieFetcher = new TvSerieFetcher(tvSerie);

		result = IndeterminateProgressDialogWorker.show(tvSerieFetcher, Labels.FETCHING_DATA);

		return result;
	}

	// **************
	// * PathEntity *
	// **************
	public static File getExtrafanartPath(TvSeriePathEntity tvSeriePathEntity) {
		return new File(tvSeriePathEntity.getPath(), EXTRAFANART);
	}
	
	public static String getTitle(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getTvSerie() != null && StringUtils.isNotBlank(tvSeriePathEntity.getTvSerie().getName()) ?
				tvSeriePathEntity.getTvSerie().getName() : tvSeriePathEntity.getLabel();
	}
	
	public static boolean hasExtraFanarts(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.hasExtraFanarts();
	}

	public static Set<String> getExtraFanarts(TvSeriePathEntity tvSeriePathEntity) {
		return tvSeriePathEntity.getExtraFanarts();
	}
	
	public static Set<String> getVideoFilesNotReferenced(TvSeriePathEntity tvSeriePathEntity, Integer season) {
		return tvSeriePathEntity.getVideoFilesNotReferenced(season);
	}

	public static Set<String> getSubtitleFilesNotReferenced(TvSeriePathEntity tvSeriePathEntity, Integer season) {
		return tvSeriePathEntity.getSubtitleFilesNotReferenced(season);
	}
	
	// ***********
	// * TvSerie *
	// ***********
	public static Collection<TvSerieSeason> getSeasons(TvSerie tvSerie) {
		return tvSerie.getSeasons();
	}

	public static Collection<TvSerieActor> getActors(TvSerie tvSerie) {
		return tvSerie.getActors();
	}

	public static EnhancedLocale getLanguage(TvSerie tvSerie) {
		return tvSerie.getLanguage() != null ? tvSerie.getLanguage() : EnhancedLocaleMap.getEmptyLocale();
	}

	public static String getFirstAired(TvSerie tvSerie) {
		String result = "";

		if (tvSerie.getFirstAired() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			result = sdf.format(tvSerie.getFirstAired());
		}

		return result;
	}
	
	public static String getYear(TvSerie tvSerie) {
		String result = "";

		if (tvSerie.getFirstAired() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			result = sdf.format(tvSerie.getFirstAired());
		}

		return result;
	}

	public static String getRate(TvSerie tvSerie) {
		StringBuilder result = new StringBuilder();

		if (StringUtils.isNotBlank(tvSerie.getRating())) {
			result.append(tvSerie.getRating());
			if (StringUtils.isNotBlank(tvSerie.getRatingCount())) {
				result.append(" (").append(tvSerie.getRatingCount()).append(")");
			}
		}

		return result.toString();
	}

	public static String getGenre(TvSerie tvSerie) {
		String result = "";

		if (!tvSerie.getGenres().isEmpty()) {
			result = StringUtils.join(tvSerie.getGenres(), ", ");
		}

		return result;
	}

	public static String getOverview(TvSerie tvSerie) {
		return StringUtils.isNotBlank(tvSerie.getOverview()) ? tvSerie.getOverview() : "";
	}

	public static String getStatus(TvSerie tvSerie) {
		return StringUtils.isNotBlank(tvSerie.getStatus()) ? tvSerie.getStatus() : "";
	}

	public static String getRating(TvSerie tvSerie) {
		return StringUtils.isNotBlank(tvSerie.getRating()) ? tvSerie.getRating() : "";
	}

	public static String getRatingCount(TvSerie tvSerie) {
		return StringUtils.isNotBlank(tvSerie.getRatingCount()) ? tvSerie.getRatingCount() : "";
	}

	public static String getNetwork(TvSerie tvSerie) {
		return StringUtils.isNotBlank(tvSerie.getNetwork()) ? tvSerie.getNetwork() : "";
	}

	public static String getContentRating(TvSerie tvSerie) {
		return StringUtils.isNotBlank(tvSerie.getContentRating()) ? tvSerie.getContentRating() : "";
	}

	public static String getImdbId(TvSerie tvSerie) {
		return StringUtils.isNotBlank(tvSerie.getImdbId()) ? tvSerie.getImdbId() : "";
	}

	public static Set<TvSerieImage> getFanarts(TvSerie tvSerie, TvSerieFanart fanart) {
		Set<TvSerieImage> result = new LinkedHashSet<>();

		result.addAll(tvSerie.getTheTvDbFanart(fanart));
		result.addAll(tvSerie.getFanarttvFanart(fanart));

		return result;
	}

	public static boolean hasFanarts(TvSerie tvSerie, TvSerieFanart fanart) {
		return tvSerie.hasFanarts(fanart);
	}
	
	// **********
	// * Season *
	// **********
	public static boolean existsLocalSeason(TvSerieSeason season) {
		boolean result = false;

		File seasonDirectory = getLocalSeasonPath(season);
		result = seasonDirectory.exists() && seasonDirectory.isDirectory();

		return result;
	}

	public static File getLocalSeasonPath(TvSerieSeason season) {
		return new File(season.getTvSerie().getTvSeriePathEntity().getPath(), Labels.getTvSerieSeason(season));
	}

	public static boolean isLocalSeasonComplete(TvSerieSeason season) {
		boolean result = true;

		for (TvSerieEpisode episode : season.getEpisodes()) {
			if (episode.getFilename() == null) {
				result = false;
				break;
			}
		}

		return result;
	}

	public static boolean existsLocalSeasonPoster(TvSerieSeason season) {
		boolean result = false;

		File poster = new File(season.getTvSerie().getTvSeriePathEntity().getPath(), getSeasonPosterFilename(season));
		result = poster.exists() && poster.isFile();

		return result;
	}

	public static String getSeasonPosterFilename(TvSerieSeason season) {
		String result = null;

		DecimalFormat df = new DecimalFormat("00");
		result = String.format("season%s-poster.jpg", df.format(season.getNumber()));

		return result;
	}

	// ***********
	// * Episode *
	// ***********

	public static String getFirstAired(TvSerieEpisode tvSerieEpisode) {
		String result = "";

		if (tvSerieEpisode.getFirstAired() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			result = sdf.format(tvSerieEpisode.getFirstAired());
		}

		return result;
	}

	public static String getFullEpisodeName(TvSerieEpisode episode) {
		return String.format("%s - %s", episode.getSeason().getTvSerie().getName(), getEpisodeName(episode));
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
		return tvSerieEpisode.getDirectors() != null ? tvSerieEpisode.getDirectors() : Collections.<String> emptySet();
	}

	public static Collection<String> getEpisodeWriters(TvSerieEpisode tvSerieEpisode) {
		return tvSerieEpisode.getWriters() != null ? tvSerieEpisode.getWriters() : Collections.<String> emptySet();
	}

	public static Collection<String> getEpisodeGuestStars(TvSerieEpisode tvSerieEpisode) {
		return tvSerieEpisode.getGuestStars() != null ? tvSerieEpisode.getGuestStars() : Collections.<String> emptySet();
	}

	private TvSerieHelper() {
	}

}
