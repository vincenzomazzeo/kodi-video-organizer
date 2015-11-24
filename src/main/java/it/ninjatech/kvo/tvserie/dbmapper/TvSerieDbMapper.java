package it.ninjatech.kvo.tvserie.dbmapper;

import it.ninjatech.kvo.db.AbstractDbMapper;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieActor;
import it.ninjatech.kvo.tvserie.model.TvSerieFanart;
import it.ninjatech.kvo.tvserie.model.TvSerieImage;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TvSerieDbMapper extends AbstractDbMapper<TvSerie> {

    @Override
    public void save(TvSerie tvSerie) throws Exception {
        Connection connection = null;

        try {
            connection = ConnectionHandler.getInstance().getConnection();

            // Tv Serie
            write(connection,
                  "INSERT INTO tv_serie (id, tv_series_id, path, provider_id, name, language, first_aired, content_rating, network, overview, rating, rating_count, status, banner, fanart, poster, imdb_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                  new SimpleEntry<Object, Integer>(tvSerie.getId(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getTvSeriePathEntity().getTvSeriesPathEntity().getId(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getTvSeriePathEntity().getPath(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getProviderId(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getName(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getLanguage().getLanguageCode(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getFirstAired(), Types.DATE),
                  new SimpleEntry<Object, Integer>(tvSerie.getContentRating(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getNetwork(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getOverview(), Types.CLOB),
                  new SimpleEntry<Object, Integer>(tvSerie.getRating(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getRatingCount(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getStatus(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getBanner(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getFanart(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getPoster(), Types.VARCHAR),
                  new SimpleEntry<Object, Integer>(tvSerie.getImdbId(), Types.VARCHAR));

            for (String genre : tvSerie.getGenres()) {
                write(connection,
                      "INSERT INTO tv_serie_genre (tv_serie_id, genre) VALUES (?, ?)",
                      new SimpleEntry<Object, Integer>(tvSerie.getId(), Types.VARCHAR),
                      new SimpleEntry<Object, Integer>(genre, Types.VARCHAR));
            }

            for (TvSerieActor actor : tvSerie.getActors()) {
                write(connection,
                      "INSERT INTO tv_serie_actor (id, tv_serie_id, name, role, image_path, sort_order) VALUES (?, ?, ?, ?, ?, ?)",
                      new SimpleEntry<Object, Integer>(actor.getId(), Types.VARCHAR),
                      new SimpleEntry<Object, Integer>(tvSerie.getId(), Types.VARCHAR),
                      new SimpleEntry<Object, Integer>(actor.getName(), Types.VARCHAR),
                      new SimpleEntry<Object, Integer>(actor.getRole(), Types.VARCHAR),
                      new SimpleEntry<Object, Integer>(actor.getImagePath(), Types.VARCHAR),
                      new SimpleEntry<Object, Integer>(actor.getSortOrder(), Types.INTEGER));
            }

            for (TvSerieFanart fanart : TvSerieFanart.values()) {
                for (TvSerieImage image : tvSerie.getTheTvDbFanart(fanart)) {
                    saveImage(connection, image, tvSerie.getId(), ImageProvider.TheTvDb, fanart);
                }
                for (TvSerieImage image : tvSerie.getFanarttvFanart(fanart)) {
                    saveImage(connection, image, tvSerie.getId(), ImageProvider.Fanarttv, fanart);
                }
            }

            // Tv Serie Season
            TvSerieSeasonDbMapper tvSerieSeasonDbMapper = new TvSerieSeasonDbMapper();
            for (TvSerieSeason season : tvSerie.getSeasons()) {
                tvSerieSeasonDbMapper.save(connection, season);
            }

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
    public void delete(TvSerie tvSerie) throws Exception {
        write("DELETE FROM tv_serie WHERE id = ?", new SimpleEntry<Object, Integer>(tvSerie.getId(), Types.VARCHAR));
    }

    @SuppressWarnings("incomplete-switch")
    public Map<String, TvSerie> find(String tvSeriesPathEntityId) throws Exception {
        Map<String, TvSerie> result = new HashMap<>();

        Connection connection = null;

        PreparedStatement tvSeriesStatement = null;
        PreparedStatement genresStatement = null;
        PreparedStatement actorsStatement = null;
        PreparedStatement imagesStatement = null;
        try {
            connection = ConnectionHandler.getInstance().getConnection();

            // TV Series
            tvSeriesStatement = connection.prepareStatement("SELECT * FROM tv_serie WHERE tv_series_id = ?");
            // Genres
            genresStatement = connection.prepareStatement("SELECT * FROM tv_serie_genre WHERE tv_serie_id = ?");
            // Actors
            actorsStatement = connection.prepareStatement("SELECT * FROM tv_serie_actor WHERE tv_serie_id = ?");
            // Images
            imagesStatement = connection.prepareStatement("SELECT * FROM tv_serie_image WHERE tv_serie_id = ?");
            
            tvSeriesStatement.setString(1, tvSeriesPathEntityId);
            try (ResultSet tvSeriesResultSet = tvSeriesStatement.executeQuery()) {
                while (tvSeriesResultSet.next()) {
                    TvSerie tvSerie = map(tvSeriesResultSet);
                    result.put(tvSeriesResultSet.getString("path"), tvSerie);
                    
                    // Genres
                    genresStatement.setString(1, tvSerie.getId());
                    try (ResultSet resultSet = genresStatement.executeQuery()) {
                        List<String> genres = new ArrayList<>();
                        tvSerie.setGenres(genres);
                        while (resultSet.next()) {
                            genres.add(resultSet.getString("genre"));
                        }
                    }
                    
                    // Actors
                    actorsStatement.setString(1, tvSerie.getId());
                    try (ResultSet resultSet = actorsStatement.executeQuery()) {
                        while (resultSet.next()) {
                            tvSerie.addActor(resultSet.getString("name"),
                                             resultSet.getString("role"),
                                             resultSet.getString("image_path"),
                                             resultSet.getInt("sort_order"));
                        }
                    }
                    
                    // Images
                    imagesStatement.setString(1, tvSerie.getId());
                    try (ResultSet resultSet = imagesStatement.executeQuery()) {
                        while (resultSet.next()) {
                            ImageProvider imageProvider = ImageProvider.valueOf(resultSet.getString("provider"));
                            switch (imageProvider) {
                            case Fanarttv:
                                tvSerie.addFanarttvFanart(TvSerieFanart.parseName(resultSet.getString("fanart")),
                                                          resultSet.getString("id"),
                                                          resultSet.getString("path"),
                                                          resultSet.getString("rating"),
                                                          EnhancedLocaleMap.getByLanguage(resultSet.getString("language")));
                                break;
                            case TheTvDb:
                                tvSerie.addTheTvDbFanart(TvSerieFanart.parseName(resultSet.getString("fanart")),
                                                         resultSet.getString("id"),
                                                         resultSet.getString("path"),
                                                         resultSet.getString("rating"),
                                                         resultSet.getString("rating_count"),
                                                         EnhancedLocaleMap.getByLanguage(resultSet.getString("language")));
                                break;
                            }
                        }
                    }
                    
                    // Tv Serie Season
                    TvSerieSeasonDbMapper tvSerieSeasonDbMapper = new TvSerieSeasonDbMapper();
                    tvSerieSeasonDbMapper.find(connection, tvSerie);
                }
            }
        }
        finally {
            if (tvSeriesStatement != null) {
                try {
                    tvSeriesStatement.close();
                }
                catch (SQLException e) {
                }
            }
            if (genresStatement != null) {
                try {
                    genresStatement.close();
                }
                catch (SQLException e) {
                }
            }
            if (actorsStatement != null) {
                try {
                    actorsStatement.close();
                }
                catch (SQLException e) {
                }
            }
            if (imagesStatement != null) {
                try {
                    imagesStatement.close();
                }
                catch (SQLException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                }
            }
        }

        return result;
    }

    @Override
    protected TvSerie map(ResultSet resultSet) throws Exception {
        TvSerie result = null;

        result = new TvSerie(resultSet.getString("id"),
                             resultSet.getString("provider_id"),
                             resultSet.getString("name"),
                             EnhancedLocaleMap.getByLanguage(resultSet.getString("language")));
        result.setFirstAired(resultSet.getDate("first_aired"));
        result.setContentRating(resultSet.getString("content_rating"));
        result.setNetwork(resultSet.getString("network"));
        result.setOverview(resultSet.getString("overview"));
        result.setRating(resultSet.getString("rating"));
        result.setRatingCount(resultSet.getString("rating_count"));
        result.setStatus(resultSet.getString("status"));
        result.setBanner(resultSet.getString("banner"));
        result.setFanart(resultSet.getString("fanart"));
        result.setPoster(resultSet.getString("poster"));
        result.setImdbId(resultSet.getString("imdb_id"));

        return result;
    }

    private void saveImage(Connection connection, TvSerieImage image, String tvSerieId, ImageProvider imageProvider, TvSerieFanart fanart) throws Exception {
        write(connection,
              "INSERT INTO tv_serie_image (id, tv_serie_id, provider, fanart, path, rating, rating_count, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
              new SimpleEntry<Object, Integer>(image.getId(), Types.VARCHAR),
              new SimpleEntry<Object, Integer>(tvSerieId, Types.VARCHAR),
              new SimpleEntry<Object, Integer>(imageProvider.name(), Types.VARCHAR),
              new SimpleEntry<Object, Integer>(fanart.name(), Types.VARCHAR),
              new SimpleEntry<Object, Integer>(image.getPath(), Types.VARCHAR),
              new SimpleEntry<Object, Integer>(image.getRating(), Types.VARCHAR),
              new SimpleEntry<Object, Integer>(image.getRatingCount(), Types.VARCHAR),
              new SimpleEntry<Object, Integer>(image.getLanguage().getLanguageCode(), Types.VARCHAR));
    }

}
