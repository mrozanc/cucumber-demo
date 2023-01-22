plugins {
    `java-platform`
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("mavenJavaPlatform") {
            from(components["javaPlatform"])
        }
    }
}
