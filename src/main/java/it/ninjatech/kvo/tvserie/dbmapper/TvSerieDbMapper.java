package it.ninjatech.kvo.tvserie.dbmapper;

import it.ninjatech.kvo.db.AbstractDbMapper;
import it.ninjatech.kvo.db.ConnectionHandler;
import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSerieActor;
import it.ninjatech.kvo.tvserie.model.TvSerieFanart;
import it.ninjatech.kvo.tvserie.model.TvSerieImage;
import it.ninjatech.kvo.tvserie.model.TvSerieSeason;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

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

	@Override
	protected TvSerie map(ResultSet resultSet) throws Exception {
		// TODO
		// id, tv_series_id, provider_id, name, language, first_aired, content_rating, network, overview, rating, rating_count, status, banner, fanart, poster, imdb_id
		return null;
	}
	
	public List<TvSerie> find(String tvSeriesPathEntityId) throws Exception {
		// TODO Auto-generated method stub
		return super.find();
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
