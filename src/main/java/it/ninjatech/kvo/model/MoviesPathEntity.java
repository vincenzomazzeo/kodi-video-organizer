package it.ninjatech.kvo.model;

import java.io.File;

public class MoviesPathEntity extends AbstractPathEntity {

	public MoviesPathEntity(String id, String path, String label) {
		super(id, path, label);
	}
	
	public MoviesPathEntity(File file) {
		super(file);
	}

}
