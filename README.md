# Cucumber Demo, Gradle version
This project setup provides a full setup of Cucumber allowing:
 * publication of step definitions by project
   * for reuse of the step definitions related to a specific component in other tests
 * dedicated functional test setup by project
   * to avoid having huge dependencies requirements on an integration test module that would make it rebuilt more than
     necessary if the build cache is enabled.

When working with multi-modules projects, this setup allows to split the step definitions as separated libraries
that are reusable as test features under the `stepdefs` [capability](https://docs.gradle.org/current/userguide/feature_variants.html#sec::consuming_feature_variants).

The other benefit of this setup is that if incremental build and build cache are enabled,
the build time may be drastically reduced if building everything is not required.

The test execution is located under the `functionalTest` source set and
uses a [test suite](https://docs.gradle.org/current/userguide/jvm_test_suite_plugin.html#header).

To automatically get this setup on any project of the build, we just have to apply the
build script plugin from [java-conventions.gradle.kts](buildSrc/src/main/kotlin/java-conventions.gradle.kts):
```kotlin
plugins {
    `java-conventions`
}
```
