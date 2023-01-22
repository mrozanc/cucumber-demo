plugins {
    `java-platform-conventions`
}

dependencies {
    constraints {
        rootProject.subprojects
            .forEach { p ->
                p.pluginManager.withPlugin("java") {
                    api(project(p.path))
                }
            }
    }
}
