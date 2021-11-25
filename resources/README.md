# Resources

General requirements:

* works with modules
* works without modules
* works on Java <9

TODO: JavaFX?

## Within JAR / module

Requirements:

* retrieving some resource (a text file, an image, etc) from own JAR

Solution:

TODO


## Depending on module

Requirements:

* retrieving some resource (a text file, an image, etc) from a JAR
* across module boundaries

Solution:

* opening packages with resources
* user of resource reads (usually `requires`) module containing the resource
* use `Class::getResource` et al.

TODO


## Without dependencies

Requirements:

* retrieving some resource (a text file, an image, etc) from a JAR
* across module boundaries
* without depending on module containing resources

For `ResourceBundle`:
* Gunnar's blog post
* maybe minus the creative usage of Locale variants for different bundles

TODO
