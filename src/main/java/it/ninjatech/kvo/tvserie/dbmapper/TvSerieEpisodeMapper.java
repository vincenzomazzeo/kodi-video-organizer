package it.ninjatech.kvo.tvserie.dbmapper;

import it.ninjatech.kvo.db.AbstractDbMapper;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class TvSerieEpisodeMapper extends AbstractDbMapper<TvSerieEpisode> {

    @Override
    protected TvSerieEpisode map(ResultSet resultSet) throws Exception {
        TvSerieEpisode result = null;
        
        result = new TvSerieEpisode(resultSet.getString("id"), 
                                    resultSet.getString("provider_id"), 
                                    resultSet.getInt("number"), 
                                    EnhancedLocaleMap.getByLanguage(resultSet.getString("language")));
        result.setFilename(resultSet.getString("filename"));
        result.setDvdNumber(resultSet.getBigDecimal("dvd_number"));
        result.setName(resultSet.getString("name"));
        result.setFirstAired(resultSet.getDate("first_aired"));
        result.setImdbId(resultSet.getString("imdb_id"));
        result.setOverview(resultSet.getString("overview"));
        result.setRating(resultSet.getBigDecimal("rating"));
        result.setRatingCount(resultSet.getInt("rating_count"));
        result.setArtwork(resultSet.getString("artwork"));
        
        return result;
    }

    protected void save(Connection connection, TvSerieEpisode tvSerieEpisode) throws Exception {
        write(connection,
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
            write(connection,
                  "INSERT INTO tv_serie_episode_subtitle (tv_serie_episode_id, filename) VALUES (?, ?)",
                  new SimpleEntry<Object, Integer>(tvSerieEpisode.getId(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(subtitle, Types.VARCHAR));
        }

        for (String director : tvSerieEpisode.getDirectors()) {
            write(connection,
                  "INSERT INTO tv_serie_episode_director (tv_serie_episode_id, director) VALUES (?, ?)",
                  new SimpleEntry<Object, Integer>(tvSerieEpisode.getId(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(director, Types.VARCHAR));
        }

        for (String guestStar : tvSerieEpisode.getGuestStars()) {
            write(connection,
                  "INSERT INTO tv_serie_episode_guest_star (tv_serie_episode_id, guest_star) VALUES (?, ?)",
                  new SimpleEntry<Object, Integer>(tvSerieEpisode.getId(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(guestStar, Types.VARCHAR));
        }

        for (String writer : tvSerieEpisode.getWriters()) {
            write(connection,
                  "INSERT INTO tv_serie_episode_writer (tv_serie_episode_id, writer) VALUES (?, ?)",
                  new SimpleEntry<Object, Integer>(tvSerieEpisode.getId(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(writer, Types.VARCHAR));
        }
    }

    protected void find(Connection connection, TvSerie tvSerie, TvSerieSeason tvSerieSeason) throws Exception {
        PreparedStatement episodesStatement = null;
        PreparedStatement subtitlesStatement = null;
        PreparedStatement directorsStatement = null;
        PreparedStatement guestStarsStatement = null;
        PreparedStatement writersStatement = null;
        try {
            // Episodes
            episodesStatement = connection.prepareStatement("SELECT * FROM tv_serie_episode WHERE tv_serie_season_id = ?");
            // Subtitles
            subtitlesStatement = connection.prepareStatement("SELECT * FROM tv_serie_episode_subtitle WHERE tv_serie_episode_id = ?");
            // Directors
            directorsStatement = connection.prepareStatement("SELECT * FROM tv_serie_episode_director WHERE tv_serie_episode_id = ?");
            // GuestStars
            guestStarsStatement = connection.prepareStatement("SELECT * FROM tv_serie_episode_guest_star WHERE tv_serie_episode_id = ?");
            // Writers
            writersStatement = connection.prepareStatement("SELECT * FROM tv_serie_episode_writer WHERE tv_serie_episode_id = ?");
            
            episodesStatement.setString(1, tvSerieSeason.getId());
            try (ResultSet episodesResultSet = episodesStatement.executeQuery()) {
                while (episodesResultSet.next()) {
                    TvSerieEpisode tvSerieEpisode = map(episodesResultSet);
                    tvSerie.addEpisode(tvSerieSeason.getNumber(), tvSerieEpisode);
                    
                    // Subtitles
                    subtitlesStatement.setString(1, tvSerieEpisode.getId());
                    try (ResultSet resultSet = subtitlesStatement.executeQuery()) {
                        while (resultSet.next()) {
                            tvSerieEpisode.addSubtitleFilename(resultSet.getString("filename"));
                        }
                    }
                    
                    // Directors
                    directorsStatement.setString(1, tvSerieEpisode.getId());
                    try (ResultSet resultSet = directorsStatement.executeQuery()) {
                        List<String> directors = new ArrayList<>();
                        tvSerieEpisode.setDirectors(directors);
                        while (resultSet.next()) {
                            directors.add(resultSet.getString("director"));
                        }
                    }
                    
                    // GuestStars
                    guestStarsStatement.setString(1, tvSerieEpisode.getId());
                    try (ResultSet resultSet = guestStarsStatement.executeQuery()) {
                        List<String> guestStars = new ArrayList<>();
                        tvSerieEpisode.setGuestStars(guestStars);
                        while (resultSet.next()) {
                            guestStars.add(resultSet.getString("guest_star"));
                        }
                    }
                    
                    // Writers
                    writersStatement.setString(1, tvSerieEpisode.getId());
                    try (ResultSet resultSet = writersStatement.executeQuery()) {
                        List<String> writers = new ArrayList<>();
                        tvSerieEpisode.setWriters(writers);
                        while (resultSet.next()) {
                            writers.add(resultSet.getString("writer"));
                        }
                    }
                }
            }
        }
        finally {
            if (episodesStatement != null) {
                try {
                    episodesStatement.close();
                }
                catch (SQLException e) {
                }
            }
            if (subtitlesStatement != null) {
                try {
                    subtitlesStatement.close();
                }
                catch (SQLException e) {
                }
            }
            if (directorsStatement != null) {
                try {
                    directorsStatement.close();
                }
                catch (SQLException e) {
                }
            }
            if (guestStarsStatement != null) {
                try {
                    guestStarsStatement.close();
                }
                catch (SQLException e) {
                }
            }
            if (writersStatement != null) {
                try {
                    writersStatement.close();
                }
                catch (SQLException e) {
                }
            }
        }
    }
    
}
