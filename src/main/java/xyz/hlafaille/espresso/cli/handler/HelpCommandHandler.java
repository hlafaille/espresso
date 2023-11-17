package xyz.hlafaille.espresso.cli.handler;

import xyz.hlafaille.espresso.cli.ArgumentParser;

public class HelpCommandHandler extends ArgumentParser.CommandHandler {
    public HelpCommandHandler() {
        super(false);
    }

    @Override
    public Integer execute(String input) {
        StringBuilder outputStringBuilder = new StringBuilder(getArgumentParser().getApplicationName() + " - " + getArgumentParser().getApplicationDescription() + "\n");
        for (ArgumentParser.Command command : getArgumentParser().getCommandHandlerMap().keySet()) {
            outputStringBuilder.append("%s, %s    -   %s\n".formatted(command.getShortName(), command.getName(), command.getHelpText()));
        }
        getLogger().info(outputStringBuilder.toString());
        return 0;
    }
}
