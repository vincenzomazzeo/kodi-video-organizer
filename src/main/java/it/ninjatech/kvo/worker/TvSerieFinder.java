package it.ninjatech.kvo.worker;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.tvserie.model.TvSerie;

import java.util.ArrayList;
import java.util.List;
// TODO rimuovere
public class TvSerieFinder extends AbstractWorker<List<TvSerie>> {

	private final String name;
	private final EnhancedLocale language;
	
	public TvSerieFinder(String name, EnhancedLocale language) {
		this.name = name;
		this.language = language;
	}

	@Override
	public List<TvSerie> work() throws Exception {
		List<TvSerie> result = new ArrayList<>();
		
		notifyUpdate(this.name, null);
		result = TheTvDbManager.getInstance().search(this.name, this.language);
		
		return result;
	}
	
}
