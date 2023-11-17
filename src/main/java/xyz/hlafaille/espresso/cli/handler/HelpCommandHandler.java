package xyz.hlafaille.espresso.cli.handler;


import xyz.hlafaille.espresso.cli.Command;
import xyz.hlafaille.espresso.cli.CommandHandler;

public class HelpCommandHandler extends CommandHandler {
    public HelpCommandHandler() {
        super(false);
    }

    @Override
    public Integer execute(String input) {
        StringBuilder outputStringBuilder = new StringBuilder(getArgumentParser().getApplicationName() + " - " + getArgumentParser().getApplicationDescription() + "\n");
        for (Command command : getArgumentParser().getCommandHandlerMap().keySet()) {
            outputStringBuilder.append("%s, %s    -   %s\n".formatted(command.getShortName(), command.getName(), command.getHelpText()));
        }
        getLogger().info(outputStringBuilder.toString());
        return 0;
    }
}
