plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
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
    config.setFrom("$projectDir/../../detekt.yml")
}

dependencies {
    implementation(project(":architecture-domain"))
    implementation(project(":architecture-presentation"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.test.junit)
    implementation(libs.test.mockito.kotlin)
    implementation(libs.test.kotlinx.coroutines)
    implementation(project(":coroutine-test"))
}
