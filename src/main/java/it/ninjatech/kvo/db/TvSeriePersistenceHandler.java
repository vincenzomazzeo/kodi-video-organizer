package it.ninjatech.kvo.db;

import it.ninjatech.kvo.model.TvSerieSeason;
import it.ninjatech.kvo.model.TvSeriesPathEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public final class TvSeriePersistenceHandler {

	@SuppressWarnings("unchecked")
	public static void store(TvSeriesPathEntity tvSeriesPathEntity) throws SQLException {
		store("INSERT INTO tv_series (id, path, label) VALUES (?, ?, ?)",
		      new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getId(), Types.VARCHAR),
		      new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getPath(), Types.VARCHAR),
		      new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getLabel(), Types.VARCHAR));
		
	}

	public static void store(TvSerieSeason season) {
		// TODO
	}

	@SuppressWarnings("unchecked")
	private static void store(String statement, Entry<Object, Integer>... parameters) throws SQLException {
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = ConnectionHandler.getInstance().getConnection().prepareStatement(statement);
			int index = 0;
			for (Entry<Object, Integer> parameter : parameters) {
				preparedStatement.setObject(++index, parameter.getKey(), parameter.getValue());
			}

			preparedStatement.execute();

			ConnectionHandler.getInstance().getConnection().commit();
		}
		catch (SQLException e) {
			try {
				ConnectionHandler.getInstance().getConnection().rollback();
			}
			catch (SQLException e2) {
			}
			throw e;
		}
		finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				}
				catch (SQLException e) {
				}
			}
		}
	}

	private TvSeriePersistenceHandler() {
	}

}
