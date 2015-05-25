package it.ninjatech.kvo.ui.explorer.roots.treenode;

import java.util.Collections;

import it.ninjatech.kvo.model.AbstractPathEntity;
import it.ninjatech.kvo.model.TvSeriePathEntity;
import it.ninjatech.kvo.model.TvSeriesPathEntity;
import it.ninjatech.kvo.ui.IconRetriever;

import javax.swing.Icon;

public class TvSeriesExplorerRootsTreeNode extends AbstractRootExplorerRootsTreeNode<TvSeriesPathEntity> {

	protected TvSeriesExplorerRootsTreeNode(TvSeriesPathEntity value, RootsExplorerRootsTreeNode parent) {
		super(true, value, parent);
		
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
		return IconRetriever.retrieveExplorerTreeTvSeriesRootIcon();
	}

	@Override
	public void merge(AbstractPathEntity value) {
		TvSeriePathEntity tvSerie = (TvSeriePathEntity)value;
		
		// TODO ma è giusto il merge? non dovrebbe arrivare la pappa bella e pronta?
	}

}
