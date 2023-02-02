plugins {
    id("java-platform")
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("mavenJavaPlatform") {
            from(components["javaPlatform"])
        }
    }
}
