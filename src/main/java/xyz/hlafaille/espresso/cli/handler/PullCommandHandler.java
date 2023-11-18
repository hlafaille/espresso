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
            String groupId;
            for (int i = 0; i < groupMap.keySet().size(); i++) {
                groupId = groupMap.keySet().stream().toList().get(i);
                for (EspressoProjectConfiguration.Artifact artifact : groupMap.get(groupId).getArtifacts()) {
                    getLogger().info("[%s/%s] %s v%s from '%s'".formatted(i+1, groupMap.keySet().size(), artifact.getArtifactId(), artifact.getVersion(), groupId));
                    projectManager.pullDependency(groupId, artifact.getArtifactId(), artifact.getVersion());
                }
            }
        } catch (IOException | EspressoProjectIntegrityCompromisedException e) {
            getLogger().severe(e.getMessage());
            return 1;
        }
        getLogger().info("Done!");
        return 0;
    }
}
