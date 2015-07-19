package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.connector.imdb.ImdbManager;
import it.ninjatech.kvo.util.Utils;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ActorFullWorker extends AbstractWorker<ActorFullWorker.ActorFullWorkerResult> {

	private final String cacheName;
	private final String actorName;
	
	public ActorFullWorker(String cacheName, String actorName) {
		this.cacheName = cacheName;
		this.actorName = actorName;
	}
	
	@Override
	public ActorFullWorkerResult work() throws Exception {
		ActorFullWorkerResult result = null;
		
		System.out.printf("-> executing actor full %s\n", this.actorName);
		
		Image image = null;
		String imdbId = null;
		
		File file = new File(Utils.getCacheDirectory(), this.cacheName);
		if (file.exists()) {
			image = ImageIO.read(file);
		}
		
		if (ImdbManager.getInstance().isEnabled()) {
			imdbId = ImdbManager.getInstance().searchForActor(this.actorName);
		}
		
		if (image != null || imdbId != null) {
			result = new ActorFullWorkerResult(image, imdbId);
		}
		
		return result;
	}
	
	public static class ActorFullWorkerResult {
		
		private final Image image;
		private final String imdbId;
		
		private ActorFullWorkerResult(Image image, String imdbId) {
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
