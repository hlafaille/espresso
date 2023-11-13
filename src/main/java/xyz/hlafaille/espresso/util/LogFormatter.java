package xyz.hlafaille.espresso.util;

import xyz.hlafaille.espresso.Main;

import java.util.logging.*;

public class LogFormatter extends Formatter {
    /**
     * Override setting our custom bare minimum format
     * @param logRecord
     * @return
     */
    @Override
    public String format(LogRecord logRecord) {
        return String.format("[%2$s] %3$s %n",
                new java.util.Date(logRecord.getMillis()),
                logRecord.getLevel().getLocalizedName(),
                formatMessage(logRecord));
    }

    /**
     * Configures our logger
     */
    public static void configureLogger() {
        Logger logger = Logger.getLogger(Main.class.getName());
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
    }
}
