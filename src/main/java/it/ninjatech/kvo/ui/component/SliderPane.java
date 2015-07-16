package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.UIUtils;

import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.ImageIcon;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.panel.WebPanel;

public class SliderPane extends WebPanel {

	private static final long serialVersionUID = 8163448162251362370L;

	public static WebDecoratedImage makeImagePane(ImageIcon image, Dimension size) {
		WebDecoratedImage result = new WebDecoratedImage(image);
		
		result.setMinimumSize(size);
		result.setShadeWidth(5);
		result.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		result.setDrawGlassLayer(false);
		
		return result;
	}
	
	private ComponentTransition transition;
	
	public SliderPane(ImageIcon voidImage, Dimension size, WebPanel titlePane) {
		super(new VerticalFlowLayout(0, 0));
		
		setOpaque(false);
		
		WebDecoratedImage image = makeImagePane(voidImage, size);
		
		this.transition = new ComponentTransition(image, new FadeTransitionEffect());
		add(this.transition);
		this.transition.setOpaque(false);
		
		add(UIUtils.makeVerticalFillerPane(10, false));
		
		add(titlePane);
	}
	
	public void setImage(WebDecoratedImage image) {
		this.transition.performTransition(image);
	}
	
}
