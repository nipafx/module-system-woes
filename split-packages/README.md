# Split Packages

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

## Workaround: Patch module

Approach:

* use `--patch-module` to merge all JARs into the same module at compile and run time
* change scope of all merged JARs to runtime to remove them from class path

But:

* requires removal of all `requires` directives for the merged JARs from module declaration
	* e.g. if Lucene Analyzer is merged into Lucene Core, `requires lucene.analyzers.common` needs to be removed
	* ~> IDEs will complain that you use packages from a module that's not required
* requires same command line flag at run time
	* ~> makes the solution non-portable and should only be used if you have control over the artifact's run environment

## Ideas

* local shaded copy
* leave splitting artifacts on class path
