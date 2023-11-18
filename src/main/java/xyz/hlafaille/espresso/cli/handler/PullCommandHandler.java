package xyz.hlafaille.espresso.cli.handler;


import xyz.hlafaille.espresso.cli.Command;
import xyz.hlafaille.espresso.cli.CommandHandler;
import xyz.hlafaille.espresso.exception.EspressoProjectIntegrityCompromisedException;
import xyz.hlafaille.espresso.project.ProjectManager;
import xyz.hlafaille.espresso.project.dto.EspressoProjectConfiguration;

import java.io.IOException;
import java.util.Map;

public class PullCommandHandler extends CommandHandler {
    public PullCommandHandler() {
        super(false);
    }

    @Override
    public Integer execute(String input) {
        getLogger().info("Pulling dependencies");
        try {
            ProjectManager projectManager = ProjectManager.getInstance();
            Map<String, EspressoProjectConfiguration.Group> groupMap = projectManager.getEspressoProjectConfiguration().getDependencies();
            for (String groupId : groupMap.keySet()) {
                for (EspressoProjectConfiguration.Artifact artifact : groupMap.get(groupId).getArtifacts()) {
                    getLogger().info("... %s v%s from '%s'".formatted(artifact.getArtifactId(), artifact.getVersion(), groupId));
                    projectManager.pullDependency(groupId, artifact.getArtifactId(), artifact.getVersion());
                }
            }
        } catch (IOException | EspressoProjectIntegrityCompromisedException e) {
            getLogger().severe(e.getMessage());
            return 1;
        }
        getLogger().info("Dependencies pulled");
        return 0;
    }
}
