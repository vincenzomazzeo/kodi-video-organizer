package it.ninjatech.kvo.model;

import java.io.File;

public class MovieSetPathEntity extends AbstractPathEntity {

	private final MoviesPathEntity moviesPathEntity;
	
	public MovieSetPathEntity(String id, String path, String label, MoviesPathEntity moviesPathEntity) {
		super(id, path, label);
		
		this.moviesPathEntity = moviesPathEntity;
	}
	
	public MovieSetPathEntity(File file, MoviesPathEntity moviesPathEntity) {
		super(file);
		
		this.moviesPathEntity = moviesPathEntity;
	}

	public MoviesPathEntity getMoviesPathEntity() {
		return this.moviesPathEntity;
	}
	
}
