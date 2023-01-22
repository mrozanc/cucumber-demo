plugins {
    `java-library`
    `jvm-test-suite`
    jacoco
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
        }
    }
}

tasks {
    named("check") {
        dependsOn(testing.suites.named("functionalTest"))
    }
}
