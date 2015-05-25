package it.ninjatech.kvo.model;

import java.io.File;

public class MoviePathEntity<T extends AbstractPathEntity> extends AbstractPathEntity {

	private final T movieParentPathEntity;
	
	public MoviePathEntity(String id, String path, String label, T moviesPathEntity) {
		super(id, path, label);
		
		this.movieParentPathEntity = moviesPathEntity;
	}
	
	public MoviePathEntity(File file, T moviesPathEntity) {
		super(file);
		
		this.movieParentPathEntity = moviesPathEntity;
	}

	public T getMovieParentPathEntity() {
		return this.movieParentPathEntity;
	}
	
}
