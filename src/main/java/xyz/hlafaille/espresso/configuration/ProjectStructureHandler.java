package xyz.hlafaille.espresso.configuration;

import xyz.hlafaille.espresso.Main;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private final File espressoJarsDirectory = new File(".espresso/jars");
    private final File espressoConfig = new File(".espresso/espresso.json5");
    private final Logger logger = Logger.getLogger(Main.class.getName());

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
     * Establish a new Espresso project
     */
    public void createEspressoProject() throws EspressoProjectIntegrityCompromisedException, IOException {
        // ensure we're not overwriting an existing project
        if (doesProjectDirectoryExist()) {
            throw new EspressoProjectIntegrityCompromisedException(".espresso directory already exists, this might already be an Espresso project!");
        }

        // create the directories
        espressoDirectory.mkdirs();
        espressoJarsDirectory.mkdirs();

        // create our config file
        espressoConfig.createNewFile();

        // read the base config from resources
        File baseConfigFile = new File(getClass().getClassLoader().getResource("base.espresso.json5").getFile());
        Scanner baseConfigReader = new Scanner(baseConfigFile);
        baseConfigReader.close();

        // build our string
        StringBuilder data = new StringBuilder();
        while (baseConfigReader.hasNextLine()) {
            data.append(baseConfigReader.nextLine()).append("\n");
        }

        // write the string
        FileWriter writer = new FileWriter(espressoConfig);
        writer.write(data.toString());
        writer.close();
        logger.info("new espresso project created, let's get programming");
    }

    /**
     * Get source .java files
     *
     * @return List of Path objects
     */
    public List<Path> getSourceFiles() throws URISyntaxException, IOException {
        List<Path> result;
        try (Stream<Path> walk = Files.walk(Path.of(new URI("file:///" + System.getProperty("user.dir") + "/test/src/main/java")))) {
            result = walk.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .collect(Collectors.toList());
        }
        return result;
    }
}
