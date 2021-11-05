# Testing

Testing code that comes in modules isn't always straight forward.
Here are a few ways that work for different testing purposes.


## Unit Tests / In-Box Tests

These are "classic" unit tests that want to test a module's internals, e.g. package-private classes and methods.

Requirements:

* access to package-local classes/members
* test dependencies

### Class path testing

One approach is to ignore modules during test execution and place everything on the class path.
This of course leads to a divergence between test and production environments, which is usually not great, although it may not matter much for real _unit_ tests, which don't cross module boundaries (or otherwise interact with the module system).
Then again, this is not straight-forward to configure as for example Maven will always run tests of modular code from the module path.

### Test Module

Putting the tests into their own module won't work because in order to test package visible classes, the tests need to be in the same package, which immediately creates a [split package](../split-package) situation between the code under test and the tests.

### Command Line Flags

Adding lots of command line flags will do the trick:

* to access module internals, add test code to the module under test during compilation end execution with `--patch-module`
* to get test dependencies, add them with `--add-modules` and `--add-reads` or allow the tested module to read the class path content with `--add-reads=...=ALL-UNNAMED`
* to allow the testing framework to discover the tests with reflection `--add-opens` for all packages that contain tests

You can see this approach in [system-under-test](system-under-test), which has a bunch of tests for package-local classes in [its test source tree](system-under-test/src/test/java).

IntelliJ and Maven do this out of the box.

#### IntelliJ

These are the relevant arguments that IntelliJ adds to the `java` executable for running the tests:

```shell
# adds the test classes to the tested module
--patch-module dev.nipafx.module_woes.system_under_test=/home/nipa/code/module-system-woes/testing/system-under-test/target/test-classes
# lets the tested module (and this the test code) read the class path for access to the test dependencies
--add-reads dev.nipafx.module_woes.system_under_test=ALL-UNNAMED
# gives the test dependencies on the class path reflective access to the test code
--add-opens dev.nipafx.module_woes.system_under_test/dev.nipafx.module_woes.testing.api=ALL-UNNAMED
# add the tested module to the module graph (won't be resolved because IntelliJ launcher doesn't depend on it)
--add-modules dev.nipafx.module_woes.system_under_test

# place all test dependencies on the class path
-classpath /home/nipa/.m2/repository/org/junit/platform/junit-platform-launcher/1.8.1/junit-platform-launcher-1.8.1.jar:/home/nipa/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-1/213.5281.15/lib/idea_rt.jar:/home/nipa/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-1/213.5281.15/plugins/junit/lib/junit5-rt.jar:/home/nipa/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-1/213.5281.15/plugins/junit/lib/junit-rt.jar:/home/nipa/code/module-system-woes/testing/system-under-test/target/test-classes:/home/nipa/.m2/repository/org/junit/jupiter/junit-jupiter/5.8.1/junit-jupiter-5.8.1.jar:/home/nipa/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.8.1/junit-jupiter-api-5.8.1.jar:/home/nipa/.m2/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar:/home/nipa/.m2/repository/org/junit/platform/junit-platform-commons/1.8.1/junit-platform-commons-1.8.1.jar:/home/nipa/.m2/repository/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar:/home/nipa/.m2/repository/org/junit/jupiter/junit-jupiter-params/5.8.1/junit-jupiter-params-5.8.1.jar:/home/nipa/.m2/repository/org/junit/jupiter/junit-jupiter-engine/5.8.1/junit-jupiter-engine-5.8.1.jar:/home/nipa/.m2/repository/org/junit/platform/junit-platform-engine/1.8.1/junit-platform-engine-1.8.1.jar
# place the tested module on the module path
-p /home/nipa/code/module-system-woes/testing/system-under-test/target/classes
```

#### Maven

Maven does pretty much the same:

```shell
[DEBUG] args file content:
--module-path
"/home/nipa/code/module-system-woes/testing/system-under-test/target/classes"
--class-path
"/home/nipa/.m2/repository/org/apache/maven/surefire/surefire-booter/3.0.0-M5/surefire-booter-3.0.0-M5.jar:/home/nipa/.m2/repository/org/apache/maven/surefire/surefire-api/3.0.0-M5/surefire-api-3.0.0-M5.jar:/home/nipa/.m2/repository/org/apache/maven/surefire/surefire-logger-api/3.0.0-M5/surefire-logger-api-3.0.0-M5.jar:/home/nipa/.m2/repository/org/apache/maven/surefire/surefire-shared-utils/3.0.0-M4/surefire-shared-utils-3.0.0-M4.jar:/home/nipa/.m2/repository/org/apache/maven/surefire/surefire-extensions-spi/3.0.0-M5/surefire-extensions-spi-3.0.0-M5.jar:/home/nipa/code/module-system-woes/testing/system-under-test/target/test-classes:/home/nipa/.m2/repository/org/junit/jupiter/junit-jupiter/5.8.1/junit-jupiter-5.8.1.jar:/home/nipa/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.8.1/junit-jupiter-api-5.8.1.jar:/home/nipa/.m2/repository/org/opentest4j/opentest4j/1.2.0/opentest4j-1.2.0.jar:/home/nipa/.m2/repository/org/junit/platform/junit-platform-commons/1.8.1/junit-platform-commons-1.8.1.jar:/home/nipa/.m2/repository/org/apiguardian/apiguardian-api/1.1.2/apiguardian-api-1.1.2.jar:/home/nipa/.m2/repository/org/junit/jupiter/junit-jupiter-params/5.8.1/junit-jupiter-params-5.8.1.jar:/home/nipa/.m2/repository/org/junit/jupiter/junit-jupiter-engine/5.8.1/junit-jupiter-engine-5.8.1.jar:/home/nipa/.m2/repository/org/junit/platform/junit-platform-engine/1.8.1/junit-platform-engine-1.8.1.jar:/home/nipa/.m2/repository/org/apache/maven/surefire/surefire-junit-platform/3.0.0-M5/surefire-junit-platform-3.0.0-M5.jar:/home/nipa/.m2/repository/org/apache/maven/surefire/common-java5/3.0.0-M5/common-java5-3.0.0-M5.jar:/home/nipa/.m2/repository/org/junit/platform/junit-platform-launcher/1.8.1/junit-platform-launcher-1.8.1.jar"
--patch-module
dev.nipafx.module_woes.system_under_test="/home/nipa/code/module-system-woes/testing/system-under-test/target/test-classes"
--add-exports
dev.nipafx.module_woes.system_under_test/dev.nipafx.module_woes.testing.api=ALL-UNNAMED
--add-modules
dev.nipafx.module_woes.system_under_test
--add-reads
dev.nipafx.module_woes.system_under_test=ALL-UNNAMED
org.apache.maven.surefire.booter.ForkedBooter
```

The `--add-exports` is questionable because it's not enough - testing frameworks need deeper access than that.
So unfortunately, Maven requires manual `--add-opens` (in the Surefire plugin configuration) for all packages that contain tests.


## Integration Tests / Out-Box Tests

This is for tests that verify the behavior of a public API, typically by executing larger parts or even the whole system.

Requirements:

* access to public API
* test dependencies

This one is pretty straight-forward:
The test code can be its own proper module:

* open module to allow reflective access by test framework
* requires module under test
* requires test dependencies

This works as expected as can be seen in [out-box-tests](out-box-tests) (for this project to work either `mvn install` [system-under-test](system-under-test) first or run Maven from this folder).
