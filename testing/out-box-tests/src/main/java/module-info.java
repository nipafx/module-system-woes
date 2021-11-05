// If there's no module declaration in src/main/java,
// Maven compiler plugin throws an error:
//
// > Can't compile test sources when main sources are missing a module descriptor
//
// Can only be fixed with this specific module name (beware Poe's Law)
module PLEASE_OH_MAVEN_HEAR_MY_PRAYERS { }
