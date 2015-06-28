package it.ninjatech.kvo.ui.explorer;

import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsModel;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsView;

import java.awt.Insets;

import com.alee.global.StyleConstants;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.tabbedpane.TabbedPaneStyle;
import com.alee.laf.tabbedpane.WebTabbedPane;

public class ExplorerView extends WebPanel {

	private static final long serialVersionUID = -7297279602345249270L;

	private final WebTabbedPane container;
	private final ExplorerRootsView roots;
	
	public ExplorerView(int uiWidth) {
		super();
		
		this.container = new WebTabbedPane(WebTabbedPane.TOP, TabbedPaneStyle.attached);
		
		ExplorerRootsModel rootsModel = new ExplorerRootsModel();
		this.roots = new ExplorerRootsView(rootsModel);
		new ExplorerRootsController(rootsModel, this.roots);
		
		init(uiWidth);
	}
	
	private void init(int uiWidth) {
		setPaintLeft(false);
		setUndecorated(false);
		setMargin(new Insets(3, 3, 3, 3));
		setRound(StyleConstants.largeRound);
		setPreferredWidth(uiWidth / 5);
		
		add(this.container);
		this.container.addTab("Roots", ImageRetriever.retrieveExplorerTreeFolderTab(), this.roots);
	}
	
}
