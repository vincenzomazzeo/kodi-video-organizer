package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.UIUtils;

import java.awt.Dimension;

import javax.swing.ImageIcon;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.transition.ComponentTransition;
import com.alee.laf.panel.WebPanel;

public class SliderPane extends WebPanel {

	private static final long serialVersionUID = 8163448162251362370L;

	private ComponentTransition transition;
	private Object data;
	
	public SliderPane(ImageIcon voidImage, Dimension size, WebPanel titlePane) {
		super(new VerticalFlowLayout(0, 0));
		
		setOpaque(false);
		
		WebDecoratedImage image = UIUtils.makeImagePane(voidImage, size);
		
		this.transition = UIUtils.makeClickableTransition(image);
		add(this.transition);
		
		add(UIUtils.makeVerticalFillerPane(10, false));
		
		add(titlePane);
	}
	
	public void setImage(WebDecoratedImage image) {
		this.transition.performTransition(image);
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
