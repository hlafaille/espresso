package xyz.hlafaille.espresso.cli;

import lombok.Getter;
import xyz.hlafaille.espresso.util.ColorLogger;

/**
 * Base command handler. This class will encapsulate the logic of a particular command.
 */
@Getter
public class CommandHandler {
    private final ArgumentParser argumentParser = ArgumentParser.getInstance();
    private final ColorLogger logger = ColorLogger.getInstance();
    private Boolean requireInput;

    public CommandHandler(Boolean requireInput) {
        this.requireInput = requireInput;
    }

    public Integer execute(String input) {
        return null;
    }
}