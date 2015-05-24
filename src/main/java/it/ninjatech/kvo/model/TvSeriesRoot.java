package it.ninjatech.kvo.model;

import java.io.File;

public class TvSeriesRoot extends Root {

	public TvSeriesRoot(String id, String path, String label) {
		super(id, path, label);
	}
	
	public TvSeriesRoot(File file) {
		super(file);
	}

}
