package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.util.Utils;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class CachedImageFullWorker extends AbstractWorker<Image> {

	private final String name;
	
	public CachedImageFullWorker(String name) {
		this.name = name;
	}
	
	@Override
	public Image work() throws Exception {
		Image result = null;
		
		File file = new File(Utils.getCacheDirectory(), this.name);
		if (file.exists()) {
			result = ImageIO.read(file);
		}
		
		return result;
	}
	
}
