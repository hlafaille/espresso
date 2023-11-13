package xyz.hlafaille.espresso.build;

import xyz.hlafaille.espresso.Main;
import xyz.hlafaille.espresso.configuration.ConfigurationParser;
import xyz.hlafaille.espresso.configuration.ProjectStructureHandler;
import xyz.hlafaille.espresso.exception.EspressoProjectCompilationFailedException;

import java.io.*;
import java.lang.module.Configuration;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        // build our java compiler command
        List<String> javaCompilerCommand = new ArrayList<String>();
        javaCompilerCommand.add("%s".formatted(configurationParser.getEspressoProjectConfiguration().getJavaDetails().getJdkPath()));
        javaCompilerCommand.add("-verbose");
        javaCompilerCommand.add("-Werror");
        javaCompilerCommand.add("-d");
        javaCompilerCommand.add("bin");
        javaCompilerCommand.add("-classpath");
        javaCompilerCommand.add("\".espresso/jars/*\"");
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
        logger.info("running javac: `%s`".formatted(Arrays.toString(javaCompilerCommand)));

        // run the command
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(javaCompilerCommand);
        processBuilder.directory(new File(System.getProperty("user.dir")));
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            logger.info("javac out ->   %s".formatted(line));
        }
        int exitCode = process.waitFor();

        // handle our exit code
        if (!(exitCode == 0)) {
            throw new EspressoProjectCompilationFailedException(exitCode);
        }
        logger.info("compilation complete");
    }

    /**
     * Writes a manifest file to the `bin` directory
     */
    public void createManifest() throws IOException {
        // create the manifest directory
        File manifestDirectory = new File("bin/META-INF");
        manifestDirectory.mkdirs();

        // create the manifest
        File manifest = new File("bin/META-INF/MANIFEST.MF");
        manifest.delete();
        manifest.createNewFile();
        try (FileWriter manifestWriter = new FileWriter(manifest)) {
            manifestWriter.append("Manifest-Version: 1.0");
            manifestWriter.append("Main-CLass: xyz.hlafaille.project.Main");
            // todo add lib directory to bin:
        }
    }
}
