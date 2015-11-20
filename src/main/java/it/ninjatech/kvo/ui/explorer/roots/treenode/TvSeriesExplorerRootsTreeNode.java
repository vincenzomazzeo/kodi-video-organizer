package it.ninjatech.kvo.ui.explorer.roots.treenode;

import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.tvserie.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.explorer.roots.ExplorerRootsController;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.AbstractExplorerRootsContextMenu;
import it.ninjatech.kvo.ui.explorer.roots.contextmenu.TvSeriesExplorerRootsContextMenu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

public class TvSeriesExplorerRootsTreeNode extends AbstractRootsExplorerRootsTreeNode<TvSeriesPathEntity> {

	private static final long serialVersionUID = 6332400580163063140L;

	protected TvSeriesExplorerRootsTreeNode(TvSeriesPathEntity value, RootsExplorerRootsTreeNode parent) {
		super(value, parent);
		
		for (TvSeriePathEntity tvSerie : value.getTvSeries()) {
			this.children.add(new TvSerieExplorerRootsTreeNode(tvSerie, this));
		}
		Collections.sort(this.children);
	}
	
	@Override
	public boolean equals(Object other) {
		boolean result = false;

		result = other.getClass().equals(TvSeriesExplorerRootsTreeNode.class) && this.value.getLabel().equals(((TvSeriesExplorerRootsTreeNode)other).value.getLabel());
		
		return result;
	}
	
	@Override
	public Icon getCustomIcon() {
		return ImageRetriever.retrieveExplorerTreeFolderTvSeries();
	}
	
	@Override
	public AbstractExplorerRootsContextMenu<TvSeriesExplorerRootsTreeNode> getContextMenu(ExplorerRootsController controller) {
		return new TvSeriesExplorerRootsContextMenu(controller, this);
	}

	public Map<TvSeriePathEntity, TvSerieExplorerRootsTreeNode> findChildren(Set<TvSeriePathEntity> entities) {
	    Map<TvSeriePathEntity, TvSerieExplorerRootsTreeNode> result = new HashMap<>();
	    
	    for (int i = 0, n = this.children.size(); i < n; i++) {
	        TvSerieExplorerRootsTreeNode child = (TvSerieExplorerRootsTreeNode)this.children.get(i);
	        if (entities.contains(child.getValue())) {
	            result.put(child.getValue(), child);
	        }
	    }
	    
	    return result;
	}
	
}
