package it.ninjatech.kvo.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class OutputHandler extends OutputStream {

    private static OutputHandler self;
    
    public static OutputHandler getInstance() {
        return self == null ? self = new OutputHandler() : self;
    }
    
    private final PrintStream standardOutput;
    private final List<OutputListener> listeners;
    
    private OutputHandler() {
        this.standardOutput = System.out;
        this.listeners = new ArrayList<>();
    }
    
    @Override
    public void write(int b) throws IOException {
        this.standardOutput.write(b);
        for (OutputListener listener : listeners) {
            listener.log(b);
        }
    }
    
    public void addListener(OutputListener listener) {
        this.listeners.add(listener);
    }

    public interface OutputListener {
        
        public void log(int log);
        
    }
    
}
