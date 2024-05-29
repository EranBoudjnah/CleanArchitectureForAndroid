import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("com.google.android.gms.oss-licenses-plugin")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.mitteloupe.whoami"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.mitteloupe.whoami"
        minSdk = 22
        targetSdk = 34
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
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
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
    implementation(projects.datasourceArchitecture)
    implementation(projects.datasourceSource)
    implementation(projects.datasourceImplementation)

    implementation(projects.architectureUi)
    implementation(projects.architecturePresentation)
    implementation(projects.architectureDomain)

    implementation(projects.homeUi)
    implementation(projects.homePresentation)
    implementation(projects.homeDomain)
    implementation(projects.homeData)

    implementation(projects.historyUi)
    implementation(projects.historyPresentation)
    implementation(projects.historyDomain)
    implementation(projects.historyData)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.bundles.retrofit)
    implementation(libs.moshi.kotlin)

    implementation(libs.opensource.licenses)

    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.recyclerview)
    ksp(libs.hilt.android.compiler)

    implementation(projects.analytics)

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
    androidTestImplementation(projects.architectureInstrumentationTest)
    androidTestImplementation(libs.test.android.mockwebserver)
    kspAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.test.mockito) {
        exclude("net.bytebuddy")
    }
    androidTestImplementation(libs.test.mockito.kotlin)
    androidTestImplementation(libs.test.mockito.android)

    debugImplementation(libs.debug.compose.ui.tooling)
    debugImplementation(libs.debug.compose.ui.manifest)
}

val installGitHook = tasks.register<Copy>("installGitHook") {
    from(File(rootProject.rootDir, "automation/git/pre-commit"))
    into(File(rootProject.rootDir, ".git/hooks"))
    fileMode = 0b111111101
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
