package it.ninjatech.kvo.model;

import java.io.File;

public class MoviesRoot extends Root {

	public MoviesRoot(String id, String path, String label) {
		super(id, path, label);
	}
	
	public MoviesRoot(File file) {
		super(file);
	}

}
