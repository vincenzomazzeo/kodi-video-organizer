package it.ninjatech.kvo.ui.worker;

import it.ninjatech.kvo.ui.component.ImageGallery;
import it.ninjatech.kvo.worker.AbstractWorker;

import java.awt.Image;
import java.io.File;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ExtraFanartsGalleryCreator extends AbstractWorker<ImageGallery> {

	private final String name;
	private final Set<String> fanarts;
	
	public ExtraFanartsGalleryCreator(String name, Set<String> fanarts) {
		this.name = name;
		this.fanarts = fanarts;
	}
	
	@Override
	public ImageGallery work() throws Exception {
		ImageGallery result = ImageGallery.getInstance(this.name, 5);
		
		notifyInit("Loading Extra Fanarts", this.fanarts.size());
		
		int i = 0;
		for (String fanart : this.fanarts) {
			File fanartFile = new File(fanart);
			if (fanartFile.exists()) {
				Image image = ImageIO.read(fanartFile);
				result.addImage(new ImageIcon(image));
				notifyUpdate(null, ++i);
			}
		}
		
		result.notifyImageCompletion();
		
		return result;
	}

}
