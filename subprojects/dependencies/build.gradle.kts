plugins {
    `java-platform-conventions`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(platform(project(":${rootProject.name}-bom")))
    api(platform(libs.assertj.bom))
    api(platform(libs.cucumber.bom))
    api(platform(libs.junit.bom))
    constraints {
        api(libs.guava)
    }
}
