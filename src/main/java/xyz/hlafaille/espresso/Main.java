package xyz.hlafaille.espresso;

import xyz.hlafaille.espresso.configuration.ConfigurationParser;
import xyz.hlafaille.espresso.util.LogFormatter;

import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        LogFormatter.configureLogger();
        ConfigurationParser configurationParser = new ConfigurationParser();
    }
}