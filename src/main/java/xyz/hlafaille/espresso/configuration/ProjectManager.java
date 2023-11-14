package xyz.hlafaille.espresso.configuration;

/**
 *
 */
public class ProjectManager {
    private ProjectManager instance;

    private ProjectManager() {
    }

    public ProjectManager getInstance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }
}
