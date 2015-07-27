package it.ninjatech.kvo.ui.tvserie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractListModel;

public class TvSerieSeasonListModel extends AbstractListModel<String> {

	private static final long serialVersionUID = 1576352902620800824L;
	
	private final List<String> data;
	
	protected TvSerieSeasonListModel(Set<String> data) {
		this.data = new ArrayList<>(data);
	}
	
	@Override
	public int getSize() {
		return this.data.size();
	}

	@Override
	public String getElementAt(int index) {
		return this.data.get(index);
	}
	
	protected void addElement(String element) {
		this.data.add(element);
		Collections.sort(this.data);
		int index = this.data.indexOf(element);
		fireIntervalAdded(this, index, index);
	}
	
	protected void removeElement(String element) {
		int index = this.data.indexOf(element);
		this.data.remove(index);
		fireIntervalRemoved(this, index, index);
	}

}
