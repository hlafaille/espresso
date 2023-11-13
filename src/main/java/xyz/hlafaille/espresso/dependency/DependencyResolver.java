package xyz.hlafaille.espresso.dependency;

import org.apache.commons.io.FileUtils;
import xyz.hlafaille.espresso.Main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Couldn't make the official Maven Dependency Resolver API work, so we're doing it ourselves...
 */
public class DependencyResolver {
    private final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Reach out to repo1.maven.org/maven2 and fetch the specified dependency.
     *
     * @param groupId         Maven style Group ID (ex: com.google.code.gson)
     * @param artifactName    Maven style artifact name (ex: gson)
     * @param artifactVersion Maven style artifact version (ex: 1.0.0)
     */
    public void resolveDependency(String groupId, String artifactName, String artifactVersion) throws IOException {
        logger.info("resolving gid:%s || a:%s || v:%s".formatted(groupId, artifactName, artifactVersion));

        // todo assemble the url
        URL dependencyUrl = new URL("https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar");

        FileUtils.copyURLToFile(
                dependencyUrl,
                new File("gson.jar"),
                5000,
                5000
        );
    }
}
