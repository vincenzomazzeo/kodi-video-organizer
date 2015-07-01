package it.ninjatech.kvo.ui.tvserie;

import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;

import com.alee.laf.rootpane.WebDialog;

public class TvSerieSearchMultiResultView extends WebDialog {

	private static final long serialVersionUID = -5826369642058899605L;

	private TvSerieSearchMultiResultController controller;
	
	public TvSerieSearchMultiResultView() {
		super(UI.get(), "TV Serie Search Multi Result", true);

//		this.container = new WebPanel(new BorderLayout());

		setIconImage(ImageRetriever.retrieveExplorerTreeTvSerie().getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
//		init();
		pack();
		setResizable(false);
		setLocationRelativeTo(getOwner());
	}
	
	protected void setController(TvSerieSearchMultiResultController controller) {
		this.controller = controller;
	}
	
}
