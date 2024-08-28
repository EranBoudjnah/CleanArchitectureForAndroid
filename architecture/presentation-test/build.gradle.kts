plugins {
    id("project-java-library")
    alias(libs.plugins.kotlin.jvm)
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

ktlint {
    version.set("0.49.1")
    android.set(true)
}

detekt {
    config.setFrom("$projectDir/../../detekt.yml")
}

dependencies {
    implementation(projects.architecture.presentation)
    implementation(projects.architecture.domain)

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.test.junit)
    implementation(libs.test.mockito.kotlin)
    implementation(libs.test.kotlinx.coroutines)
    implementation(projects.coroutineTest)
}
