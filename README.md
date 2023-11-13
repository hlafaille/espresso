# Espresso
A light weight, opinionated build tool for Java. Because Gradle and Maven don't have opinions.

# Planned Commands
Currently, Espresso does not have a command line interface. I am building out classes that handle functionality first.
* `espresso init`
  * Initialize a new project by creating the following:
    * `.espresso/`
    * `.espresso/jars`
    * `.espresso/espresso.json5`
* `espresso install`
  * Search Maven Repository for dependencies through the CLI. Imagine how `flatpak install` works currently
* `espresso pull`
  * Pull all dependencies, regardless of local status
* `espresso build`
  * Build a `javac` command with all the required arguments for building class files
  * Assemble the class files in a `.jar` file in the newly created `build` folder in the root of the project
