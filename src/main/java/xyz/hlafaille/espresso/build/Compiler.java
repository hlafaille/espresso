package xyz.hlafaille.espresso.build;

import java.io.File;

/**
 * Handles mirroring the source directory file structure to the build directory,
 */
public class Compiler {

    /**
     * Ensures that the build directory's file structure matches the source file structure
     */
    public void ensureMirroredBuildDirectoryFileStructure() {
    }

    /**
     * Search the src directory for .java source files
     * @return Array of File objects, each of them being a .java file in the src directory
     */
    public File[] findSourceFiles() {
        return null;
    }

    /**
     * Call `javac` on provided files
     * @param files Array of File objects
     */
    public void compileFiles(File[] files) {
    }


}
