plugins {
    id("java-conventions")
}

dependencies {
    testImplementation(libs.assertj.core)
    testImplementation(libs.junit.jupiter)

    stepdefsImplementation(libs.cucumber.picocontainer)
    stepdefsImplementation(libs.assertj.core)
    stepdefsImplementation(libs.cucumber.java)

    functionalTestImplementation(libs.cucumber.junit.platform.engine)
    functionalTestImplementation(libs.junit.platform.suite)
}
