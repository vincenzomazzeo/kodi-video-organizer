package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.connector.thetvdb.TheTvDbManager;
import it.ninjatech.kvo.model.EnhancedLocale;

import org.apache.commons.lang3.StringUtils;

public class TvSerieSearchController {

	private final TvSerieSearchDialog view;
	private final TvSerieSearchListener listener;

	public TvSerieSearchController(TvSerieSearchListener listener) {
		this.view = TvSerieSearchDialog.getInstance(this);
		this.listener = listener;

		this.view.setLanguages(TheTvDbManager.getInstance().getLanguages());
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
			}
		}
	}

}
