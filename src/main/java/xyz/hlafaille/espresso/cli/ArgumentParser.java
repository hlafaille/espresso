package xyz.hlafaille.espresso.cli;

import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.hlafaille.espresso.cli.handler.HelpCommandHandler;
import xyz.hlafaille.espresso.exception.EspressoCliCommandNotFoundException;
import xyz.hlafaille.espresso.util.ColorLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a small Argument Parsing and command routing utility
 */
public class ArgumentParser {
    private static ArgumentParser instance = null;

    @Getter
    private final String applicationName;

    @Getter
    private final String applicationDescription;

    @Getter
    Map<Command, CommandHandler> commandHandlerMap = new HashMap<>();
    private final Command helpCommand = new Command(
            "help",
            "h",
            "Displays this text"
    );

    public ArgumentParser(String applicationName, String applicationDescription) {
        instance = this;

        // set the application name and description
        this.applicationName = applicationName;
        this.applicationDescription = applicationDescription;

        // add the help command
        commandHandlerMap.put(helpCommand, new HelpCommandHandler());
    }

    /**
     * Method to get singleton instance
     *
     * @return Singleton instance
     */
    public static ArgumentParser getInstance() throws RuntimeException {
        if (instance == null) {
            throw new RuntimeException("ArgumentParser has never been instantiated");
        }
        return instance;
    }

    public static void exitWithCode(Integer exitCode) {
        if (exitCode == null) {
            exitCode = 0;
        }
        System.exit(exitCode);
    }

    private Integer executeCommand(Command command, String input) {
        Integer exitCode = commandHandlerMap.get(command).execute(input);
        return exitCode;
    }

    private Integer executeCommandByName(String name, String input) throws EspressoCliCommandNotFoundException {
        for (Command command : commandHandlerMap.keySet()) {
            if (name.equals(command.name) || name.equals(command.shortName)) {
                Integer exitCode = executeCommand(command, input);
                return exitCode;
            }
        }
        throw new EspressoCliCommandNotFoundException(name);
    }

    /**
     * When called, this method will iterate over the Command/CommandHandler map to determine which command needs to ran
     */
    public void parse(String[] args) {
        ColorLogger logger = ColorLogger.getInstance();

        try {
            // if no commands were provided
            if (args.length == 0) {
                logger.severe("Please provide a command");
                executeCommandByName("help", null);
                exitWithCode(1);
            }

            if (args.length == 1) {
                exitWithCode(executeCommandByName(args[0], null));
            }

            if (args.length >= 2) {
                exitWithCode(executeCommandByName(args[0], args[1]));
            }

        } catch (EspressoCliCommandNotFoundException e) {
            logger.severe(e.getMessage());
            exitWithCode(1);
        }
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
}
