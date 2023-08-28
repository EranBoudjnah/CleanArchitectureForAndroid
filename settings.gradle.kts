pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WhoAmI"
include(":app")
include(":datasource-architecture")
project(":datasource-architecture").projectDir = File("datasource/architecture")
include(":datasource-implementation")
project(":datasource-implementation").projectDir = File("datasource/implementation")
include(":datasource-source")
project(":datasource-source").projectDir = File("datasource/source")

include(":analytics")
include(":time")
include(":coroutine")
include(":coroutine-test")
include(":widget")

setOf(
    "ui",
    "instrumentation-test",
    "presentation",
    "presentation-test",
    "domain"
).forEach { module ->
    include(":architecture-$module")
    project(":architecture-$module").projectDir = File("architecture/$module")
}

setOf("ui", "presentation", "domain", "data").forEach { layer ->
    include(":home-$layer")
    project(":home-$layer").projectDir = File("home/$layer")
}

setOf("ui", "presentation", "domain", "data").forEach { layer ->
    include(":history-$layer")
    project(":history-$layer").projectDir = File("history/$layer")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
