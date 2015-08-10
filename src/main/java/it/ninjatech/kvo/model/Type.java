package it.ninjatech.kvo.model;

import it.ninjatech.kvo.util.Labels;

public enum Type {

	Movie(Labels.MOVIE, Labels.MOVIES),
	TvSerie(Labels.TV_SERIE, Labels.TV_SERIES);
	
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
