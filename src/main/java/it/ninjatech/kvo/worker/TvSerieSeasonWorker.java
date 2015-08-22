package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.util.Labels;
import it.ninjatech.kvo.util.TvSerieUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

public class TvSerieSeasonWorker extends AbstractWorker<Void> {

	private final TvSerieSeason season;
	private final Map<TvSerieEpisode, String> videoEpisodeMap;
	private final Map<TvSerieEpisode, Map<String, EnhancedLocale>> subtitleEpisodeMap;
	private File seasonImage;

	public TvSerieSeasonWorker(TvSerieSeason season,
							   Map<TvSerieEpisode, String> videoEpisodeMap, Map<TvSerieEpisode, Map<String, EnhancedLocale>> subtitleEpisodeMap, File seasonImage) {
		this.season = season;
		this.videoEpisodeMap = videoEpisodeMap;
		this.subtitleEpisodeMap = subtitleEpisodeMap;
		this.seasonImage = seasonImage;
	}

	@Override
	public Void work() throws Exception {
		notifyInit("", this.videoEpisodeMap.size() + this.subtitleEpisodeMap.size() + 2);

		int update = 0;

		File seasonDir = TvSerieUtils.getLocalSeasonPath(this.season);
		for (TvSerieEpisode episode : this.videoEpisodeMap.keySet()) {
			notifyUpdate(String.format("%s -> %s", Labels.VIDEO, TvSerieUtils.getEpisodeName(episode)), null);

			String filename = this.videoEpisodeMap.get(episode);
			File sourceFile = new File(seasonDir, filename);
			String ext = filename.substring(filename.lastIndexOf('.'));
			File targetFile = new File(seasonDir, String.format("%s%s", TvSerieUtils.getFullEpisodeName(episode), ext));

			if (!sourceFile.renameTo(targetFile)) {
				throw new Exception(Labels.getFailedToRename(sourceFile.getAbsolutePath(), targetFile.getAbsolutePath()));
			}
			episode.setFilename(targetFile.getName());

			notifyUpdate(null, ++update);
		}

		for (TvSerieEpisode episode : this.subtitleEpisodeMap.keySet()) {
			notifyUpdate(String.format("%s -> %s", Labels.SUBTITLE, TvSerieUtils.getEpisodeName(episode)), null);

			Map<String, EnhancedLocale> subtitleMap = this.subtitleEpisodeMap.get(episode);
			for (String filename : subtitleMap.keySet()) {
				File sourceFile = new File(seasonDir, filename);
				File targetFile = new File(seasonDir, getSubtitleFilename(filename, subtitleMap.get(filename), episode));

				if (!sourceFile.renameTo(targetFile)) {
					throw new Exception(Labels.getFailedToRename(sourceFile.getAbsolutePath(), targetFile.getAbsolutePath()));
				}

				episode.addSubtitleFilename(targetFile.getName());
			}

			notifyUpdate(null, ++update);
		}

		if (!this.seasonImage.getParentFile().equals(seasonDir.getParentFile())) {
			File targetFile = new File(seasonDir.getParent(), TvSerieUtils.getSeasonPosterFilename(this.season));
			Files.copy(this.seasonImage.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		notifyUpdate(null, ++update);

		notifyUpdate(Labels.STORING_TV_SERIE_SEASON, null);
		// TODO
//		PersistenceHandler.save(this.season);
		notifyUpdate(null, ++update);

		return null;
	}

	private String getSubtitleFilename(String filename, EnhancedLocale language, TvSerieEpisode episode) {
		String result = null;

		String ext = filename.substring(filename.lastIndexOf('.'));

		int index = 1;
		do {
			String indexSuffix = index == 1 ? "" : String.format(".%d", index);
			result = String.format("%s%s.%s%s", TvSerieUtils.getFullEpisodeName(episode), indexSuffix, language.getLanguageCode(), ext);
			index++;
		} while (episode.getSubtitleFilenames().contains(result));

		return result;
	}

}
