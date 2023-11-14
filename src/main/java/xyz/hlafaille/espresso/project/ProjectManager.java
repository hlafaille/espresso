package xyz.hlafaille.espresso.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.hlafaille.espresso.project.dto.EspressoProjectConfiguration;
import xyz.hlafaille.espresso.util.Constants;

import java.util.Map;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Takes care of project actions during the lifetime of a project (ex: adding dependencies)
 */
public class ProjectManager {
    private ProjectManager instance;

    private EspressoProjectConfiguration espressoProjectConfiguration;

    private ProjectManager() throws IOException {
        readConfiguration();
    }

    /**
     * Get a Singleton instance
     *
     * @return ProjectManager singleton instance
     */
    public ProjectManager getInstance() throws IOException {
        if (instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }

    /**
     * Saves the configuration at this.espressoProjectConfiguration to disk
     */
    private void writeConfiguration() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(Constants.ESPRESSO_CONFIG)) {
            fileWriter.write(gson.toJson(espressoProjectConfiguration));
        }
    }


    /**
     * Read the configuration, put it at this.espressoProjectConfiguration
     */
    private void readConfiguration() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.ESPRESSO_CONFIG))) {
            espressoProjectConfiguration = new Gson().fromJson(reader, EspressoProjectConfiguration.class);
        }
    }

    /**
     * Add a dependency to the project
     */
    public void addDependency(String dependencyGroupId, String dependencyArtifactId, String dependencyVersion, Boolean isAnnotationProcessor) {
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
    }

}
