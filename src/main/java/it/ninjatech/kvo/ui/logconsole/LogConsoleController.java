package it.ninjatech.kvo.ui.logconsole;

public class LogConsoleController {

    private final LogConsoleView view;
    
    public LogConsoleController() {
        this.view = LogConsoleView.getInstance(this);
    }

    public LogConsoleView getView() {
        return this.view;
    }
    
    public void log(int log) {
        this.view.log(log);
    }
    
}
