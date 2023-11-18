package xyz.hlafaille.espresso.cli.handler;


import xyz.hlafaille.espresso.cli.CommandHandler;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.project.ProjectManager;
import xyz.hlafaille.espresso.project.dto.EspressoProjectConfiguration;

import java.io.IOException;
import java.util.Map;

public class InstallCommandHandler extends CommandHandler {
    public InstallCommandHandler() {
        super(true);
    }

    @Override
    public Integer execute(String input) {
        getLogger().info("Finding '%s'".formatted(input));
        return 0;
    }
}
