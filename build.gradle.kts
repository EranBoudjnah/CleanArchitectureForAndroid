import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.plugin.oss.licenses)
    }
}

subprojects {
    afterEvaluate {
        tasks {
            withType<KotlinCompile> {
                compilerOptions {
                    freeCompilerArgs.add("-Xskip-prerelease-check")
                }
            }
        }
    }
}
