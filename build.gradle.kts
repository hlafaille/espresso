import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

application {
    mainClass.set("xyz.hlafaille.espresso.Main")
}


repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api("org.projectlombok:lombok:1.18.28")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.mockito:mockito-core:5.7.0")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    api("com.jayway.jsonpath:json-path:2.8.0")
    api("com.google.code.gson:gson:2.10.1")
    api("commons-io:commons-io:2.15.0")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set("espresso.jar")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.github.csolem.gradle.shadow.kotlin.example.App"))
        }
    }
}


tasks.test {
    useJUnitPlatform()
}

tasks {
    build {
        dependsOn(shadowJar)
        // ~/.jdks/openjdk-21.0.1/bin/jpackage --input ~/EspressoBuild --main-jar espresso.jar --type app-image --name espresso
    }
}

// ~/.jdks/corretto-17.0.9/bin/jpackage --input ~/EspressoBuild --main-jar espresso.jar --type app-image --name espresso
task<Exec>("buildJpackageAppImage") {
    commandLine("rm -rf espresso")
    commandLine("jpackage", "--input", "~/EspressoBuild", "--main-jar",
            "espresso.jar", "--type", "app-image", "--name", "espresso"
    );
}

group = "xyz.hlafaille.espresso"
version = "1.0-SNAPSHOT"
description = "buildwithespresso"
java.sourceCompatibility = JavaVersion.VERSION_17
