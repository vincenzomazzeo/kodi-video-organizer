package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.configuration.SettingsHandler;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.tvserie.TvSerieManager;
import it.ninjatech.kvo.tvserie.model.TvSerie;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TvSerieSearchControllerNEW {

	private List<TvSeriePathEntity> entities;
	
	public TvSerieSearchControllerNEW(List<TvSeriePathEntity> entities) {
		this.entities = entities;
	}
	
	public void execute() {
		Map<String, EnhancedLocale> searchData = new HashMap<>();
		
		EnhancedLocale defaultLocale = EnhancedLocaleMap.getByLanguage(SettingsHandler.getInstance().getSettings().getTheTvDbPreferredLanguage());
		for (TvSeriePathEntity entity : this.entities) {
			searchData.put(entity.getLabel(), defaultLocale);
		}
		Map<String, List<TvSerie>> searchResult = TvSerieManager.getInstance().search(searchData);
		for ()
	}
	
}
