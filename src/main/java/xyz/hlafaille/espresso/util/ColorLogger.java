package xyz.hlafaille.espresso.util;

import lombok.Getter;


public class ColorLogger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private static final String PREFIX_WHITE = "\u001B[97m\u001B[1m";
    private static final String PREFIX_RED = "\u001B[38;5;9m\u001B[1m";

    @Getter
    private static final ColorLogger instance = new ColorLogger();

    public void info(String message) {
        for (String line : message.split("\n")) {
            System.out.println(PREFIX_WHITE + "INFO" + ANSI_RESET + "   " + line + ANSI_RESET);
        }
    }

    public void severe(String message) {
        for (String line : message.split("\n")) {
            System.out.println(PREFIX_RED + "ERROR" + ANSI_RESET + ANSI_RED + " " + line + ANSI_RESET);
        }
    }

}
