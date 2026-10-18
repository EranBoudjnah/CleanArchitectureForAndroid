plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

ktlint {
    version.set("1.7.1")
    android.set(true)
}

detekt {
    config.setFrom("$projectDir/../../detekt.yml")
}

dependencies {
    implementation(projects.coroutine)

    implementation(libs.kotlinx.coroutines.core)
}
