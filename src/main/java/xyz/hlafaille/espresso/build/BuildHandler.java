package xyz.hlafaille.espresso.build;

import xyz.hlafaille.espresso.Main;
import xyz.hlafaille.espresso.configuration.ProjectStructureHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

/**
 * Handles building and packaging the project into a nice, neat .jar file (with a manifest!)
 */
public class BuildHandler {
    private final ProjectStructureHandler projectStructureHandler = new ProjectStructureHandler();
    private final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Build the javac command
     *
     * @return Javac command that can be passed to a Runtime Process
     */
    public String buildJavaCompilerCommand() throws URISyntaxException, IOException {
        // iterate over the source file paths, get their absolute path
        List<Path> sourceFilePaths = projectStructureHandler.getSourceFiles();

        // todo add options

        // build our java compiler command
        StringBuilder javaCompilerCommand = new StringBuilder("javac ");
        for (Path sourceFilePath : sourceFilePaths) {
            javaCompilerCommand.append(sourceFilePath.toAbsolutePath()).append(" ");
        }
        return javaCompilerCommand.toString();
    }
}
