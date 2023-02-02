import org.gradle.api.internal.artifacts.configurations.ResolutionStrategyInternal

plugins {
    id("java-library")
    id("io.freefair.lombok")
    id("jvm-test-suite")
    id("jacoco")
    id("jacoco-report-aggregation")
    id("maven-publish")
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
    repositories {
        maven {
            name = "GitLab"
            url = uri("${System.getenv("CI_API_V4_URL")}/projects/${System.getenv("CI_PROJECT_ID")}/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Deploy-Token"
                value = System.getenv("CI_JOB_TOKEN")
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
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
