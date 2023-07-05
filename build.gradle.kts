plugins {
    id("com.android.application") version "8.1.0-beta04" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.0" apply false
    @Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}
