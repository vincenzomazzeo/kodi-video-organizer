package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.TvSerie;

import java.util.ArrayList;
import java.util.List;

public class TvSerieFinder extends AbstractWorker<List<TvSerie>> {

	private final String name;
	
	public TvSerieFinder(String name) {
		this.name = name;
	}

	@Override
	public List<TvSerie> work() throws Exception {
		List<TvSerie> result = new ArrayList<>();
		
		notifyUpdate(this.name, null);
		// TODO to handle language
		result = TheTvDbManager.getInstance().search(this.name, null);
		
		return result;
	}
	
}
