package xyz.hlafaille.espresso.project;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.hlafaille.espresso.project.dto.EspressoProjectConfiguration;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectInitializer {

    /**
     * Get the main class name from the main class path. In the example `xyz.hlafaille.espresso.Main`, `Main` would be
     * our main class.
     *
     * @param mainClassPath Full path pointing to the main class
     * @return Main class name
     */
    private static String getMainClassNameFromMainClassPathString(String mainClassPath) {
        String[] splitSourcePackage = mainClassPath.split("\\.");
        return splitSourcePackage[splitSourcePackage.length - 1];
    }

    /**
     * Get the project name from the main class path. In the example `xyz.hlafaille.espresso.Main`, `espresso` would be
     * our project name.
     *
     * @param mainClassPath Full path pointing to the main class
     * @return Project name
     */
    private static String getProjectNameFromMainClassPathString(String mainClassPath) {
        String[] splitSourcePackage = mainClassPath.split("\\.");
        return splitSourcePackage[splitSourcePackage.length - 2];
    }

    /**
     * Get the root package from the main class path. In the example `xyz.hlafaille.espresso.Main`,
     * `xyz.hlafaille.espresso` would be our root package.
     *
     * @param mainClassPath Full path pointing to the main class
     * @return Root package
     */
    private static String getRootPackageFromMainClassPathString(String mainClassPath) {
        String[] splitSourcePackage = mainClassPath.split("\\.");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < splitSourcePackage.length - 2; i++) {
            stringBuilder.append(splitSourcePackage[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Get the author from the main class path. In the example `xyz.hlafaille.espresso.Main`, `hlafaille` would be our
     * author.
     *
     * @param mainClassPath Full path pointing to the main class
     * @return Author name
     */
    private static String getAuthorFromMainClassPathString(String mainClassPath) {
        String[] splitSourcePackage = mainClassPath.split("\\.");
        return splitSourcePackage[1];
    }

    /**
     * Initialize a new Espresso project by creating the `.espresso` directory, `espresso.json5`, and the `libs`
     * directory.
     *
     * @param mainClassPath The path pointing to the main class (ex: xyz.hlafaille.espresso.Main)
     */
    public static String initializeProject(String mainClassPath) throws EspressoProjectIntegrityCompromisedException, IOException {
        // pre-requisites
        final File espressoDirectory = new File(".espresso");
        final File espressoConfiguration = new File(espressoDirectory + "/espresso.json5");
        final File espressoLibsDirectory = new File(espressoDirectory + "/libs");

        // check if any files exist
        List<Boolean> existingPreRequisites = new ArrayList<Boolean>(List.of(espressoDirectory.exists(), espressoConfiguration.exists(), espressoLibsDirectory.exists()));
        if (existingPreRequisites.contains(true)) {
            throw new EspressoProjectIntegrityCompromisedException("Espresso project (or portions of one) already exist");
        }

        // create the files
        espressoDirectory.mkdirs();
        espressoConfiguration.createNewFile();
        espressoLibsDirectory.mkdirs();

        // create an empty string/group map (dependencies key won't end up in the json file otherwise)
        Map<String, EspressoProjectConfiguration.Group> groupMap = new HashMap<>();

        String projectName = getProjectNameFromMainClassPathString(mainClassPath);

        // initialize an EspressoProjectConfiguration DTO, that will be converted to JSON by Gson
        EspressoProjectConfiguration espressoProjectConfiguration = new EspressoProjectConfiguration(
                new EspressoProjectConfiguration.Details(
                        projectName,
                        getAuthorFromMainClassPathString(mainClassPath),
                        "1.0.0"),
                groupMap,
                null,
                new EspressoProjectConfiguration.JavaDetails(
                        "/usr/bin/javac",
                        "/usr/bin/jar",
                        mainClassPath)
        );

        // convert this DTO to json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String espressoConfigurationJsonString = gson.toJson(espressoProjectConfiguration);

        // write the file
        try (FileWriter fileWriter = new FileWriter(espressoConfiguration)) {
            fileWriter.write(espressoConfigurationJsonString);
        }
        return projectName;
    }
}
