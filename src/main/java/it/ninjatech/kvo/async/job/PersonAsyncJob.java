package it.ninjatech.kvo.async.job;

import it.ninjatech.kvo.async.AsyncJob;
import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.connector.myapifilms.MyApiFilmsManager;
import it.ninjatech.kvo.connector.myapifilms.model.MyApiFilmsPerson;
import it.ninjatech.kvo.model.ImageProvider;
import it.ninjatech.kvo.util.PeopleManager;
import it.ninjatech.kvo.util.PeopleManager.Person;

import java.awt.Dimension;
import java.awt.Image;

import org.apache.commons.lang3.StringUtils;

public class PersonAsyncJob extends AsyncJob {

	private static final long serialVersionUID = -2429014461674573911L;

	private final String id;
	private final String name;
	private final Dimension imageSize;
	private String imdbId;
	private Image image;
	
	public PersonAsyncJob(String id, String name, Dimension imageSize) {
		this.id = id;
		this.name = name;
		this.imageSize = imageSize;
	}
	
	@Override
	protected void execute() {
		Person person = PeopleManager.getInstance().getPerson(this.name);
		
		// Check/Load IMDB ID
		if (!person.isImdbIdNotFound() && StringUtils.isBlank(person.getImdbId())) {
			if (ImdbManager.getInstance().isEnabled()) {
				System.out.printf("-> searching for imdbId of person %s\n", this.name);
				this.imdbId = ImdbManager.getInstance().searchForActor(person.getName());
				person.setImdbIdNotFound(StringUtils.isBlank(this.imdbId));
				person.setImdbId(this.imdbId);
			}
		}
		
		// Check image
		if (StringUtils.isNotBlank(person.getImdbId()) && person.getImageProvider() == ImageProvider.MyApiFilms && person.isImageDownloadable() && StringUtils.isBlank(person.getImagePath())) {
			if (MyApiFilmsManager.getInstance().isEnabled()) {
				System.out.printf("-> searching for image of person %s\n", this.name);
				MyApiFilmsPerson myApiFilmsPerson = MyApiFilmsManager.getInstance().searchForPerson(person.getImdbId());
				person.setImageDownloadable(myApiFilmsPerson != null && StringUtils.isNotBlank(myApiFilmsPerson.getUrlPhoto()));
				person.setImagePath(myApiFilmsPerson.getUrlPhoto());
			}
		}
		
		if (person.isImageDownloadable()) {
			CacheRemoteImageAsyncJob job = new CacheRemoteImageAsyncJob(person.getId(), person.getImageProvider(), person.getImagePath(), this.imageSize);
			job.execute();
			this.image = job.getImage();
		}
	}

	public String getId() {
		return this.id;
	}

	public String getImdbId() {
		return this.imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
