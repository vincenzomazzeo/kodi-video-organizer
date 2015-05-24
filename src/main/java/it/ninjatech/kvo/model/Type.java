package it.ninjatech.kvo.model;

public enum Type {

	Movie("Movie", "Movies"),
	TvSerie("TV Serie", "TV Series");
	
	private final String singular;
	private final String plural;
	
	private Type(String singular, String plural) {
		this.singular = singular;
		this.plural = plural;
	}

	public String getSingular() {
		return singular;
	}

	public String getPlural() {
		return plural;
	}
	
}
