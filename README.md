# Module System Woes

In a quest to make the module system more approachable, I gathered common problems developers have with it (on [Twitter](https://twitter.com/nipafx/status/1405765007200030720) and [Reddit](https://www.reddit.com/r/java/comments/o2jeiv/your_problems_with_java_modules/)) and plan to address them here.
This is still in very early stages...


## 99 Problems

I compiled this list from the Twitter replies (didn't go through Reddit yet) and hope to address them at some point.

### Mentioned Repeatedly

* resources
	* https://twitter.com/realTomFi/status/1406146695835029506
	* https://mail.openjdk.java.net/pipermail/jigsaw-dev/2019-August/014280.html
	* https://livebook.manning.com/book/the-java-module-system/chapter-5/60
* how to test (@sormuras)

### Mentioned Once

* unsuitable automatic module names
	* ~> Moditect
	* ~> "An ugly fix to this is create an empty jar with name that the module system likes, and use the --patch-module to the original jar" (but won't Maven leave the original JAR on the module path?)
* uber/fat JARs
* native images
* split packages
* annotation processors
	* https://github.com/schnatterer/moby-names-generator-java/commit/dbd710121f33c22df501103a6434690afbf190c5
* class vs module path
* Java 8 project with module descriptor
	* ~> assemble descritor with Moditect
* custom class loading
* class in default package
* JavaFX
	* https://andresalmiray.com/building-a-layered-modular-java-application-watch-out-for-these/
	* exception swallowed by JavaFX classes?
		* https://twitter.com/lanthale/status/1405776799724806144
	* https://github.com/javafxports/openjdk-jfx/issues/236#issuecomment-426583174
	* resources, e.g. FXML, CSS

### Comment

* "Having `-jar some.jar` is easier to tell the people to use instead of `-p some.jar -m some`. One fix uses a JLinked image and a launcher, but not running the library is unfortunate. Maybe -mjar like the -jam from here?"
	* https://openjdk.java.net/projects/modules/jdktools.html
* MR-JAR support in IDEs, build tools, testing frameworks, etc.
* with users being able to use the class path, does strong encapsulation even matter?
