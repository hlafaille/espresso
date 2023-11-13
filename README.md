# Espresso
A light weight, opinionated build tool for Java. Because Gradle and Maven don't have opinions.

# Features
* Simple `.json5` file for declaring dependencies
  * Run `espresso init` to begin an Espresso project
* Works with MavenCentral and other Maven based repositories
  * Espresso will handle the rest when you call `espresso build`
* Search for dependencies by using `espresso install`
  * For example, running `espresso install gson` will search Maven Repository for `gson` artifacts and automatically 
    add it to your `espresso.json5` file.  