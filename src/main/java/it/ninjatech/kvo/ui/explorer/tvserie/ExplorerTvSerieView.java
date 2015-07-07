package it.ninjatech.kvo.ui.explorer.tvserie;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

public class ExplorerTvSerieView extends WebScrollPane implements ChangeListener {

	private static final long serialVersionUID = -1748479108540324466L;
	
	private final ExplorerTvSerieController controller;
	
	protected ExplorerTvSerieView(ExplorerTvSerieController controller) {
		super(new WebPanel(new VerticalFlowLayout()));
		
		this.controller = controller;
		
		init();
		
		getViewport().addChangeListener(this);
	}
	
	@Override
	public void stateChanged(ChangeEvent event) {
		this.controller.handleStateChanged();
	}
	
	protected void addTile(ExplorerTvSerieTileView tile) {
		((WebPanel)getViewport().getView()).add(tile);
	}
	
	protected void ciccio() {
		WebLabel label = new WebLabel();
		label.setPreferredHeight(30);
		label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		((WebPanel)getViewport().getView()).add(label);
	}

	private void init() {
		
	}
	
}
