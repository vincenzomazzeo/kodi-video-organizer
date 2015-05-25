package it.ninjatech.kvo.model;

import java.io.File;

public abstract class AbstractMoviePathEntity extends AbstractPathEntity {

	private final MoviesPathEntity moviesPathEntity;
	
	protected AbstractMoviePathEntity(String id, String path, String label, MoviesPathEntity moviesPathEntity) {
		super(id, path, label);
		
		this.moviesPathEntity = moviesPathEntity;
	}
	
	protected AbstractMoviePathEntity(File file, MoviesPathEntity moviesPathEntity) {
		super(file);
		
		this.moviesPathEntity = moviesPathEntity;
	}

	public MoviesPathEntity getMoviesPathEntity() {
		return this.moviesPathEntity;
	}

}
