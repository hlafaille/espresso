package xyz.hlafaille.espresso.configuration.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Represents an espresso.json config file
 */
@Data
@AllArgsConstructor
public class EspressoProjectConfiguration {
    private Details details;
    private Map<String, Group> dependencies;
    private String[] repositories;

    @SerializedName("java")
    private JavaDetails javaDetails;

    @Data
    @AllArgsConstructor
    public static class JavaDetails {
        private String compilerPath;
        private String jarToolPath;
        private String mainClassPath;
    }

    @Data
    @AllArgsConstructor
    public static class Details {
        private String name;
        private String author;
        private String version;
    }

    @Data
    @AllArgsConstructor
    public static class Group {
        private List<Artifact> artifacts;
    }

    @Data
    @AllArgsConstructor
    public static class Artifact {
        private String artifactId;
        private String version;
        private boolean annotationProcessor;
    }
}
