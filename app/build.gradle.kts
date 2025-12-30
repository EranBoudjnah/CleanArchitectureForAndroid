import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.compose.compiler)
    id("kotlin-parcelize")
}

kotlin {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_17)
}

android {
    namespace = "com.mitteloupe.whoami"
    compileSdk = libs.versions.compileSdk.get().toInt()

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.mitteloupe.whoami"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.mitteloupe.whoami.di.HiltTestRunner"
        javaCompileOptions.annotationProcessorOptions
            .arguments["dagger.hilt.disableCrossCompilationRootValidation"] = "true"

        vectorDrawables {
            useSupportLibrary = true
        }

        buildFeatures {
            resValues = true
        }
    }

    testBuildType = "espresso"

    buildTypes {
        debug {
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("espresso") {
            initWith(buildTypes["debug"])
            matchingFallbacks += listOf("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        animationsDisabled = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ktlint {
    version.set("0.50.0")
    android.set(true)
}

detekt {
    config.setFrom("$projectDir/../detekt.yml")
}

dependencies {
    implementation(projects.time)
    implementation(projects.coroutine)
    implementation(projects.datasource.architecture)
    implementation(projects.datasource.source)
    implementation(projects.datasource.implementation)

    implementation(projects.architecture.ui)
    implementation(projects.architecture.presentation)
    implementation(projects.architecture.domain)

    implementation(projects.home.ui)
    implementation(projects.home.presentation)
    implementation(projects.home.domain)
    implementation(projects.home.data)

    implementation(projects.history.ui)
    implementation(projects.history.presentation)
    implementation(projects.history.domain)
    implementation(projects.history.data)

    implementation(projects.opensourcenotices.ui)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.bundles.retrofit)
    implementation(libs.moshi.kotlin)

    implementation(libs.aboutlibraries.core)
    implementation(libs.aboutlibraries.compose)

    implementation(libs.hilt.android)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.navigation3.ui)
    ksp(libs.kotlin.metadata.jvm)
    ksp(libs.hilt.android.compiler)

    implementation(projects.analytics)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.hamcrest)
    testImplementation(libs.test.konsist)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
    androidTestImplementation(libs.test.androidx.espresso.contrib)
    androidTestImplementation(libs.test.androidx.espresso.intents)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.compose.ui.junit4)
    androidTestImplementation(libs.test.android.hilt)
    androidTestImplementation(libs.test.android.uiautomator)
    androidTestImplementation(projects.architecture.instrumentationTest)
    androidTestImplementation(libs.test.android.mockwebserver)
    kspAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.test.mockito) {
        exclude("net.bytebuddy")
    }
    androidTestImplementation(libs.test.mockito.kotlin)
    androidTestImplementation(libs.test.mockito.android)

    implementation(libs.androidx.tracing)

    debugImplementation(libs.debug.compose.ui.tooling)
    debugImplementation(libs.debug.compose.ui.manifest)
}

val installGitHook = tasks.register<Copy>("installGitHook") {
    from(File(rootProject.rootDir, "automation/git/pre-commit"))
    into(File(rootProject.rootDir, ".git/hooks"))
    filePermissions {
        user {
            read = true
            write = true
            execute = true
        }
        group {
            read = true
            execute = true
        }
        other {
            read = true
            execute = true
        }
    }
}

tasks.getByPath(":app:preBuild").dependsOn(installGitHook)

tasks.withType(Test::class) {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(
            TestLogEvent.SKIPPED,
            TestLogEvent.PASSED,
            TestLogEvent.FAILED
        )
        showStandardStreams = true
    }
}
