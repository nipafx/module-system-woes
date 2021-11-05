# Module System Woes

In a quest to make the module system more approachable, I gathered common problems developers have with it (on [Twitter](https://twitter.com/nipafx/status/1405765007200030720) and [Reddit](https://www.reddit.com/r/java/comments/o2jeiv/your_problems_with_java_modules/)) and plan to address them here.
This is still in very early stages...


## 99 Problems

This repo describes and provides solutions (hopefully) for the following problems:

* [split packages](split-packages)
* [testing](testing)


## General Comments

* "Having `-jar some.jar` is easier to tell the people to use instead of `-p some.jar -m some`. One fix uses a JLinked image and a launcher, but not running the library is unfortunate. Maybe -mjar like the -jam from here?"
	* https://openjdk.java.net/projects/modules/jdktools.html
* MR-JAR support in IDEs, build tools, testing frameworks, etc.
* with users being able to use the class path, does strong encapsulation even matter?
