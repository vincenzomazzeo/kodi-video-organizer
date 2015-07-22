package it.ninjatech.kvo.test;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.alee.extended.image.WebImageGallery;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebFrame;

public class ExtraFanartTest extends WebFrame {

	private static final long serialVersionUID = -7271692298261867823L;

	public static void main(String[] args) throws Exception {
		WebLookAndFeel.install();

		ExtraFanartTest f = new ExtraFanartTest();
		f.setVisible(true);
	}

	private ExtraFanartTest() throws Exception {
		super();

		init();

		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void init() throws Exception {
		WebImageGallery wig = new WebImageGallery();
		wig.setPreferredColumnCount(3);

		File directory = new File("/Users/Shared/Well/Multimedia/Video/TV Series/2 Broke Girls/extrafanart");
		for (File f : directory.listFiles()) {
			Image image = ImageIO.read(f);
			wig.addImage(new ImageIcon(image));
		}

		add(wig.getView(false));
	}

}
