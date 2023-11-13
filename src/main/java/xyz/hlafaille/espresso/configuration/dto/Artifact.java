package xyz.hlafaille.espresso.configuration.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an individual artifact under a Group ID. For example, `gson:2.8.9`.
 */
@Getter
@Setter
public class Artifact {
    private String artifactId;
    private String version;
}