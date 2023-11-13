package xyz.hlafaille.espresso.exception;

/**
 * Represents a scenario in which the Java Compiler (javac) command exited with a status code that is non-zero
 */
public class EspressoProjectCompilationFailedException extends Exception {
    public EspressoProjectCompilationFailedException(int exitCode) {
        super("Java Compiler exited with exit code: " + exitCode);
    }
}
