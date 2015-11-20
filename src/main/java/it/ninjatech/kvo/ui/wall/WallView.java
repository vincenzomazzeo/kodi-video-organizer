package it.ninjatech.kvo.ui.wall;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.tvserie.TvSerieView;

import java.awt.BorderLayout;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;

public class WallView extends WebScrollPane {

    private static final long serialVersionUID = 2105962969404383341L;

    @SuppressWarnings("unused")
    private final WallController controller;
    
    public WallView(WallController controller) {
        super(new WebPanel(new BorderLayout()));
        
        this.controller = controller;
        
        init();
    }

    protected void showTvSerie(TvSerieView tvSerieView) {
        WebPanel contentPane = (WebPanel)getViewport().getView();
        contentPane.removeAll();
        contentPane.add(tvSerieView, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }
    
    private void init() {
        WebPanel contentPane = (WebPanel)getViewport().getView();
        contentPane.setBackground(Colors.BACKGROUND_INFO);
        contentPane.setOpaque(true);
    }
    
}
