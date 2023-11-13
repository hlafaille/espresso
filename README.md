# Espresso
A light weight, opinionated build tool for Java. Because Gradle and Maven don't have opinions.

# Features
* Simple `.json5` file for declaring dependencies
  * Run `espresso init` to begin a basic espresso project
* Works with MavenCentral and other Maven based repositories
  * Espresso will handle the rest when you call `espresso build`
* Search for dependencies by using `espresso search`
  * For example, running `espresso search gson` will search Maven Repository for `gson` artifacts. 