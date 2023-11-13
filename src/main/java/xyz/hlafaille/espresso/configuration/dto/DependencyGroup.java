package xyz.hlafaille.espresso.configuration.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents an individual dependency group. For example, `com.google.code.gson`
 */
@Getter
@Setter
public class DependencyGroup {
    private String groupId;
    private Artifact[] artifacts;
}