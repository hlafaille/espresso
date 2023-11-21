package xyz.hlafaille.espresso.eap.project;

import xyz.hlafaille.eap.CommandContainer;

public class ProjectContainer extends CommandContainer {
    public ProjectContainer() {
        // tell eap about this command
        super("project", "Interact with project resources");

        // add commands
        addCommand(new ProjectInitCommand());
        addCommand(new ProjectBuildCommand());
    }
}
