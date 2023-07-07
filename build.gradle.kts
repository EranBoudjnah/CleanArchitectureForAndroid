plugins {
    id("com.android.application") version "8.1.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.0" apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}
