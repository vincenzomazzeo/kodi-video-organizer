package it.ninjatech.kvo.tvserie;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieActor;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSerieFanart;
import it.ninjatech.kvo.tvserie.model.TvSerieImage;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.util.EnhancedLocaleMap;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.Utils;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public final class TvSerieHelper {

	public static final String EXTRAFANART = "extrafanart";
	public static final String SEASON = "season";
	private static final String FIRST_AIRED_DATE_FORMAT = "dd/MM/yyyy";
	private static final String YEAR_DATE_FORMAT = "yyyy";
	private static final String SEASON_POSTER_FILENAME = "season%s-poster.jpg";
	private static final String FULL_EPISODE_NAME_FORMAT = "%s - %s";
	private static final String EPISODE_NAME_FORMAT = "%s - %s";
	private static final String FS_EPISODE_NAME_FORMAT = "%s - (?<%s>[\\d]+) - .+";
	private static final String EPISODE_NUMBER_GROUP_NAME = "episodeNumber";

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
			SimpleDateFormat sdf = new SimpleDateFormat(FIRST_AIRED_DATE_FORMAT);
			result = sdf.format(tvSerie.getFirstAired());
		}

		return result;
	}
	
	public static String getYear(TvSerie tvSerie) {
		String result = "";

		if (tvSerie.getFirstAired() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(YEAR_DATE_FORMAT);
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
		result = String.format(SEASON_POSTER_FILENAME, df.format(season.getNumber()));

		return result;
	}

	// ***********
	// * Episode *
	// ***********

	public static String getEpisodeFileName(TvSerieEpisode episode) {
	    return (new File(episode.getFilename())).getName();
	}
	
	public static Set<String> getEpisodeSubtitleFileNames(TvSerieEpisode episode) {
	    Set<String> result = new TreeSet<>();
	    
	    for (String subtitleFilename : episode.getSubtitleFilenames()) {
	        result.add((new File(subtitleFilename)).getName());
	    }
        
        return result;
    }
	
	public static String getFirstAired(TvSerieEpisode episode) {
		String result = "";

		if (episode.getFirstAired() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(FIRST_AIRED_DATE_FORMAT);
			result = sdf.format(episode.getFirstAired());
		}

		return result;
	}

	public static String getFullEpisodeName(TvSerieEpisode episode) {
		return String.format(FULL_EPISODE_NAME_FORMAT, episode.getSeason().getTvSerie().getName(), getEpisodeName(episode));
	}

	public static String getEpisodeName(TvSerieEpisode episode) {
		String result = null;

		DecimalFormat format = new DecimalFormat("00");
		result = String.format(EPISODE_NAME_FORMAT, format.format(episode.getNumber()), episode.getName());

		return result;
	}
	
	public static boolean setEpisodeFilename(TvSerie tvSerie, File file) {
	    boolean result = false;
	    
	    Integer seasonNumber = Integer.valueOf(file.getParentFile().getName().toLowerCase().substring(SEASON.length() + 1));
	    String tvSerieNameNormalized = Utils.normalizeName(tvSerie.getName());
	    String regexp = String.format(FS_EPISODE_NAME_FORMAT, tvSerieNameNormalized, EPISODE_NUMBER_GROUP_NAME);
	    Matcher matcher = Pattern.compile(regexp).matcher(file.getName());
	    if (matcher.matches()) {
	        Integer episodeNumber = Integer.valueOf(matcher.group(EPISODE_NUMBER_GROUP_NAME));
	        
	        TvSerieSeason season = tvSerie.getSeason(seasonNumber);
	        if (season != null) {
	            TvSerieEpisode episode = season.getEpisode(episodeNumber);
	            if (episode != null) {
	                episode.setFilename(file.getAbsolutePath());
	                result = true;
	            }
	        }
	    }
	    
	    return result;
	}
	
	public static boolean addEpisodeSubtitleFilename(TvSerie tvSerie, File file) {
	    boolean result = false;
	    
	    Integer seasonNumber = Integer.valueOf(file.getParentFile().getName().toLowerCase().substring(SEASON.length() + 1));
	    String tvSerieNameNormalized = Utils.normalizeName(tvSerie.getName());
	    String regexp = String.format("%s - (?<n>[\\d]+) - .+(?<s>[\\.\\d]?)(?<l>[\\.a-z]{2}).+", tvSerieNameNormalized);
	    Matcher matcher = Pattern.compile(regexp).matcher(file.getName());
	    if (matcher.matches()) {
	        System.out.println("qui");
            /*
	        Integer episodeNumber = Integer.valueOf(matcher.group(EPISODE_NUMBER_GROUP_NAME));
            
            TvSerieSeason season = tvSerie.getSeason(seasonNumber);
            if (season != null) {
                TvSerieEpisode episode = season.getEpisode(episodeNumber);
                if (episode != null) {
                    episode.setFilename(file.getAbsolutePath());
                    result = true;
                }
            }
            */
        }
	    
	    // The Following - 01 - Inside the Following.2.it.srt
	    // The Following - 01 - Inside the Following.it.srt
	    
	    return result;
	}
	
	public static void main(String[] args) {
        TvSerie a = new TvSerie("", "ciccio", null);
        addEpisodeSubtitleFilename(a, new File("D:/ciccio/season 1/ciccio - 01 - prova.2.it.srt"));
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
