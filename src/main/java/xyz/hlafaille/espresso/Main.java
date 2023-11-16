package xyz.hlafaille.espresso;


import xyz.hlafaille.espresso.cli.ArgumentParser;

public class Main {
    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser("Espresso", "Build with Espresso. Because Maven and Gradle don't have opinions.");
        argumentParser.parse(args);
    }
}