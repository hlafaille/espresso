package xyz.hlafaille.espresso.util;

import xyz.hlafaille.espresso.Main;
import xyz.hlafaille.espresso.cli.ArgumentParser;

import java.util.logging.*;

public class LogFormatter extends Formatter {
    private static Logger logger;

    /**
     * Override setting our custom bare minimum format
     *
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
        logger = Logger.getLogger(ArgumentParser.class.getName());
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new LogFormatter());
        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);
    }

    public static Logger getConfiguredLogger() {
        if (logger == null) {
            configureLogger();
        }
        return logger;
    }
}
