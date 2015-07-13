package it.ninjatech.kvo.ui.explorer;

import it.ninjatech.kvo.ui.Dimensions;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsView;
import it.ninjatech.kvo.ui.explorer.tvserie.ExplorerTvSerieView;

import java.awt.Insets;

import com.alee.global.StyleConstants;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;

public class ExplorerView extends WebPanel {

	private static final long serialVersionUID = -7297279602345249270L;

	private final ExplorerController controller;
	private final WebTabbedPane container;
	
	protected ExplorerView(ExplorerController controller) {
		super();

		this.controller = controller;
		this.container = new WebTabbedPane(WebTabbedPane.TOP, TabbedPaneStyle.attached);
		
		init();
	}
	
	protected void addRootsViewTab(ExplorerRootsView view) {
		this.container.addTab("Roots", ImageRetriever.retrieveExplorerTreeFolderTab(), view);
	}

	protected void addTvSerieTab(ExplorerTvSerieView view) {
		String title = "TV Series";
		
		if (this.container.getTabCount() == 1) {
			this.container.addTab(title, ImageRetriever.retrieveExplorerTreeFolderTvSeriesTab(), view);
		}
		else if (!this.container.getTitleAt(1).equals(title)) {
			this.container.insertTab(title, ImageRetriever.retrieveExplorerTreeFolderTvSeriesTab(), view, null, 1);
		}
	}
	
	protected void removeTvSerieTab() {
		this.container.removeTabAt(1);
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
