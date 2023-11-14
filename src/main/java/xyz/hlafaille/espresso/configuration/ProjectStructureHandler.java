package xyz.hlafaille.espresso.configuration;

import xyz.hlafaille.espresso.Main;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.module.Configuration;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Deals with the .espresso directory. In a nutshell, this class handles initializing and maintaining the project
 * directory.
 */
public class ProjectStructureHandler {
    private final File espressoDirectory = new File(".espresso");
    private final ConfigurationParser configurationParser = new ConfigurationParser();
    private final File espressoJarsDirectory = new File(".espresso/jars");
    private final File espressoConfig = new File(".espresso/espresso.json5");
    private final Logger logger = Logger.getLogger(Main.class.getName());

    public ProjectStructureHandler() throws FileNotFoundException {
    }

    /**
     * Checks if the Project Directory exists
     *
     * @return True if it does, false if it does not
     */
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
        if (!espressoJarsDirectory.exists()) {
            throw new EspressoProjectIntegrityCompromisedException(".espresso/jars directory does not exist, is this a valid Espresso project?");
        }
    }

    /**
     * Get a File pointing towards the main package. The main package is where the main class file should be located.
     * @return File object
     */
    public File getMainPackage() {
        String[] splitSourcePackage = configurationParser.getEspressoProjectConfiguration().getJavaDetails().getMainClassPackagePath().split("\\.");
        StringBuilder sourcePackageStringBuilder = new StringBuilder();
        for (int i = 0; i < splitSourcePackage.length - 1; i++) {
            sourcePackageStringBuilder.append(splitSourcePackage[i]).append("/");
        }
        return new File(sourcePackageStringBuilder.toString());
    }

    /**
     * Get source .java files
     *
     * @return List of Path objects
     */
    public List<Path> getSourceFiles() throws URISyntaxException, IOException {
        List<Path> result;
        try (Stream<Path> walk = Files.walk(Path.of(new URI("file:///" + System.getProperty("user.dir") + "/src/main/java/" + getMainPackage())))) {
            result = walk.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());
        }
        return result;
    }

    /**
     * Get a list of .jar files in .espresso/jars
     *
     * @return List of Path objects
     */
    public List<Path> getJarLibraryFiles() throws URISyntaxException, IOException {
        List<Path> result;
        try (Stream<Path> walk = Files.walk(Path.of(new URI("file:///" + System.getProperty("user.dir") + "/.espresso/jars")))) {
            result = walk.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".jar"))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
