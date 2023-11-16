package xyz.hlafaille.espresso.cli;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            "Displays this text",
            null
    );
    private final CommandHandler helpCommandHandler = new CommandHandler() {
        @Override
        public String toString() {
            StringBuilder outputStringBuilder = new StringBuilder(applicationName + " - " + applicationDescription + "\n");
            for (Command command : commandHandlerMap.keySet()) {
                outputStringBuilder.append("  %s, %s    -   %s\n".formatted(command.shortName, command.name, command.helpText));
            }
            return outputStringBuilder.toString();
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
        // if no command was specified, call the help command handler
        if (args.length == 0) {
            System.out.println(helpCommandHandler);
            return;
        }

        // iterate over the commands, find a match for the short or long name and call its command handler
        for (Command command : commandHandlerMap.keySet()) {
            if (args[0].equals(command.name) || args[0].equals(command.shortName)) {
                System.out.println(commandHandlerMap.get(command));
                return;
            }
        }

        // if a command wasn't found, say so and call the help command handler
        System.out.println(helpCommandHandler);
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
        List<Command> childCommands;
    }

    /**
     * Base command handler. This class will encapsulate the logic of a particular command.
     */
    public class CommandHandler {
        @Override
        public String toString() {
            return null;
        }
    }
}
