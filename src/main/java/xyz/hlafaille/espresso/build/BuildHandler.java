package xyz.hlafaille.espresso.build;

import org.apache.commons.io.FileUtils;
import xyz.hlafaille.espresso.Main;
import xyz.hlafaille.espresso.configuration.ConfigurationParser;
import xyz.hlafaille.espresso.configuration.ProjectStructureHandler;
import xyz.hlafaille.espresso.configuration.dto.EspressoProjectConfiguration;
import xyz.hlafaille.espresso.exception.EspressoProjectCompilationFailedException;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Handles building and packaging the project into a nice, neat .jar file (with a manifest!)
 */
public class BuildHandler {
    private final ProjectStructureHandler projectStructureHandler = new ProjectStructureHandler();
    private final ConfigurationParser configurationParser = new ConfigurationParser();
    private final Logger logger = Logger.getLogger(Main.class.getName());

    public BuildHandler() throws FileNotFoundException {
    }

    /**
     * Get the Java Compiler Command
     *
     * @return javac command that can be passed to a Runtime Process
     */
    public String[] getJavaCompilerCommand() throws URISyntaxException, IOException {
        // convert source paths into array of strings
        List<Path> sourcePaths = projectStructureHandler.getSourceFiles();

        // determine which jars need to be passed in as annotation processors
        List<String> annotationProcessorJarNames = new ArrayList<String>();
        Map<String, EspressoProjectConfiguration.Group> dependencies = configurationParser.getEspressoProjectConfiguration().getDependencies();
        for (String groupId : dependencies.keySet()) {
            EspressoProjectConfiguration.Group group = dependencies.get(groupId);
            for (EspressoProjectConfiguration.Artifact artifact : group.getArtifacts()) {
                if (artifact.isAnnotationProcessor()) {
                    annotationProcessorJarNames.add("%s-%s.jar".formatted(artifact.getArtifactId(), artifact.getVersion()));
                }
            }
        }

        // build our java compiler command
        List<String> javaCompilerCommand = new ArrayList<String>();
        javaCompilerCommand.add("%s".formatted(configurationParser.getEspressoProjectConfiguration().getJavaDetails().getCompilerPath()));
        // javaCompilerCommand.add("-verbose");
        javaCompilerCommand.add("-Werror");
        javaCompilerCommand.add("-d");
        javaCompilerCommand.add(".espresso/build");

        // add the dependency jars
        for (Path dependencyJar : projectStructureHandler.getJarLibraryFiles()) {
            javaCompilerCommand.add("-classpath");
            javaCompilerCommand.add(dependencyJar.toAbsolutePath().toString());
        }

        // add the annotation processor jars
        for (String annotationProcessorJar : annotationProcessorJarNames) {
            javaCompilerCommand.add("-processorpath");
            javaCompilerCommand.add(".espresso/jars/" + annotationProcessorJar);
        }

        // add the source files
        for (Path path : sourcePaths) {
            javaCompilerCommand.add(path.toString());
        }

        return javaCompilerCommand.toArray(new String[0]);
    }

    /**
     * Call the Java Compiler with all required classpath jars and source files
     */
    public void compile() throws IOException, InterruptedException, EspressoProjectCompilationFailedException, URISyntaxException {
        // get the java compiler command
        String[] javaCompilerCommand = getJavaCompilerCommand();

        // run the command
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(javaCompilerCommand);
        processBuilder.directory(new File(System.getProperty("user.dir")));
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            logger.info("javac ->   %s".formatted(line));
        }
        int exitCode = process.waitFor();

        // handle our exit code
        if (!(exitCode == 0)) {
            throw new EspressoProjectCompilationFailedException(exitCode, Arrays.toString(javaCompilerCommand));
        }
        logger.info("compilation complete");
    }

    /**
     * Automatically handles creating the custom manifest, telling jar what libs we have
     */
    public void createManifest() {

    }
    /**
     * Creates the .jar file. A .jar is effectively a .zip file.
     */
    public void createJarFromBinDirectory() throws InterruptedException, IOException {
        // run the command
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("jar", "cfe", ".espresso/build/artifact.jar", configurationParser.getEspressoProjectConfiguration().getJavaDetails().getMainClassPackagePath(), "-C", "build/", ".");
        processBuilder.directory(new File(System.getProperty("user.dir")));
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            logger.info("javac ->   %s".formatted(line));
        }
        int exitCode = process.waitFor();

        // handle our exit code
        if (!(exitCode == 0)) {
            // todo add custom exception
            throw new RuntimeException(String.valueOf(exitCode));
        }
        logger.info("artifact jar packaged");
    }
}
