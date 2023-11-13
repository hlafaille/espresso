package xyz.hlafaille.espresso.configuration.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents an espresso.json config file
 */
@Getter
@ToString
public class ConfigurationFileDto {
    private Details details;
    private Object dependencies;

    /**
     * Represents the details block in the json file
     */
    @Getter
    static class Details {
        private String name;
        private String author;
        private String version;
    }

    /**
     * Represents the dependencies block in the json file
     */
    @Getter
    static class Dependencies {
        private DependencyGroup[] dependencyGroups;

        /**
         * Represents an individual dependency group. For example, `com.google.code.gson`
         */
        static class DependencyGroup {
            private String groupId;
            private Artifact[] artifacts;
        }

        /**
         * Represents an individual artifact under a Group ID. For example, `gson:2.8.9`.
         */
        @Getter
        static class Artifact {
            private String artifactId;
            private String version;
        }
    }
}
