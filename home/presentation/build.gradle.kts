@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

ktlint {
    version.set("0.49.1")
    android.set(true)
}

detekt {
    config.setFrom("$projectDir/detekt.yml")
}

dependencies {
    implementation(project(":home-domain"))
    implementation(project(":architecture-presentation"))
    implementation(project(":architecture-domain"))

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(project(":architecture-presentation-test"))
    testImplementation(project(":coroutine-test"))
}
