package xyz.hlafaille.espresso;


import xyz.hlafaille.espresso.cli.ArgumentParser;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.project.ProjectInitializer;
import xyz.hlafaille.espresso.project.ProjectManager;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        // build our argument parser
        ArgumentParser argumentParser = new ArgumentParser(
                "Espresso",
                "Build with Espresso. Because Maven and Gradle don't have opinions."
        );

        // add our commands
        argumentParser.addCommand(
                new ArgumentParser.Command(
                        "initialize",
                        "init",
                        "Establish a new Espresso project",
                        null
                ),
                new ArgumentParser.CommandHandler() {
                    @Override
                    public void execute(Logger logger, String input) {
                        logger.info("initializing project");
                        try {
                            ProjectInitializer.initializeProject(input);
                        } catch (EspressoProjectIntegrityCompromisedException | IOException e) {
                            logger.severe(e.getMessage());
                        }
                    }
                }
        );

        // parse the arguments
        argumentParser.parse(args);
    }
}