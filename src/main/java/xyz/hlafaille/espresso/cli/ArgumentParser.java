package xyz.hlafaille.espresso.cli;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.hlafaille.espresso.Main;
import xyz.hlafaille.espresso.util.LogFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This is a small Argument Parsing and command routing utility
 */
public class ArgumentParser {
    private ArgumentParser instance = null;
    private final String applicationName;
    private final String applicationDescription;
    Map<Command, CommandHandler> commandHandlerMap = new HashMap<>();
    private final Command helpCommand = new Command(
            "help",
            "h",
            "Displays this text"
    );

    private final CommandHandler helpCommandHandler = new CommandHandler(false) {
        @Override
        public void execute(String input) {
            StringBuilder outputStringBuilder = new StringBuilder(applicationName + " - " + applicationDescription + "\n");
            for (Command command : commandHandlerMap.keySet()) {
                outputStringBuilder.append("  %s, %s    -   %s\n".formatted(command.shortName, command.name, command.helpText));
            }
            System.out.println(outputStringBuilder);
        }
    };

    public ArgumentParser(String applicationName, String applicationDescription) {
        // set the application name and description
        this.applicationName = applicationName;
        this.applicationDescription = applicationDescription;

        // add the help command
        commandHandlerMap.put(helpCommand, helpCommandHandler);
    }

    /**
     * Method to get singleton instance
     *
     * @return Singleton instance
     */
    public ArgumentParser getInstance() throws RuntimeException {
        if (instance == null) {
            throw new RuntimeException("ArgumentParser has never been instantiated");
        }
        return instance;
    }

    /**
     * When called, this method will iterate over the Command/CommandHandler map to determine which command needs to ran
     */
    public void parse(String[] args) {
        // get a logger for this application
        Logger logger = Logger.getLogger(Main.class.getName());

        // if no command was specified, call the help command handler
        if (args.length == 0) {
            helpCommandHandler.execute(null);
            return;
        }

        // iterate over the commands, find a match for the short or long name and call its command handler
        for (Command command : commandHandlerMap.keySet()) {
            if (args[0].equals(command.name) || args[0].equals(command.shortName)) {
                String input = null;
                if (args.length > 1) {
                    input = args[1];
                }
                commandHandlerMap.get(command).execute(input);
                return;
            }
        }

        // if a command wasn't found, call the help command handler
        helpCommandHandler.execute(null);
    }

    /**
     * Add a command
     *
     * @param command        Command instance
     * @param commandHandler CommandHandler instance
     */
    public void addCommand(Command command, CommandHandler commandHandler) {
        commandHandlerMap.put(command, commandHandler);
    }

    @Getter
    @AllArgsConstructor
    public static class Command {
        String name;
        String shortName;
        String helpText;
    }

    /**
     * Base command handler. This class will encapsulate the logic of a particular command.
     */
    public static class CommandHandler {
        @Getter
        private final Logger logger = LogFormatter.getConfiguredLogger();
        private Boolean requireInput;

        public CommandHandler(Boolean requireInput) {
            this.requireInput = requireInput;
        }

        public Boolean getRequireInput() {
            return requireInput;
        }

        public void execute(String input) {
        }
    }
}
