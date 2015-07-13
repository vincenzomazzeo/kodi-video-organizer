package it.ninjatech.kvo.ui;

import com.alee.laf.panel.WebPanel;

public final class UIUtils {

	public static WebPanel makeSeparatorPane(int height) {
		WebPanel result = new WebPanel();
		
		result.setPreferredHeight(height);
		
		return result;
	}
	
	public static WebPanel makeHorizontalFillerPane(int width) {
		WebPanel result = new WebPanel();
		
		result.setPreferredWidth(width);
		
		return result;
	}
	
	private UIUtils() {}
	
}
