package project;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.project.ProjectInitializer;
import xyz.hlafaille.espresso.util.Constants;

import java.io.File;
import java.io.IOException;

public class TestProjectInitializer {
    @Test
    public void testCreateProject() throws IOException, EspressoProjectIntegrityCompromisedException {
        ProjectInitializer.initializeProject("com.test.project.Main");
    }

    @AfterEach
    public void cleanUp() throws IOException {
        FileUtils.deleteDirectory(Constants.ESPRESSO_DIRECTORY);
    }
}
