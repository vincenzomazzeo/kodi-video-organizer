package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.UI;

import javax.swing.ImageIcon;

import com.alee.extended.image.WebImageGallery;
import com.alee.laf.rootpane.WebDialog;
//TODO UIUtils
public class ImageGallery extends WebDialog {

	private static final long serialVersionUID = 1L;

	private WebImageGallery gallery;
	
	public ImageGallery(String title, int preferredColumnCount) {
		super(UI.get(), title, true);
		
		init(preferredColumnCount);
		
		setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
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
	
	private void init(int preferredColumnCount) {
		this.gallery = new WebImageGallery();
		this.gallery.setPreferredColumnCount(preferredColumnCount);
	}
	
}
