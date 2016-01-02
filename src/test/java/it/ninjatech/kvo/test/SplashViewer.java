package it.ninjatech.kvo.test;

import it.ninjatech.kvo.ui.component.SplashPane;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebFrame;

public class SplashViewer extends WebFrame {

    private static final long serialVersionUID = -7272545711481869048L;

    public static void main(String[] args) {
        WebLookAndFeel.install();
        
        (new SplashViewer()).setVisible(true);
    }
    
    private SplashViewer() {
        super();
        
        setDefaultCloseOperation(WebFrame.DISPOSE_ON_CLOSE);
        
        setUndecorated(true);
        setResizable(false);
        setContentPane(new SplashPane());
        
        pack();
        setLocationRelativeTo(null);
    }
    
}
