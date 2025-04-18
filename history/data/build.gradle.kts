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
    implementation(projects.history.domain)
    implementation(projects.architecture.domain)

    implementation(projects.datasource.architecture)
    implementation(projects.datasource.source)

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.hamcrest)
    testImplementation(libs.test.kotlinx.coroutines)
    testImplementation(projects.coroutineTest)
}
