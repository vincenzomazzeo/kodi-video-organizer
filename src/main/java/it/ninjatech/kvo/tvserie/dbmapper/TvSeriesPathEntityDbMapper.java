package it.ninjatech.kvo.tvserie.dbmapper;

import it.ninjatech.kvo.db.AbstractDbMapper;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class TvSeriesPathEntityDbMapper extends AbstractDbMapper<TvSeriesPathEntity> {

	public TvSeriesPathEntityDbMapper() {
		super();
	}
	
	@Override
	public void save(TvSeriesPathEntity tvSeriesPathEntity) throws Exception {
		write("INSERT INTO tv_series (id, path, label) VALUES (?, ?, ?)",
			 new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getId(), Types.VARCHAR),
			 new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getPath(), Types.VARCHAR),
			 new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getLabel(), Types.VARCHAR));
	}

	@Override
	public void delete(TvSeriesPathEntity tvSeriesPathEntity) throws Exception {
		write("DELETE FROM tv_series WHERE id = ?", new SimpleEntry<Object, Integer>(tvSeriesPathEntity.getId(), Types.VARCHAR));
	}

	@Override
	public List<TvSeriesPathEntity> find() throws Exception {
		return find("SELECT * FROM tv_series ORDER BY label");
	}

	@Override
	protected TvSeriesPathEntity map(ResultSet resultSet) throws Exception {
		TvSeriesPathEntity result = null;

		result = new TvSeriesPathEntity(resultSet.getString("id"),
										resultSet.getString("path"),
										resultSet.getString("label"));

		return result;
	}

}
