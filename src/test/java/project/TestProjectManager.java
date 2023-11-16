package project;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.project.ProjectInitializer;
import xyz.hlafaille.espresso.project.ProjectManager;
import xyz.hlafaille.espresso.util.Constants;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProjectManager {

    @InjectMocks
    private ProjectManager projectManager;

    @BeforeEach
    public void build() throws IOException, EspressoProjectIntegrityCompromisedException {
        // initialize a new espresso project and have mockito get our singleton
        ProjectInitializer.initializeProject("com.test.project.Main");
        MockitoAnnotations.openMocks(this);

        // reset singleton state (if there is any)
        projectManager.reset();

        // add a dependency to the project
        projectManager.addDependency(
                "com.google.code.gson", "gson", "2.10.1",
                false
        );
    }

    @AfterEach
    public void teardown() throws IOException {
        FileUtils.deleteDirectory(Constants.ESPRESSO_DIRECTORY);
    }

    @Test
    public void testPullingDependencies() throws IOException {
        projectManager.pullDependencies();
    }

    @Test
    public void testGettingDependencyFiles() throws IOException {
        projectManager.pullDependencies();
        List<File> libJars = projectManager.getDependencies();
        assertEquals(libJars.size(), 1, "file count in .espresso/libs was: " + libJars.size());
    }
}
