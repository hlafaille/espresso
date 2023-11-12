Espresso is an opinionated build tool for Java. It does things how the developers think things should be done. There are
a few reasons for this.

1. Well-defined rules in the beginning lead to predictable ends
    * In my personal opinion, build tools such as Gradle are very powerful. So powerful that the documentation is a
      clusterfuck and all the examples for how to handle things like discrepancies are all outdated.
    * For example, on November 9th 2023, I spent a few hours trying to figure out how to handle discrepancies during
      my build task and just gave up.
    * In Espresso, this will have a sensible default option that can be configured, shall the user want it. With the
      single-build-file using Java as its language, it will allow for rich code completion, which
      Gradle does not have (in my experience with the Kotlin and Groovy DSLs).
2.  Simplicity is invaluable
    * Espresso will remain daemon-less, instead opting for filesystem caches for determining which files should be 
      recompiled.