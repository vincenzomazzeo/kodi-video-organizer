package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.util.MemoryUtils;

import javax.swing.ImageIcon;

import com.alee.extended.image.WebImageGallery;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;

public class ImageGallery extends WebDialog {

	private static final long serialVersionUID = 5770898993627038351L;
	private static ImageGallery self;
	
	public static ImageGallery getInstance(String title, int preferredColumnCount) {
		if (self == null) {
			boolean decorateFrames = WebLookAndFeel.isDecorateDialogs();
			WebLookAndFeel.setDecorateDialogs(true);
			self = new ImageGallery();
			WebLookAndFeel.setDecorateDialogs(decorateFrames);
		}
		
		self.set(title, preferredColumnCount);

		return self;
	}
	
	private WebImageGallery gallery;
	
	private ImageGallery() {
		super(UI.get(), true);
		
		setDefaultCloseOperation(WebDialog.HIDE_ON_CLOSE);
		setResizable(false);
	}
	
	public void addImage(ImageIcon image) {
		this.gallery.addImage(image);
	}
	
	public void notifyImageCompletion() {
		add(this.gallery.getView(false));
		pack();
		setLocationRelativeTo(UI.get());
	}
	
	public void release() {
		System.out.println("*** ImageGallery -> release ***");
		MemoryUtils.printMemory("Before ImageGallery release");
		setContentPane(new WebPanel());
		MemoryUtils.printMemory("After ImageGallery release");
	}
	
	private void set(String title, int preferredColumnCount) {
		setTitle(title);
		
		init(preferredColumnCount);
	}
	
	private void init(int preferredColumnCount) {
		this.gallery = new WebImageGallery();
		this.gallery.setPreferredColumnCount(preferredColumnCount);
	}
	
}
