plugins {
    id("com.android.application") version "8.2.1" apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.4" apply false
    alias(libs.plugins.kotlin.jvm) apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.plugin.oss.licenses)
    }
}
