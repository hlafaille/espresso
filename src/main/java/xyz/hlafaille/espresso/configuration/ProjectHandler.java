package xyz.hlafaille.espresso.configuration;

import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;

import java.io.File;

/**
 * Deals with the .espresso directory. In a nutshell, this class handles initializing and maintaining the project
 * directory.
 */
public class ProjectHandler {
    private final File espressoDirectory = new File(".espresso");

    private boolean doesProjectDirectoryExist() {
        return espressoDirectory.isDirectory();
    }

    /**
     * Ensures that the .espresso directory is in proper working order
     */
    public void ensureProjectIntegrity() throws EspressoProjectIntegrityCompromisedException {
        // check that the .espresso directory exists
        if (!doesProjectDirectoryExist()) {
            throw new EspressoProjectIntegrityCompromisedException(".espresso directory does not exist, is this a valid Espresso project?");
        }
    }

    /**
     * Establish a new Espresso project
     */
    public void createEspressoProject() throws EspressoProjectIntegrityCompromisedException {
        // ensure we're not overwriting an existing project
        if (doesProjectDirectoryExist()) {
            throw new EspressoProjectIntegrityCompromisedException(".espresso directory already exists");
        }

        // create the directory
        espressoDirectory.mkdirs();

    }
}
