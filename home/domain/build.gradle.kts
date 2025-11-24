plugins {
    id("project-java-library")
    alias(libs.plugins.kotlin.jvm)
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

ktlint {
    version.set("1.7.1")
    android.set(true)
}

detekt {
    config.setFrom("$projectDir/../../detekt.yml")
}

dependencies {
    implementation(projects.architecture.domain)

    implementation(projects.coroutine)

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito.kotlin)
}
