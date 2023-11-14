package xyz.hlafaille.espresso.configuration;


import java.io.File;

public class ProjectInitializer {
    /**
     * Initialize a new Espresso project by creating the `.espresso` directory, `espresso.json5`, and the `libs`
     * directory.
     *
     * @param sourcePackage The main package / root package (ex: xyz.hlafaille.espresso)
     * @return ProjectManager pointed towards the new project
     */
    public static ProjectManager initializeProject(String sourcePackage) {
        // pre-requisites
        final String currentWorkingDirectory = System.getProperty("user.dir");
        final File espressoDirectory = new File(".espresso");
        final File espressoConfiguration = new File(espressoDirectory + "/espresso.json5");
        final File espressoLibsDirectory = new File(espressoDirectory + "/libs");
    }
}
