# Split Packages

We're using Lucene as an example to demonstrate a split package, but the Lucene code itself doesn't necessarily make sense - it doesn't matter because that part isn't important.
Focus on where types are used.

## Problem

Go into `problem`, build with `mvn compile`, and observe the error messages:

```
[ERROR] module lucene.core reads package org.apache.lucene.analysis.standard from both lucene.analyzers.common and lucene.core
[ERROR] module lucene.analyzers.common reads package org.apache.lucene.analysis.standard from both lucene.core and lucene.analyzers.common
[ERROR] /home/nipa/code/module-system-woes/split-packages/problem/src/main/java/module-info.java:
	[1,1] module dev.nipafx.module_woes.split_package reads package org.apache.lucene.analysis.standard from both lucene.analyzers.common and lucene.core
```


## Solution

Ask maintainers to mend the split package and/or provide uber JARs for their project that don't contain the split.


## Workarounds

There are a number of workarounds.
They're listed here roughly (!) in decreasing order of preference as things get more complicated and unwieldy the further down the list you get.

### Merge the modules

You can always merge the modules yourself:

* create a subproject (e.g. a Maven module) that...
	* depends on the splitting JARs
	* merges them (e.g. shading in Maven) without repackaging
	* potentially contains a module descriptor
* modular code then depends on that subproject
* alternatively, set the subproject up separately and install the JAR in your local Nexus

This workaround only works well for projects that don't publish their artifacts outside a local Nexus.
It is not demonstrated in this repo.

### Create a bridge module

A _bridge module_ is an automatic module that contains the code that depends on the split package.
Your modules then depend on the automatic module.
Its dependencies, among them the splitting JARs, can then be placed on the class path, where package splits are no problem.

This only works if at most one splitting JAR ends up on the module path, which in turn means that your modules can only ever make direct use of the same splitting JAR.
Consequently, the bridge module(s) may end up containing a lot or even most of functionality that deals with the splitting JARs.

This workaround is demonstrated in [workaround-bridge](workaround-bridge).

**FYI**: You need to at least `mvn package` because otherwise...
* the bridge module isn't packaged with a manifest, which defines its module name
* when compiling the other modules, Maven can't tell that the bridge module needs to be on the module path

### Read the unnamed module with `add-reads`

This workaround also relies on split packages not being a problem on the class path.
You remove all `requires` for splitting JARs from your modular code - consequently, the build tool should place them on class path.
To allow your code to read the splitting JARs (in fact, all JARs on the class path), add the command line flag `--add-reads=...=ALL-UNNAMED` at compile and run time.

Downsides:
* IDEs hate this and will shower you with compile errors because you're using code from a dependency that you don't require
* because `--add-reads` is required at run time, only projects that don't publish their artifacts can make use of this (or they will make their users add the command line flag as well)

This workaround is demonstrated in [workaround-read-unnamed](workaround-read-unnamed).

### Patch the module

The module system's solution for the problem of split packages is the option `--patch-module`, but it's not ideal either:

* use `--patch-module` to merge all JARs into the same module at compile and run time
  (from here on, _merged JARs_ means all splitting JARs _except_ the one that contains the module their code was patched into)
* remove `requires` directives for the merged JARs from module declarations

Downsides:

* IDEs hate this and will shower you with compile errors because you're using code from a dependency that you don't require
* because `--patch-module` is required at run time, only projects that don't publish their artifacts can make use of this (or they will make their users add the command line flag as well)

This workaround is demonstrated in [workaround-patch](workaround-patch).
