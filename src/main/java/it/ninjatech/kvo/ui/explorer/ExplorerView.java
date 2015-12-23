package it.ninjatech.kvo.ui.explorer;

import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsView;
import it.ninjatech.kvo.ui.explorer.tvserie.ExplorerTvSerieView;
import it.ninjatech.kvo.util.Labels;

import java.awt.Insets;

import com.alee.global.StyleConstants;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;

public class ExplorerView extends WebPanel {

	private static final long serialVersionUID = -7297279602345249270L;

	@SuppressWarnings("unused")
    private final ExplorerController controller;
	private final WebTabbedPane container;
	
	protected ExplorerView(ExplorerController controller) {
		super();

		this.controller = controller;
		this.container = new WebTabbedPane(WebTabbedPane.TOP, TabbedPaneStyle.attached);
		
		init();
	}
	
	protected void addRootsViewTab(ExplorerRootsView view) {
		this.container.addTab(Labels.ROOTS, ImageRetriever.retrieveExplorerTreeFolderTab(), view);
	}

	protected void addTvSerieTab(ExplorerTvSerieView view) {
		String title = Labels.TV_SERIES;
		
		if (this.container.getTabCount() == 1) {
			this.container.addTab(title, ImageRetriever.retrieveExplorerTreeFolderTvSeriesTab(), view);
		}
		boolean add = true;
		for (int i = 1, n = this.container.getTabCount(); i < n; i++) {
		    if (this.container.getTitleAt(i).equals(title)) {
		        add = false;
		        break;
		    }
		}
		if (add) {
		    this.container.insertTab(title, ImageRetriever.retrieveExplorerTreeFolderTvSeriesTab(), view, null, 1);
		}
	}
	
	protected void removeTvSerieTab() {
		for (int i = 1, n = this.container.getTabCount(); i < n; i++) {
            if (this.container.getTitleAt(i).equals(Labels.TV_SERIES)) {
                this.container.removeTabAt(i);
                break;
            }
		}
	}
	
	protected void showTvSerieTab() {
	    this.container.setSelectedIndex(1);
	}

	private void init() {
		setPaintLeft(false);
		setUndecorated(false);
		setMargin(new Insets(3, 3, 3, 3));
		setRound(StyleConstants.largeRound);
		setPreferredWidth(Dimensions.getExplorerWidth());
		
		add(this.container);
	}
	
}
