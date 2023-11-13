package xyz.hlafaille.espresso.configuration.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the details block in the json file
 */
@Getter
@Setter
public class Details {
    private String name;
    private String author;
    private String version;
}