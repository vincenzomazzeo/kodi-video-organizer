package it.ninjatech.kvo.model;

public enum TvSerieFanart {

	Banner("Banner", "banner.jpg"),
	Character("Character", "character.png"),
	Clearart("Clearart", "clearart.png"),
	Fanart("Fanart", "fanart.jpg"),
	Landscape("Landscape", "landscape.jpg"),
	Logo("Logo", "logo.png"),
	Poster("Poster", "poster.jpg");
	
	private final String name;
	private final String filename;
	
	private TvSerieFanart(String name, String filename) {
		this.name = name;
		this.filename = filename;
	}

	public String getName() {
		return this.name;
	}

	public String getFilename() {
		return this.filename;
	}
	
}
