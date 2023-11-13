package xyz.hlafaille.espresso.configuration.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the dependencies block in the json file
 */
@Getter
@Setter
public class Dependencies {
    private DependencyGroup[] dependencyGroups;
}