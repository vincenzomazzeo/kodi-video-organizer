package it.ninjatech.kvo.db.mapper;

import it.ninjatech.kvo.model.TvSerieEpisode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.AbstractMap.SimpleEntry;

public class TvSerieEpisodeMapper extends AbstractDbMapper<TvSerieEpisode> {

	@Override
	protected TvSerieEpisode map(ResultSet resultSet) throws Exception {
		return null;
	}

	@SuppressWarnings("unchecked")
	protected void save(Connection connection, TvSerieEpisode tvSerieEpisode) throws Exception {
		save(connection,
		     "INSERT INTO tv_serie_episode (id, tv_serie_season_id, provider_id, number, language, filename, dvd_number, name, first_aired, imdb_id, overview, rating, rating_count, artwork) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getId(), Types.VARCHAR),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getSeason().getId(), Types.VARCHAR),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getProviderId(), Types.VARCHAR),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getNumber(), Types.INTEGER),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getLanguage().getLanguageCode(), Types.VARCHAR),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getFilename(), Types.VARCHAR),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getDvdNumber(), Types.DECIMAL),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getName(), Types.VARCHAR),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getFirstAired(), Types.DATE),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getImdbId(), Types.VARCHAR),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getOverview(), Types.CLOB),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getRating(), Types.DECIMAL),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getRatingCount(), Types.INTEGER),
		     new SimpleEntry<Object, Integer>(tvSerieEpisode.getArtwork(), Types.VARCHAR));
		
		for (String subtitle : tvSerieEpisode.getSubtitleFilenames()) {
			save(connection,
			     "INSERT INTO tv_serie_episode_subtitle (tv_serie_episode_id, filename) VALUES (?, ?)",
			     new SimpleEntry<Object, Integer>(tvSerieEpisode.getId(), Types.VARCHAR),
			     new SimpleEntry<Object, Integer>(subtitle, Types.VARCHAR));
		}
		
		for (String director : tvSerieEpisode.getDirectors()) {
			save(connection,
			     "INSERT INTO tv_serie_episode_director (tv_serie_episode_id, director) VALUES (?, ?)",
			     new SimpleEntry<Object, Integer>(tvSerieEpisode.getId(), Types.VARCHAR),
			     new SimpleEntry<Object, Integer>(director, Types.VARCHAR));
		}
		
		for (String guestStar : tvSerieEpisode.getGuestStars()) {
			save(connection,
			     "INSERT INTO tv_serie_episode_guest_star (tv_serie_episode_id, guest_star) VALUES (?, ?)",
			     new SimpleEntry<Object, Integer>(tvSerieEpisode.getId(), Types.VARCHAR),
			     new SimpleEntry<Object, Integer>(guestStar, Types.VARCHAR));
		}
		
		for (String writer : tvSerieEpisode.getWriters()) {
			save(connection,
			     "INSERT INTO tv_serie_episode_writer (tv_serie_episode_id, writer) VALUES (?, ?)",
			     new SimpleEntry<Object, Integer>(tvSerieEpisode.getId(), Types.VARCHAR),
			     new SimpleEntry<Object, Integer>(writer, Types.VARCHAR));
		}
	}
	
}
