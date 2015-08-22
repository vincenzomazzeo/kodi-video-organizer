package it.ninjatech.kvo.db.mapper;

import it.ninjatech.kvo.model.TvSeriesPathEntity;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class TvSeriesPathEntityDbMapper extends AbstractDbMapper<TvSeriesPathEntity> {

	@Override
	@SuppressWarnings("unchecked")
	public void save(TvSeriesPathEntity tvSeriesPathEntity) throws Exception {
		save("INSERT INTO tv_series (id, path, label) VALUES (?, ?, ?)",
			 new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getId(), Types.VARCHAR),
			 new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getPath(), Types.VARCHAR),
			 new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getLabel(), Types.VARCHAR));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TvSeriesPathEntity> find() throws Exception {
		return find("SELECT * FROM tv_series ORDER BY label");
	}

	@Override
	protected TvSeriesPathEntity map(ResultSet resultSet) throws Exception {
		TvSeriesPathEntity result = null;

		result = new TvSeriesPathEntity(resultSet.getString(1),
										resultSet.getString(2),
										resultSet.getString(3));

		return result;
	}

}
