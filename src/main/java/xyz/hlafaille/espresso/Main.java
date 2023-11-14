package xyz.hlafaille.espresso;

import xyz.hlafaille.espresso.build.BuildHandler;
import xyz.hlafaille.espresso.configuration.ConfigurationParser;
import xyz.hlafaille.espresso.configuration.ProjectStructureHandler;
import xyz.hlafaille.espresso.configuration.dto.EspressoProjectConfiguration;
import xyz.hlafaille.espresso.dependency.DependencyResolver;
import xyz.hlafaille.espresso.exception.EspressoProjectCompilationFailedException;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.util.LogFormatter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This will eventually be replaced with a proper CLI. It doesn't make sense to develop the CLI and different modules
 * together, as they will inevitably be too close together for their own good. Developing them largely separately
 * will allow this project to come together using building block style development.
 */
public class Main {
    public static void main(String[] args) {
    }
}