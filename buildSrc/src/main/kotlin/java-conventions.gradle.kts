import org.gradle.api.internal.artifacts.configurations.ResolutionStrategyInternal

plugins {
    `java-library`
    `jvm-test-suite`
    jacoco
    `jacoco-report-aggregation`
    `maven-publish`
}

sourceSets {
    create("stepdefs") {
        java {
            srcDir("src/$name/java")
            compileClasspath += sourceSets.main.get().output + sourceSets.main.get().compileClasspath
            runtimeClasspath += sourceSets.main.get().output + sourceSets.main.get().runtimeClasspath
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    registerFeature("stepdefs") {
        usingSourceSet(sourceSets["stepdefs"])
        withJavadocJar()
        withSourcesJar()
    }

    consistentResolution {
        useCompileClasspathVersions()
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }

        register<JvmTestSuite>("functionalTest") {
            testType.set(TestSuiteType.FUNCTIONAL_TEST)
            dependencies {
                implementation(project())
                implementation(project()) {
                    capabilities {
                        requireCapabilities("${project.group}:${project.name}-stepdefs")
                    }
                }
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
        }
    }
}

configurations {
    compileClasspath {
        resolutionStrategy.activateDependencyLocking()
    }
    runtimeClasspath {
        resolutionStrategy.activateDependencyLocking()
    }
    annotationProcessor {
        resolutionStrategy.activateDependencyLocking()
    }
    "testCompileClasspath" {
        resolutionStrategy.activateDependencyLocking()
    }
    "testRuntimeClasspath" {
        resolutionStrategy.activateDependencyLocking()
    }
    "stepdefsCompileClasspath" {
        resolutionStrategy.activateDependencyLocking()
    }
    "stepdefsRuntimeClasspath" {
        resolutionStrategy.activateDependencyLocking()
    }
    "functionalTestCompileClasspath" {
        resolutionStrategy.activateDependencyLocking()
    }
    "functionalTestRuntimeClasspath" {
        resolutionStrategy.activateDependencyLocking()
    }
}

dependencies {
    implementation(platform(project(":${rootProject.name}-dependencies")))
    testImplementation(platform(project(":${rootProject.name}-dependencies")))
    "functionalTestImplementation"(platform(project(":${rootProject.name}-dependencies")))
    "stepdefsImplementation"(platform(project(":${rootProject.name}-dependencies")))
}

publishing {
    publications {
        create("mavenJava", MavenPublication::class.java) {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}

tasks {
    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }

    named("check") {
        dependsOn(testing.suites.named("functionalTest"))
    }

    register("resolveAndLockAll") {
        group = "help"
        doFirst {
            require(gradle.startParameter.isWriteDependencyLocks)
        }
        doLast {
            configurations.filter {
                // Add any custom filtering on the configurations to be resolved
                it.isCanBeResolved && (it.resolutionStrategy as ResolutionStrategyInternal).isDependencyLockingEnabled
            }.forEach { it.resolve() }
        }
    }
}
