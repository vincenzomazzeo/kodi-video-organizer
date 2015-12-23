package it.ninjatech.kvo.util;

public final class Logger {

    private static LoggerChannel loggerChannel = new StdOutLoggerChannel();
    
    public static void log(String log, Object... values) {
        loggerChannel.log(String.format(log, values));
    }
    
    public static void setLoggerChannel(LoggerChannel loggerChannel) {
        Logger.loggerChannel = loggerChannel;
    }

    private Logger() {
    }

    public static interface LoggerChannel {

        public void log(String log);

    }
    
    private static class StdOutLoggerChannel implements LoggerChannel {

        @Override
        public void log(String log) {
            System.out.print(log);
        }
        
    }

}
