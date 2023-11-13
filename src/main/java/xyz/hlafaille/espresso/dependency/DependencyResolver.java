package xyz.hlafaille.espresso.dependency;

import org.apache.commons.io.FileUtils;
import xyz.hlafaille.espresso.Main;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Couldn't make the official Maven Dependency Resolver API work, so we're doing it ourselves...
 */
public class DependencyResolver {
    private final Logger logger = Logger.getLogger(Main.class.getName());

    private String getEspressoJarsPathFromSeperatedFormat(String artifactName, String artifactVersion) {
        return ".espresso/jars/" + getJarNameFromSeperatedFormat(artifactName, artifactVersion);
    }

    private String getJarNameFromSeperatedFormat(String artifactName, String artifactVersion) {
        return "%s-%s.jar".formatted(artifactName, artifactVersion);
    }

    /**
     * Create a standard HTTPS URL from the seperated format we use internally
     *
     * @param groupId         Maven style Group ID (ex: com.google.code.gson)
     * @param artifactName    Maven style artifact name (ex: gson)
     * @param artifactVersion Maven style artifact version (ex: 1.0.0)
     * @return URL object
     */
    private URL getDependencyUrlFromSeperatedFormat(String groupId, String artifactName, String artifactVersion) throws MalformedURLException {
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
    public void resolveDependency(String groupId, String artifactName, String artifactVersion) throws IOException {
        URL dependencyUrl = getDependencyUrlFromSeperatedFormat(groupId, artifactName, artifactVersion);
        FileUtils.copyURLToFile(
                dependencyUrl,
                new File(getEspressoJarsPathFromSeperatedFormat(artifactName, artifactVersion)),
                5000,
                5000
        );
        logger.info("resolved -> gid:%s | a:%s | v:%s".formatted(groupId, artifactName, artifactVersion));
    }
}
