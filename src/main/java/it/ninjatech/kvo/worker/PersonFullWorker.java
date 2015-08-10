package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.util.Logger;
import it.ninjatech.kvo.util.PeopleManager;
import it.ninjatech.kvo.util.PeopleManager.Person;
import it.ninjatech.kvo.util.Utils;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class PersonFullWorker extends AbstractWorker<PersonFullWorker.PersonFullWorkerResult> {

	private final String name;
	
	public PersonFullWorker(String name) {
		this.name = name;
	}
	
	@Override
	public PersonFullWorkerResult work() throws Exception {
		PersonFullWorkerResult result = null;
		
		Logger.log("-> executing person full %s\n", this.name);
		
		Person person = PeopleManager.getInstance().getPerson(this.name);
		
		Image image = null;
		String imdbId = person.getImdbId();
		
		File file = new File(Utils.getCacheDirectory(), person.getId());
		if (file.exists()) {
			image = ImageIO.read(file);
		}
		
		if (image != null || imdbId != null) {
			result = new PersonFullWorkerResult(image, imdbId);
		}
		
		return result;
	}
	
	public static class PersonFullWorkerResult {
		
		private final Image image;
		private final String imdbId;
		
		private PersonFullWorkerResult(Image image, String imdbId) {
			this.image = image;
			this.imdbId = imdbId;
		}

		public Image getImage() {
			return this.image;
		}

		public String getImdbId() {
			return this.imdbId;
		}
		
	}

}
