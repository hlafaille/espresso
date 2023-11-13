package xyz.hlafaille.espresso.configuration.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * Represents an espresso.json config file
 */
@Getter
@ToString
public class ConfigFile {
    private Details details;
    private Dependencies dependencies;
}
