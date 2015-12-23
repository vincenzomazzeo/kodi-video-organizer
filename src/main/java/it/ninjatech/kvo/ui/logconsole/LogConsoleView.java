package it.ninjatech.kvo.ui.logconsole;

import it.ninjatech.kvo.ui.ImageRetriever;
import it.ninjatech.kvo.ui.UI;
import it.ninjatech.kvo.ui.UIUtils;
import it.ninjatech.kvo.util.Labels;

import java.awt.Dimension;

import javax.swing.text.DefaultCaret;

import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.text.WebTextArea;

public class LogConsoleView extends WebDialog {

    private static final long serialVersionUID = -3835693197892702465L;
    private static LogConsoleView self;

    public static LogConsoleView getInstance(LogConsoleController controller) {
        if (self == null) {
            self = new LogConsoleView(controller);
        }
        
        return self;
    }

    @SuppressWarnings("unused")
    private final LogConsoleController controller;
    private WebTextArea webTextArea;
    
    private LogConsoleView(LogConsoleController controller) {
        super(UI.get(), Labels.LOG_CONSOLE, false);

        this.controller = controller;
        
        setIconImage(ImageRetriever.retrieveToolBarLogConsole().getImage());
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        init();
        setPreferredSize(new Dimension(800, 300));
        setResizable(true);
        pack();
        setLocationRelativeTo(getOwner());
    }
    
    protected void log(String log) {
        this.webTextArea.append(log);
    }
    
    private void init() {
        this.webTextArea = new WebTextArea();
        DefaultCaret caret = (DefaultCaret)this.webTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);
        add(UIUtils.makeTextArea(this.webTextArea));
    }
    
}
