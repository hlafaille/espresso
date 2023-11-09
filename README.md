# espresso / buildWithEspresso
A light weight, opinionated build tool for Java. Because Gradle and Maven don't have opinions.

# Features
* Describe your build process using a single `.java` file
  * Run `espresso init` to begin a basic espresso project
* Works with MavenCentral and other Maven based repositories
  * In your `build.java` file, simply call `EspressoRepositoryHandler.addRepository();`
  * Espresso will handle the rest when you call `espresso build`