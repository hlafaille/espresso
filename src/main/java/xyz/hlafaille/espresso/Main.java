package xyz.hlafaille.espresso;

import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.project.ProjectInitializer;
import xyz.hlafaille.espresso.project.ProjectManager;

import java.io.IOException;

/**
 * This will eventually be replaced with a proper CLI. It doesn't make sense to develop the CLI and different modules
 * together, as they will inevitably be too close together for their own good. Developing them largely separately
 * will allow this project to come together using building block style development.
 */
public class Main {
    public static void main(String[] args) throws IOException, EspressoProjectIntegrityCompromisedException {
        ProjectInitializer.initializeProject("xyz.hlafaille.project.Main");
        ProjectManager projectManager = ProjectManager.getInstance();
        projectManager.pullDependencies();
    }
}