package xyz.hlafaille.espresso.configuration;

import com.google.gson.Gson;
import xyz.hlafaille.espresso.configuration.dto.EspressoProjectConfiguration;
import xyz.hlafaille.espresso.util.Constants;

import java.io.*;

/**
 * Takes care of project actions
 */
public class ProjectManager {
    private ProjectManager instance;

    private EspressoProjectConfiguration espressoProjectConfiguration;

    private ProjectManager() throws IOException {
        discoverAndLoadProject();
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
     * Discovers an Espresso project in the current working directory, load it.
     */
    private void discoverAndLoadProject() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.ESPRESSO_CONFIG))) {
            espressoProjectConfiguration = new Gson().fromJson(reader, EspressoProjectConfiguration.class);
        }
    }

}
