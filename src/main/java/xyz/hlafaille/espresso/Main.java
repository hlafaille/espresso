package xyz.hlafaille.espresso;

import xyz.hlafaille.espresso.configuration.ConfigurationParser;
import xyz.hlafaille.espresso.configuration.ProjectHandler;
import xyz.hlafaille.espresso.configuration.dto.EspressoProjectConfiguration;
import xyz.hlafaille.espresso.dependency.DependencyResolver;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.util.LogFormatter;
import java.util.List;
import java.io.IOException;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws IOException, EspressoProjectIntegrityCompromisedException {
        LogFormatter.configureLogger();

        // create our project handler, this will ensure the .espresso dir exists
        ProjectHandler projectHandler = new ProjectHandler();

        // ensure the project integrity (.espresso should exist)
        projectHandler.createEspressoProject();

        // create the rest of our modules
        ConfigurationParser configurationParser = new ConfigurationParser();
        DependencyResolver dependencyResolver = new DependencyResolver();

        // filter out our dependencies
        EspressoProjectConfiguration projectConfiguration = configurationParser.getEspressoProjectConfiguration();
        Map<String, EspressoProjectConfiguration.Group> groupMap = projectConfiguration.getDependencies();

        // iterate over each dependency, try to resolve them
        EspressoProjectConfiguration.Group group;
        List<EspressoProjectConfiguration.Artifact> artifacts;
        for (String groupId : groupMap.keySet()) {
            // get our artifact group for iterating over its defined artifacts
            group = groupMap.get(groupId);
            artifacts = group.getArtifacts();

            // iterate over each artifact, resolve it
            for (EspressoProjectConfiguration.Artifact artifact : artifacts) {
                dependencyResolver.resolveDependency(
                        groupId,
                        artifact.getArtifactId(),
                        artifact.getVersion()
                );
            }
        }
    }
}