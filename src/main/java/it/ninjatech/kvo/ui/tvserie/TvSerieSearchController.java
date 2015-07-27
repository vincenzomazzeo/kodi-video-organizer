package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.EnhancedLocale;
import it.ninjatech.kvo.util.EnhancedLocaleMap;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TvSerieSearchController {

	private final TvSerieSearchDialog view;
	private final TvSerieSearchListener listener;

	public TvSerieSearchController(TvSerieSearchListener listener) {
		this.view = new TvSerieSearchDialog(this);
		this.listener = listener;

		List<EnhancedLocale> languages = new ArrayList<>();
		languages.add(EnhancedLocaleMap.getEmptyLocale());
		languages.addAll(TheTvDbManager.getInstance().getLanguages());
		this.view.setLanguages(languages);
	}

	public TvSerieSearchDialog getView() {
		return this.view;
	}

	public String getSearch() {
		return this.view.getSearch();
	}
	
	public EnhancedLocale getLanguage() {
		return this.view.getLanguage();
	}
	
	protected void notifySearch() {
		if (StringUtils.isNotBlank(this.view.getSearch())) {
			if (this.listener.notifyTvSerieSearch(this.view.getSearch(), this.view.getLanguage())) {
				this.view.setVisible(false);
				this.view.dispose();
			}
		}
	}

}
