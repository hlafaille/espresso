package xyz.hlafaille.espresso.configuration;

import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;

import java.io.File;

/**
 * Deals with the .espresso directory. In a nutshell, this class handles ensuring the project folder integrity &
 * health.
 */
public class DirectoryHandler {
    private final File espressoDirectory = new File(".espresso");

    /**
     * Ensures that the .espresso directory is in proper working order
     */
    public void ensureProjectIntegrity() throws EspressoProjectIntegrityCompromisedException {
        // check that the .espresso directory exists
        if (!espressoDirectory.isDirectory()) {
            throw new EspressoProjectIntegrityCompromisedException(".espresso directory does not exist");
        }
    }
}
