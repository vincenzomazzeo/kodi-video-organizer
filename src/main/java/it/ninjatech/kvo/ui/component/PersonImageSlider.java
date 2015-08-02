package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Dimensions;

import com.alee.laf.panel.WebPanel;

public class PersonImageSlider extends AbstractImageSlider<String> {

	private static final long serialVersionUID = 1867272969066226417L;

	public PersonImageSlider(String id, ImageSliderListener listener) {
		super(id, listener, Dimensions.getPersonSliderSize(), false, true);
	}

	@Override
	protected WebPanel makeTitle(String data) {
		return makeTitlePane(data);
	}
	
}
