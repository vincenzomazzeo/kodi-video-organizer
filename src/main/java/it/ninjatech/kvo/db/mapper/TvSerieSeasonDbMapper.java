package it.ninjatech.kvo.db.mapper;

import it.ninjatech.kvo.model.TvSerieEpisode;
import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.model.TvSerieSeasonImage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.AbstractMap.SimpleEntry;

public class TvSerieSeasonDbMapper extends AbstractDbMapper<TvSerieSeason> {

	@Override
	protected TvSerieSeason map(ResultSet resultSet) throws Exception {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected void save(Connection connection, TvSerieSeason tvSerieSeason) throws Exception {
		save(connection,
		     "INSERT INTO tv_serie_season (id, tv_serie_id, number) VALUES (?, ?, ?)",
		     new SimpleEntry<Object, Integer>(tvSerieSeason.getId(), Types.VARCHAR),
		     new SimpleEntry<Object, Integer>(tvSerieSeason.getTvSerie().getId(), Types.VARCHAR),
		     new SimpleEntry<Object, Integer>(tvSerieSeason.getNumber(), Types.INTEGER));
		
		for (TvSerieSeasonImage image : tvSerieSeason.getImages()) {
			save (connection,
			      "INSERT INTO tv_serie_season_image (id, tv_serie_season_id, provider, path, rating, rating_count, language) VALUES (?, ?, ?, ?, ?, ?, ?)",
			      new SimpleEntry<Object, Integer>(image.getId(), Types.VARCHAR),
			      new SimpleEntry<Object, Integer>(tvSerieSeason.getId(), Types.VARCHAR),
			      new SimpleEntry<Object, Integer>(image.getProvider().name(), Types.VARCHAR),
			      new SimpleEntry<Object, Integer>(image.getPath(), Types.VARCHAR),
			      new SimpleEntry<Object, Integer>(image.getRating(), Types.VARCHAR),
			      new SimpleEntry<Object, Integer>(image.getRatingCount(), Types.VARCHAR),
			      new SimpleEntry<Object, Integer>(image.getLanguage().getLanguageCode(), Types.VARCHAR));
		}

		// Tv Serie Episode
		TvSerieEpisodeMapper tvSerieEpisodeMapper = new TvSerieEpisodeMapper();
		for (TvSerieEpisode tvSerieEpisode : tvSerieSeason.getEpisodes()) {
			tvSerieEpisodeMapper.save(connection, tvSerieEpisode);
		}
	}

}
