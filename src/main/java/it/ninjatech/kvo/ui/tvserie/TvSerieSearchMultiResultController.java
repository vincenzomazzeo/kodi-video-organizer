package it.ninjatech.kvo.ui.tvserie;

public class TvSerieSearchMultiResultController {

	private final TvSerieSearchMultiResultView view;

	public TvSerieSearchMultiResultController(TvSerieSearchMultiResultView view) {
		this.view = view;

		this.view.setController(this);
	}
	
}
