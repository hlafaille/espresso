package xyz.hlafaille.espresso.configuration;

import xyz.hlafaille.espresso.Main;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Deals with the .espresso directory. In a nutshell, this class handles initializing and maintaining the project
 * directory.
 */
public class ProjectHandler {
    private final File espressoDirectory = new File(".espresso");
    private final File espressoConfig = new File(".espresso/espresso.json5");
    private final Logger logger = Logger.getLogger(Main.class.getName());

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
    public void createEspressoProject() throws EspressoProjectIntegrityCompromisedException, IOException {
        // ensure we're not overwriting an existing project
        if (doesProjectDirectoryExist()) {
            throw new EspressoProjectIntegrityCompromisedException(".espresso directory already exists");
        }

        // create the directory and espresso.json5 file
        espressoDirectory.mkdirs();
        espressoConfig.createNewFile();

        // read the base config from resources
        File baseConfigFile = new File(getClass().getClassLoader().getResource("base.espresso.json5").getFile());
        Scanner baseConfigReader = new Scanner(baseConfigFile);

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
}
