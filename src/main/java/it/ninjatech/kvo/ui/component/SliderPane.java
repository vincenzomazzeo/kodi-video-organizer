package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.UIUtils;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.transition.ComponentTransition;
import com.alee.laf.panel.WebPanel;

public class SliderPane extends WebPanel {

	private static final long serialVersionUID = 8163448162251362370L;

	private final Object data;
	private ComponentTransition transition;
	
	public SliderPane(Object data, ImageIcon voidImage, Dimension size, WebPanel titlePane) {
		super(new VerticalFlowLayout(0, 0));
		
		this.data = data;
		
		setOpaque(false);
		
		this.transition = UIUtils.makeClickableTransition(makeVoidComponent(voidImage, size));
		add(this.transition);
		
		add(UIUtils.makeVerticalFillerPane(10, false));
		
		add(titlePane);
	}
	
	protected JComponent makeVoidComponent(ImageIcon voidImage, Dimension size) {
		return UIUtils.makeImagePane(voidImage, size);
	}
	
	protected void setComponent(JComponent component) {
		this.transition.performTransition(component);
	}
	
	public void setImage(WebDecoratedImage image) {
		setComponent(image);
	}

	public Object getData() {
		return this.data;
	}

}
