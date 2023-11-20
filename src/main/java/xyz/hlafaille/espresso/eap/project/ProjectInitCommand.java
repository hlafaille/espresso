package xyz.hlafaille.espresso.eap.project;

import xyz.hlafaille.eap.Command;
import xyz.hlafaille.eap.CommandModifier;

import java.util.List;

public class ProjectInitCommand extends Command {
    public ProjectInitCommand() {
        super("init", "Initialize a new project");
    }

    @Override
    public void execute(List<CommandModifier> commandModifier) {
        System.out.println("Init Project");
    }
}
