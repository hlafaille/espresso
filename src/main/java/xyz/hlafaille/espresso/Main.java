package xyz.hlafaille.espresso;


import xyz.hlafaille.espresso.cli.ArgumentParser;
import xyz.hlafaille.espresso.cli.Command;
import xyz.hlafaille.espresso.cli.handler.InitCommandHandler;
import xyz.hlafaille.espresso.cli.handler.PullCommandHandler;
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
                new Command(
                        "initialize",
                        "init",
                        "Establish a new Espresso project"
                ),
                new InitCommandHandler()
        );

        argumentParser.addCommand(
                new Command(
                        "pull",
                        "p",
                        "Pull dependencies regardless of local status"
                ),
                new PullCommandHandler()
        );

        // parse the arguments
        argumentParser.parse(args);
    }
}