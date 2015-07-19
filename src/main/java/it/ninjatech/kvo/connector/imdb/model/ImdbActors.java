package it.ninjatech.kvo.connector.imdb.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbActors {
	
	@JsonProperty("name_exact")
	private final List<ImdbActor> exacts;
	@JsonProperty("name_popular")
	private final List<ImdbActor> populars;
	@JsonProperty("name_approx")
	private final List<ImdbActor> approxes;
	
	@JsonCreator
	private ImdbActors(@JsonProperty("name_exact") List<ImdbActor> exacts,
	                   @JsonProperty("name_popular") List<ImdbActor> populars,
	                   @JsonProperty("name_approx") List<ImdbActor> approxes) {
		this.exacts = exacts;
		this.populars = populars;
		this.approxes = approxes;
	}
	
	public String getImdbId(String name) {
		String result = null;
		
		ImdbActor actor = null;
		if (this.exacts != null && !this.exacts.isEmpty()) {
			actor = this.exacts.get(0);
		}
		else if (this.populars != null && !this.populars.isEmpty()) {
			actor = this.populars.get(0);
		}
		else if (this.approxes != null && !this.approxes.isEmpty()) {
			actor = this.approxes.get(0);
		}
		
		if (actor != null && StringUtils.startsWithIgnoreCase(actor.name, name)) {
			result = actor.id;
		}
		
		return result;
	}

	private static class ImdbActor {
		
		@JsonProperty("id")
		private final String id;
		@JsonProperty("title")
		private final String title;
		@JsonProperty("name")
		private final String name;
		@JsonProperty("description")
		private final String description;
		
		@JsonCreator
		private ImdbActor(@JsonProperty("id") String id, 
		                  @JsonProperty("title") String title, 
		                  @JsonProperty("name") String name, 
		                  @JsonProperty("description") String description) {
			this.id = id;
			this.title = title;
			this.name = name;
			this.description = description;
		}
		
	}
	
}
