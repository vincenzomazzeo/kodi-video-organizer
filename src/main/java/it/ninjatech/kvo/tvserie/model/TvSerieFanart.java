package it.ninjatech.kvo.tvserie.model;

import java.awt.Dimension;

public enum TvSerieFanart {

	Banner("Banner", "banner.jpg", "jpg", new Dimension(1000, 185), 50, 100),
	Character("Character", "character.png", "png", new Dimension(512, 512), 130, 300),
	Clearart("Clearart", "clearart.png", "png", new Dimension(500, 281), 130, 200),
	Fanart("Fanart", "fanart.jpg", "jpg", new Dimension(1920, 1080), 130, 200),
	Landscape("Landscape", "landscape.jpg", "jpg", new Dimension(500, 281), 130, 200),
	Logo("Logo", "logo.png", "png", new Dimension(400, 155), 75, 200),
	Poster("Poster", "poster.jpg", "jpg", new Dimension(1000, 1426), 130, 350);
	
	private final String name;
	private final String filename;
	private final String type;
	private final Dimension realSize;
	private final int sliderHeight;
	private final int chooserHeight;
	
	private TvSerieFanart(String name, String filename, String type, Dimension realSize, int sliderHeight, int chooserHeight) {
		this.name = name;
		this.filename = filename;
		this.type = type;
		this.realSize = realSize;
		this.sliderHeight = sliderHeight;
		this.chooserHeight = chooserHeight;
	}

	public String getName() {
		return this.name;
	}

	public String getFilename() {
		return this.filename;
	}

	public String getType() {
        return this.type;
    }

    public Dimension getRealSize() {
		return this.realSize;
	}

	public int getSliderHeight() {
		return this.sliderHeight;
	}

	public int getChooserHeight() {
		return this.chooserHeight;
	}
	
}
