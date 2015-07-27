package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.model.TvSerie;

import java.util.List;

public class TvSerieSearchMultiResultController {

	private final List<TvSerie> tvSeries;
	private final TvSerieSearchMultiResultListener listener;
	private final TvSerieSearchMultiResultDialog view;

	public TvSerieSearchMultiResultController(List<TvSerie> tvSeries, TvSerieSearchMultiResultListener listener) {
		this.tvSeries = tvSeries;
		this.listener = listener;
		this.view = new TvSerieSearchMultiResultDialog(this, this.tvSeries);
	}
	
	public TvSerieSearchMultiResultDialog getView() {
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
