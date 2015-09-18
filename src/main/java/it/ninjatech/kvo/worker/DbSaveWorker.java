package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.db.AbstractDbMapper;
import it.ninjatech.kvo.util.Labels;

public class DbSaveWorker<T> extends AbstractWorker<Void> {

	private final T entity;
	private final AbstractDbMapper<T> mapper;
	private final String entityName;
	
	public DbSaveWorker(T entity, AbstractDbMapper<T> mapper, String entityName) {
		this.entity = entity;
		this.mapper = mapper;
		this.entityName = entityName;
	}

	@Override
	public Void work() throws Exception {
		notifyInit(Labels.dbSavingEntity(this.entityName), 1);
		this.mapper.save(this.entity);
		notifyUpdate(Labels.dbSavedEntity(this.entityName), 1);
		
		return null;
	}
	
}
