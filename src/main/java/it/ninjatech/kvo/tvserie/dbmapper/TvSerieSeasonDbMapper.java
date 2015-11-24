package it.ninjatech.kvo.tvserie.dbmapper;

import it.ninjatech.kvo.db.AbstractDbMapper;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.tvserie.model.TvSerieSeasonImage;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap.SimpleEntry;

public class TvSerieSeasonDbMapper extends AbstractDbMapper<TvSerieSeason> {

    @Override
    public void update(TvSerieSeason tvSerieSeason) throws Exception {
        Connection connection = null;

        try {
            connection = ConnectionHandler.getInstance().getConnection();

            write(connection,
                  "DELETE FROM tv_serie_season WHERE id = ?",
                  new SimpleEntry<Object, Integer>(tvSerieSeason.getId(), Types.VARCHAR));

            save(connection, tvSerieSeason);

            connection.commit();
        }
        catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                }
                catch (Exception e2) {
                }
            }

            throw e;
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                }
            }
        }
    }
    
    @Override
    protected TvSerieSeason map(ResultSet resultSet) throws Exception {
        return null;
    }

    protected void save(Connection connection, TvSerieSeason tvSerieSeason) throws Exception {
        write(connection,
              "INSERT INTO tv_serie_season (id, tv_serie_id, number) VALUES (?, ?, ?)",
              new SimpleEntry<Object, Integer>(tvSerieSeason.getId(), Types.VARCHAR),
              new SimpleEntry<Object, Integer>(tvSerieSeason.getTvSerie().getId(), Types.VARCHAR),
              new SimpleEntry<Object, Integer>(tvSerieSeason.getNumber(), Types.INTEGER));

        for (TvSerieSeasonImage image : tvSerieSeason.getImages()) {
            write(connection,
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
    
    @SuppressWarnings("incomplete-switch")
    protected void find(Connection connection, TvSerie tvSerie) throws Exception {
        PreparedStatement seasonsStatement = null;
        PreparedStatement seasonImagesStatement = null;
        try {
            // Seasons
            seasonsStatement = connection.prepareStatement("SELECT * FROM tv_serie_season WHERE tv_serie_id = ?");
            // Images
            seasonImagesStatement = connection.prepareStatement("SELECT * FROM tv_serie_season_image WHERE tv_serie_season_id = ?");
            
            seasonsStatement.setString(1, tvSerie.getId());
            try (ResultSet seasonsResultSet = seasonsStatement.executeQuery()) {
                while (seasonsResultSet.next()) {
                    TvSerieSeason tvSerieSeason = tvSerie.addSeason(seasonsResultSet.getString("id"), 
                                                                    seasonsResultSet.getInt("number"));
                    
                    // Images
                    seasonImagesStatement.setString(1, tvSerieSeason.getId());
                    try (ResultSet resultSet = seasonImagesStatement.executeQuery()) {
                        while (resultSet.next()) {
                            ImageProvider imageProvider = ImageProvider.valueOf(resultSet.getString("provider"));
                            switch (imageProvider) {
                            case Fanarttv:
                                tvSerieSeason.addFanarttvImage(resultSet.getString("id"),
                                                               resultSet.getString("path"), 
                                                               tvSerieSeason.getNumber(), 
                                                               resultSet.getString("rating"), 
                                                               EnhancedLocaleMap.getByLanguage(resultSet.getString("language")));
                                break;
                            case TheTvDb:
                                tvSerieSeason.addTheTvDbImage(resultSet.getString("id"),
                                                              resultSet.getString("path"), 
                                                              tvSerieSeason.getNumber(), 
                                                              resultSet.getString("rating"), 
                                                              resultSet.getString("rating_count"),
                                                              EnhancedLocaleMap.getByLanguage(resultSet.getString("language")));
                                break;
                            }
                        }
                    }
                    
                    // Tv Serie Episodes
                    TvSerieEpisodeMapper tvSerieEpisodeMapper = new TvSerieEpisodeMapper();
                    tvSerieEpisodeMapper.find(connection, tvSerie, tvSerieSeason);
                }
            }
        }
        finally {
            if (seasonsStatement != null) {
                try {
                    seasonsStatement.close();
                }
                catch (Exception e) {}
            }
            if (seasonImagesStatement != null) {
                try {
                    seasonImagesStatement.close();
                }
                catch (Exception e) {}
            }
        }
    }

}
