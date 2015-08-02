package it.ninjatech.kvo.util;

import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.model.TvSerieActor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class PeopleManager {

	private static PeopleManager self;
	
	public static void init() {
		if (self == null) {
			self = new PeopleManager();
		}
	}
	
	public static PeopleManager getInstance() {
		return self;
	}
	
	private final Map<String, Person> nameMap;
	
	private PeopleManager() {
		this.nameMap = new HashMap<>();
	}
	
	public Person getPerson(String name) {
		Person result = this.nameMap.get(name.toLowerCase());
		
		if (result == null) {
			result = new Person(name);
			this.nameMap.put(result.name.toLowerCase(), result);
			result.imagePath = null;
			result.imageProvider = ImageProvider.MyApiFilms;
			result.imageDownloadable = true;
		}
		
		return result;
	}
	
	public void addTvSerieActor(TvSerieActor actor) {
		Person person = this.nameMap.get(actor.getName().toLowerCase());
		
		if (person == null) {
			person = new Person(actor.getName());
			this.nameMap.put(person.name.toLowerCase(), person);
		}
		
		if (StringUtils.isNotBlank(actor.getImagePath()) && StringUtils.isBlank(person.imagePath)) {
			person.imagePath = actor.getImagePath();
			person.imageProvider = ImageProvider.TheTvDb;
			person.imageDownloadable = true;
		}
	}
	
	public static class Person {
		
		private final String id;
		private final String name;
		private String imdbId;
		private boolean imdbIdNotFound;
		private String imagePath;
		private ImageProvider imageProvider;
		private boolean imageDownloadable;
		
		private Person(String name) {
			this.id = UUID.randomUUID().toString();
			this.name = name;
			this.imdbIdNotFound = false;
			this.imageDownloadable = false;
		}
		
		public String getId() {
			return this.id;
		}

		public String getName() {
			return this.name;
		}

		public String getImdbId() {
			return this.imdbId;
		}

		public void setImdbId(String imdbId) {
			this.imdbId = imdbId;
		}

		public boolean isImdbIdNotFound() {
			return this.imdbIdNotFound;
		}

		public void setImdbIdNotFound(boolean imdbIdNotFound) {
			this.imdbIdNotFound = imdbIdNotFound;
		}

		public String getImagePath() {
			return this.imagePath;
		}

		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}

		public ImageProvider getImageProvider() {
			return this.imageProvider;
		}

		public boolean isImageDownloadable() {
			return this.imageDownloadable;
		}

		public void setImageDownloadable(boolean imageDownloadable) {
			this.imageDownloadable = imageDownloadable;
		}

	}
	
}
