package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.model.TvSeriePathEntity;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

public class ExplorerTvSerieView extends WebScrollPane implements AdjustmentListener, HierarchyListener {

	private static final long serialVersionUID = -1748479108540324466L;

	private final ExplorerTvSerieController controller;
	private final ExplorerTvSerieModel model;
	private final Map<String, ExplorerTvSerieTileView> tilesMap;

	protected ExplorerTvSerieView(ExplorerTvSerieController controller, ExplorerTvSerieModel model) {
		super(new WebPanel(new VerticalFlowLayout()));

		this.controller = controller;
		this.model = model;
		this.tilesMap = new HashMap<>();

		this.model.setView(this);

		getVerticalScrollBar().setUnitIncrement(30);
		getVerticalScrollBar().setBlockIncrement(30);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		getVerticalScrollBar().addAdjustmentListener(this);
		addHierarchyListener(this);
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent event) {
		if (!event.getValueIsAdjusting()) {
			this.controller.handleStateChanged();
		}
	}

	@Override
	public void hierarchyChanged(HierarchyEvent event) {
		if ((event.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED && getParent().isShowing()) {
			this.controller.handleStateChanged();
		}
	}

	protected void fireModelUpdate() {
		getVerticalScrollBar().removeAdjustmentListener(this);

		Rectangle viewRect = getViewport().getViewRect();
		WebPanel container = (WebPanel)getViewport().getView();
		container.removeAll();

		Set<String> tileKeys = new HashSet<>(this.tilesMap.keySet());
		for (TvSeriePathEntity tvSerie : this.model.getData()) {
			ExplorerTvSerieTileView view = this.tilesMap.get(tvSerie.getId());
			if (view == null) {
				view = new ExplorerTvSerieTileView(tvSerie, this.controller);
				this.tilesMap.put(tvSerie.getId(), view);
			}
			else {
				tileKeys.remove(tvSerie.getId());
			}

			container.add(view);
		}
		revalidate();
		repaint();

		for (String tileKey : tileKeys) {
			ExplorerTvSerieTileView tile = this.tilesMap.remove(tileKey);
			tile.clear();
		}

		scrollRectToVisible(viewRect);

		getVerticalScrollBar().addAdjustmentListener(this);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				controller.handleStateChanged();
			}
			
		});
	}

	protected List<ExplorerTvSerieTileView> getVisibleTiles() {
		List<ExplorerTvSerieTileView> result = new ArrayList<>();

		WebPanel container = (WebPanel)getViewport().getView();

		Rectangle viewRect = getViewport().getViewRect();
		int y = viewRect.y;
		int maxY = y + viewRect.height;
		while (y < maxY) {
			Component component = container.getComponentAt(0, y);
			if (component != null && component.getClass().equals(ExplorerTvSerieTileView.class)) {
				result.add((ExplorerTvSerieTileView)component);
				y = component.getY()  + component.getHeight();
			}
			else {
				break;
			}
		}

		return result;
	}

}
