package xyz.hlafaille.espresso.cli.handler;

import xyz.hlafaille.espresso.cli.CommandHandler;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.project.ProjectInitializer;

import java.io.IOException;

public class InitCommandHandler extends CommandHandler {
    public InitCommandHandler() {
        super(true);
    }

    @Override
    public Integer execute(String input) {
        if (getRequireInput() && input == null) {
            getLogger().severe("Unable to initialize Espresso project. Please provide a main class path (ex: com.domain.project.Main)");
            return 1;
        }
        getLogger().info("initializing project");
        String projectName;
        try {
            projectName = ProjectInitializer.initializeProject(input);
        } catch (EspressoProjectIntegrityCompromisedException | IOException e) {
            getLogger().severe(e.getMessage());
            return 1;
        }
        getLogger().info("'%s' initialized, happy programming :)".formatted(projectName));
        return 0;
    }

}
