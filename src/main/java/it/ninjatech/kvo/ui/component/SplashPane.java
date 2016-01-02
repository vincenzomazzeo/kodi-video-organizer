package it.ninjatech.kvo.ui.component;

import it.ninjatech.kvo.ui.Colors;
import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UIUtils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

public class SplashPane extends WebPanel {

    private static final long serialVersionUID = -8766882642003068194L;

    private static WebDecoratedImage makeImage(ImageIcon icon) {
        WebDecoratedImage result = new WebDecoratedImage(icon);
        
        result.setDrawBorder(false);
        result.setDrawGlassLayer(false);
        result.setRound(0);
        result.setShadeWidth(0);
        
        return result;
    }
    
    public SplashPane() {
        super(new VerticalFlowLayout());
        
        init();
        
        setPreferredSize(new Dimension(610, 370));
    }
    
    private void init() {
        setBackground(Colors.BACKGROUND_LOGO);
        
        WebPanel titlePane = UIUtils.makeStandardPane(new VerticalFlowLayout());
        add(titlePane);
        titlePane.add(makeImage(ImageRetriever.retrieveLogoFull()));
        WebLabel version = new WebLabel("v1.0 (160102)");
        titlePane.add(version);
        version.setMargin(0, 0, 0, 0);
        version.setFontSize(10);
        version.setForeground(Color.WHITE);
        version.setHorizontalAlignment(JLabel.CENTER);
        WebLabel developed = new WebLabel("developed by Vincenzo Mazzeo");
        titlePane.add(developed);
        developed.setMargin(0, 0, 0, 0);
        developed.setFontSize(10);
        developed.setForeground(Color.WHITE);
        developed.setHorizontalAlignment(JLabel.CENTER);
        WebLabel logo = new WebLabel("logo by Roberto Lonardi (www.robertolonardi.com)");
        titlePane.add(logo);
        logo.setMargin(0, 0, 0, 0);
        logo.setFontSize(10);
        logo.setForeground(Color.WHITE);
        logo.setHorizontalAlignment(JLabel.CENTER);
        
        add(UIUtils.makeVerticalFillerPane(40, false));
        
        WebPanel partnerPane = UIUtils.makeStandardPane(new FlowLayout(FlowLayout.CENTER, 20, 0));
        add(partnerPane);
        
        partnerPane.add(makeImage(ImageRetriever.retrieveSplashTheTvDbLogo()));
        partnerPane.add(makeImage(ImageRetriever.retrieveSplashFanarttvLogo()));
        partnerPane.add(makeImage(ImageRetriever.retrieveSplashImdbLogo()));
        partnerPane.add(makeImage(ImageRetriever.retrieveSplashMyApiFilmsLogo()));
        
        add(UIUtils.makeVerticalFillerPane(40, false));
        
        WebLabel poweredBy = new WebLabel("Powered by");
        add(poweredBy);
        poweredBy.setForeground(Color.WHITE);
        poweredBy.setMargin(0, 5, 0, 0);
        
        WebPanel poweredByPane = UIUtils.makeStandardPane(new FlowLayout(FlowLayout.CENTER, 20, 0));
        add(poweredByPane);
        
        poweredByPane.add(makeImage(ImageRetriever.retrieveSplashCommonsLangLogo()));
        poweredByPane.add(makeImage(ImageRetriever.retrieveSplashH2Logo()));
        poweredByPane.add(makeImage(ImageRetriever.retrieveSplashJacksonLogo()));
        poweredByPane.add(makeImage(ImageRetriever.retrieveSplashJerseyLogo()));
        poweredByPane.add(makeImage(ImageRetriever.retrieveSplashJWrapperLogo()));
        poweredByPane.add(makeImage(ImageRetriever.retrieveSplashWeblafLogo()));
        
        add(UIUtils.makeVerticalFillerPane(10, false));
        
        WebPanel javaPane = UIUtils.makeStandardPane(new FlowLayout(FlowLayout.LEFT));
        add(javaPane);
        
        javaPane.add(makeImage(ImageRetriever.retrieveSplashJavaLogo()));
    }

}
