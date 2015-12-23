package it.ninjatech.kvo.ui.explorer.tvserie;

import it.ninjatech.kvo.tvserie.TvSerieHelper;
import it.ninjatech.kvo.tvserie.model.TvSeriePathEntity;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.component.ExplorerTile;
import it.ninjatech.kvo.util.Labels;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import com.alee.extended.transition.ComponentTransition;
import com.alee.extended.transition.effects.fade.FadeTransitionEffect;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;

public class ExplorerTvSerieTileView extends WebPanel implements MouseListener {

	private static final long serialVersionUID = -4365144207002586456L;

	private final TvSeriePathEntity value;
	private final ExplorerTvSerieController controller;
	private ComponentTransition transition;
	private ExplorerTile defaultTile;
	private ExplorerTile fullTile;

	protected ExplorerTvSerieTileView(TvSeriePathEntity value, ExplorerTvSerieController controller) {
		super();

		this.value = value;
		this.controller = controller;

		addMouseListener(this);
		
		WebPopupMenu menu = new WebPopupMenu();
		WebMenuItem menuItem = new WebMenuItem(new SelectInRootsAction(this.value, this.controller));
		menu.add(menuItem);
		
		setComponentPopupMenu(menu);
		
		init();
	}

    @Override
    public void mouseClicked(MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            this.controller.notifyLeftClick(this.value);
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    protected TvSeriePathEntity getValue() {
		return this.value;
	}

	protected void setImages(Image fanart, Image poster) {
		if (fanart != null || poster != null) {
			this.fullTile = new ExplorerTile(fanart, poster != null ? poster : ImageRetriever.retrieveExplorerTilePosterTvSerie().getImage(),
											 TvSerieHelper.getTitle(this.value),
											 TvSerieHelper.getYear(this.value.getTvSerie()),
											 TvSerieHelper.getRate(this.value.getTvSerie()),
											 TvSerieHelper.getGenre(this.value.getTvSerie()));
			this.transition.performTransition(this.fullTile);
		}
	}

	protected void clear() {
		this.transition.performTransition(this.defaultTile);
		if (this.fullTile != null) {
			this.fullTile.destroy();
			this.fullTile = null;
		}
	}

	protected void destroy() {
		removeAll();
		this.defaultTile.destroy();
		this.defaultTile = null;
		if (this.fullTile != null) {
			this.fullTile.destroy();
			this.fullTile = null;
		}
	}

	private void init() {
		setLayout(new BorderLayout());
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		this.defaultTile = new ExplorerTile(null, ImageRetriever.retrieveExplorerTilePosterTvSerie().getImage(),
											TvSerieHelper.getTitle(this.value),
											TvSerieHelper.getYear(this.value.getTvSerie()),
											TvSerieHelper.getRate(this.value.getTvSerie()),
											TvSerieHelper.getGenre(this.value.getTvSerie()));

		this.transition = new ComponentTransition(this.defaultTile, new FadeTransitionEffect());
		add(this.transition, BorderLayout.CENTER);
	}
	
	private static class SelectInRootsAction extends AbstractAction {

        private static final long serialVersionUID = 1115542397410905299L;

        private final TvSeriePathEntity value;
        private final ExplorerTvSerieController controller;
        
        private SelectInRootsAction(TvSeriePathEntity value, ExplorerTvSerieController controller) {
            super(Labels.SELECT_IN_ROOTS, null);
            
            this.value = value;
            this.controller = controller;
        }
        
        @Override
        public void actionPerformed(ActionEvent event) {
            this.controller.selectInRoots(this.value);
        }
	    
	}

}
