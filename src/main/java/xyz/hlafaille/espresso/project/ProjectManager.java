package xyz.hlafaille.espresso.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.project.dto.EspressoProjectConfiguration;
import xyz.hlafaille.espresso.util.Constants;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.util.stream.Stream;

/**
 * Takes care of project actions during the lifetime of a project (ex: adding dependencies)
 */
public class ProjectManager {
    private static ProjectManager instance;

    private EspressoProjectConfiguration espressoProjectConfiguration;

    private ProjectManager() throws IOException, EspressoProjectIntegrityCompromisedException {
        checkProjectIntegrity();
        readConfiguration();
    }

    /**
     * Get a Singleton instance
     *
     * @return ProjectManager singleton instance
     */
    public static ProjectManager getInstance() throws IOException, EspressoProjectIntegrityCompromisedException {
        if (instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }

    /**
     * Ensures that the .espresso folder and its accompanying files exist
     */
    private void checkProjectIntegrity() throws EspressoProjectIntegrityCompromisedException {
        List<Boolean> espressoConfigsExist = List.of(Constants.ESPRESSO_CONFIG.exists(), Constants.ESPRESSO_LIBS_DIRECTORY.exists(), Constants.ESPRESSO_DIRECTORY.exists());
        if (espressoConfigsExist.contains(false)) {
            throw new EspressoProjectIntegrityCompromisedException("Project integrity compromised, one or more files/directories do not exist");
        }
    }

    /**
     * Writes the configuration at this.espressoProjectConfiguration to disk
     */
    private void writeConfiguration() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(Constants.ESPRESSO_CONFIG)) {
            fileWriter.write(gson.toJson(espressoProjectConfiguration));
        }
    }


    /**
     * Read the configuration from disk, put it at this.espressoProjectConfiguration
     */
    private void readConfiguration() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.ESPRESSO_CONFIG))) {
            espressoProjectConfiguration = new Gson().fromJson(reader, EspressoProjectConfiguration.class);
        }
    }

    /**
     * Add a dependency to the project. All changes will be written to disk.
     *
     * @param dependencyGroupId     Maven style Group ID
     * @param dependencyArtifactId  Maven style Artifact ID
     * @param dependencyVersion     Maven style artifact version string
     * @param isAnnotationProcessor If this dependency is also an annotation preprocessor (ex: Project Lombok)
     */
    public void addDependency(String dependencyGroupId, String dependencyArtifactId, String dependencyVersion, Boolean isAnnotationProcessor) throws IOException {
        // iterate over the groups, check if the user is trying to add a dependency under a group that already exists
        Map<String, EspressoProjectConfiguration.Group> groups = espressoProjectConfiguration.getDependencies();
        Set<String> groupIds = groups.keySet();
        EspressoProjectConfiguration.Group group = null;
        for (String groupId : groupIds) {
            if (Objects.equals(groupId, dependencyGroupId)) {
                group = groups.get(groupId);
            }
        }

        // add the group if it doesn't exist
        if (group == null) {
            group = new EspressoProjectConfiguration.Group();
        }

        // add the artifact to the group
        group.getArtifacts().add(
                new EspressoProjectConfiguration.Artifact(dependencyArtifactId, dependencyVersion, isAnnotationProcessor)
        );
        groups.put(dependencyGroupId, group);

        // set the dependency group back into our in-memory configuration
        espressoProjectConfiguration.setDependencies(groups);

        // write our configuration
        writeConfiguration();
    }

    /**
     * Basic method to get the Maven Repository style `artifactId-version.jar` name of a specific (even hypothetical)
     * jar.
     *
     * @param artifactName    Artifact name
     * @param artifactVersion Artifact version
     * @return String
     */
    private String getJarNameFromSeperatedFormat(String artifactName, String artifactVersion) {
        return "%s-%s.jar".formatted(artifactName, artifactVersion);
    }

    /**
     * Get a string that points to a specific (even hypothetical) jar file in the Constants.ESPRESSO_LIBS_DIRECTORY.
     *
     * @param artifactName    Artifact name
     * @param artifactVersion Artifact Version
     * @return String
     */
    private String getEspressoLibPathFromSeperatedFormat(String artifactName, String artifactVersion) {
        return Constants.ESPRESSO_LIBS_DIRECTORY + "/" + getJarNameFromSeperatedFormat(artifactName, artifactVersion);
    }

    /**
     * Create a standard HTTPS URL from the seperated format we use internally
     *
     * @param groupId         Maven style Group ID (ex: com.google.code.gson)
     * @param artifactName    Maven style artifact name (ex: gson)
     * @param artifactVersion Maven style artifact version (ex: 1.0.0)
     * @return URL object
     */
    private URL getMavenDependencyUrlFromSeperatedFormat(String groupId, String artifactName, String artifactVersion) throws MalformedURLException {
        String baseUrl = "https://repo1.maven.org/maven2/";
        groupId = groupId.replace(".", "/");
        return new URL(baseUrl + groupId + "/" + artifactName + "/" + artifactVersion + "/" + getJarNameFromSeperatedFormat(artifactName, artifactVersion));
    }

    /**
     * Reach out to repo1.maven.org/maven2 and fetch the specified dependency.
     *
     * @param groupId         Maven style Group ID (ex: com.google.code.gson)
     * @param artifactName    Maven style artifact name (ex: gson)
     * @param artifactVersion Maven style artifact version (ex: 1.0.0)
     */
    private void resolveDependency(String groupId, String artifactName, String artifactVersion) throws IOException {
        URL dependencyUrl = getMavenDependencyUrlFromSeperatedFormat(groupId, artifactName, artifactVersion);
        FileUtils.copyURLToFile(
                dependencyUrl,
                new File(getEspressoLibPathFromSeperatedFormat(artifactName, artifactVersion)),
                5000,
                5000
        );
    }

    /**
     * Get a List of all dependencies in the Constants.ESPRESSO_LIBS_DIRECTORY
     *
     * @return A List of File objects pointing to each jar in Constants.ESPRESSO_LIBS_DIRECTORY
     */
    public List<File> getDependencies() throws IOException {
        List<File> dependencies = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(Constants.ESPRESSO_LIBS_DIRECTORY.toURI()))) {
            paths.filter(Files::isRegularFile).forEach(path -> {
                dependencies.add(path.toFile());
            });
        }
        return dependencies;
    }

    /**
     * Resolve and pull dependencies from Maven Repository, writing them to Constants.ESPRESSO_LIBS_DIRECTORY.
     * Ideally, this method should be used for fetching dependencies defined in Constants.ESPRESSO_CONFIGURATION
     */
    public void pullDependencies() throws IOException {
        Map<String, EspressoProjectConfiguration.Group> groups = espressoProjectConfiguration.getDependencies();
        for (String groupId : groups.keySet()) {
            for (EspressoProjectConfiguration.Artifact artifact : groups.get(groupId).getArtifacts()) {
                resolveDependency(groupId, artifact.getArtifactId(), artifact.getVersion());
            }
        }
    }


}
