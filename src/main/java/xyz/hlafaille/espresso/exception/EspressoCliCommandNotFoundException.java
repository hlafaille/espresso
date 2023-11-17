package xyz.hlafaille.espresso.exception;

public class EspressoCliCommandNotFoundException extends Exception {
    public EspressoCliCommandNotFoundException(String name) {
        super("Command '%s' could not be found".formatted(name));
    }
}
