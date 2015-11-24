package it.ninjatech.kvo.tvserie.dbmapper;

import it.ninjatech.kvo.db.AbstractDbMapper;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.tvserie.model.TvSerieEpisode;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.tvserie.model.TvSerieSeasonImage;

import java.sql.Connection;
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

}
