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

rootProject.name = "Who Am I"
include(":app")
include(":datasource-architecture")
project(":datasource-architecture").projectDir = File("datasource/architecture")
include(":datasource-implementation")
project(":datasource-implementation").projectDir = File("datasource/implementation")
include(":datasource-source")
project(":datasource-source").projectDir = File("datasource/source")

include(":time")
include(":coroutine")
include(":coroutine-test")
include(":widget")
include(":architecture-instrumentation-test")
project(":architecture-instrumentation-test").projectDir = File("architecture/instrumentation-test")
include(":architecture-presentation")
project(":architecture-presentation").projectDir = File("architecture/presentation")
include(":architecture-presentation-test")
project(":architecture-presentation-test").projectDir = File("architecture/presentation-test")
include(":architecture-domain")
project(":architecture-domain").projectDir = File("architecture/domain")

setOf("ui", "presentation", "domain", "data").forEach { layer ->
    include(":home-$layer")
    project(":home-$layer").projectDir = File("home/$layer")
}

setOf("presentation", "domain", "data").forEach { layer ->
    include(":history-$layer")
    project(":history-$layer").projectDir = File("history/$layer")
}
