package xyz.hlafaille.espresso.configuration.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Represents an espresso.json config file
 */
@Data
public class EspressoProjectConfiguration {
    private Details details;
    private Map<String, Group> dependencies;
    private String[] repositories;

    @SerializedName("java")
    private JavaDetails javaDetails;

    @Data
    public static class JavaDetails {
        private String jdkPath;
    }

    @Data
    public static class Details {
        private String name;
        private String author;
        private String version;
    }

    @Data
    public static class Group {
        private List<Artifact> artifacts;
    }

    @Data
    public static class Artifact {
        private String artifactId;
        private String version;
    }
}
