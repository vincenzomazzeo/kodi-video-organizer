package it.ninjatech.kvo.worker;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageFullWorker extends AbstractWorker<Image> {

	private final String path;
	private final String name;
	
	public ImageFullWorker(String path, String name) {
		this.path = path;
		this.name = name;
	}
	
	@Override
	public Image work() throws Exception {
		Image result = null;
		
		File file = new File(this.path, this.name);
		if (file.exists()) {
			result = ImageIO.read(file);
		}
		
		return result;
	}
	
}
