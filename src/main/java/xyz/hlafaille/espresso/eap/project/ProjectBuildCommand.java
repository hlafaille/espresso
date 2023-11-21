package xyz.hlafaille.espresso.eap.project;

import xyz.hlafaille.eap.Command;
import xyz.hlafaille.eap.CommandModifier;

import java.util.List;

public class ProjectBuildCommand extends Command {
    public ProjectBuildCommand() {
        super("build", "Assemble all required source files into an uber .jar");
    }

    @Override
    public void execute(List<CommandModifier> commandModifier) {
        System.out.println("Build project");
    }
}
