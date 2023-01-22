rootProject.name = "cucumber-demo"

file("$rootDir/subprojects")
    .listFiles { f: File -> f.isDirectory }
    ?.forEach { subProjectDir ->
        val path = ":${rootProject.name}-${subProjectDir.name}"
        include(path)
        project(path).projectDir = subProjectDir
    }
