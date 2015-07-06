package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerie;

import java.util.List;

public class TvSerieSearchMultiResultController {

	private final List<TvSerie> tvSeries;
	private final TvSerieSearchMultiResultListener listener;
	private final TvSerieSearchMultiResultView view;

	public TvSerieSearchMultiResultController(List<TvSerie> tvSeries, TvSerieSearchMultiResultListener listener) {
		this.tvSeries = tvSeries;
		this.listener = listener;
		this.view = new TvSerieSearchMultiResultView(this, this.tvSeries);
	}
	
	public TvSerieSearchMultiResultView getView() {
		return this.view;
	}
	
	protected void notifyTvSerie(String providerId) {
		this.view.setVisible(false);
		this.view.dispose();
		
		for (TvSerie tvSerie : this.tvSeries) {
			if (tvSerie.getProviderId().equals(providerId)) {
				this.listener.notifyTvSerie(tvSerie);
				break;
			}
		}
	}
	
}
