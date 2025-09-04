pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WhoAmI"
include("app")
include("datasource:architecture")
include("datasource:implementation")
include("datasource:source")

include("analytics")
include("time")
include("coroutine")
include("coroutine-test")
include("widget")

setOf(
    "ui",
    "instrumentation-test",
    "presentation",
    "presentation-test",
    "domain"
).forEach { module ->
    include("architecture:$module")
}

setOf("ui", "presentation", "domain", "data").forEach { layer ->
    include("home:$layer")
}

setOf("ui", "presentation", "domain", "data").forEach { layer ->
    include("history:$layer")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
