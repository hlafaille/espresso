package xyz.hlafaille.espresso.build;

import org.apache.commons.io.FileUtils;
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
import java.util.zip.ZipOutputStream;

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
            logger.info("javac ->   %s".formatted(line));
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
        // get our main class
        String mainClass = configurationParser.getEspressoProjectConfiguration().getJavaDetails().getMainClass();

        // create the manifest directory
        File manifestDirectory = new File("bin/META-INF");
        manifestDirectory.mkdirs();

        // create the manifest
        File manifest = new File("bin/META-INF/MANIFEST.MF");
        manifest.delete();
        manifest.createNewFile();
        try (FileWriter manifestWriter = new FileWriter(manifest)) {
            manifestWriter.append("Manifest-Version: 1.0\n");
            manifestWriter.append("Main-Class: " + mainClass);
            // todo add lib directory to bin:
        }
        logger.info("manifest created with `%s` as main class".formatted(mainClass));
    }


    /**
     * Copies the dependencies from .espresso/jars into bin/libs
     */
    public void copyDependenciesToBinDirectory() throws URISyntaxException, IOException {
        // create the directory
        File libsDirectory = new File("bin/libs");
        libsDirectory.mkdirs();

        // get all the libs
        List<Path> jarPaths = projectStructureHandler.getJarLibraryFiles();

        // iterate over each jar lib, copy it
        File newJarFile;
        for (Path jar : jarPaths) {
            logger.info("copying lib '%s'".formatted(jar.getFileName()));
            newJarFile = new File(libsDirectory.toString() + "/" + jar.getFileName());
            FileUtils.copyFile(jar.toFile(), newJarFile);
        }
    }

    /**
     * Creates the .jar file. A .jar is effectively a .zip file.
     */
    public void createJarFromBinDirectory() throws IOException {
        // calculate what our source package is
        String[] splitSourcePackage = configurationParser.getEspressoProjectConfiguration().getJavaDetails().getMainClass().split("\\.");
        StringBuilder sourcePackageStringBuilder = new StringBuilder();
        for (int i = 0; i < splitSourcePackage.length - 1; i++) {
            sourcePackageStringBuilder.append(splitSourcePackage[i]).append("/");
        }
        sourcePackageStringBuilder.insert(0, System.getProperty("user.dir") + "/bin/");

        // establish our directories
        File libsDirectory = new File("bin/libs");
        File metaInfDirectory = new File("bin/META-INF");
        File srcPackage = new File(sourcePackageStringBuilder.toString());
        List<File> jarFiles = List.of(libsDirectory, metaInfDirectory, srcPackage);

        // build the zip


    }
}
