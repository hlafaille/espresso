package xyz.hlafaille.espresso;

import xyz.hlafaille.espresso.configuration.ConfigurationParser;
import xyz.hlafaille.espresso.dependency.DependencyResolver;
import xyz.hlafaille.espresso.util.LogFormatter;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        LogFormatter.configureLogger();
        ConfigurationParser configurationParser = new ConfigurationParser();
        DependencyResolver dependencyResolver = new DependencyResolver();
        dependencyResolver.resolveDependency("com.google.code.gson", "gson", "2.10.1");
    }
}