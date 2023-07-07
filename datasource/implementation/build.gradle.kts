plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.mitteloupe.whoami.datasource"
    compileSdk = 33

    defaultConfig {
        minSdk = 22

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ktlint {
    version.set("0.49.1")
    android.set(true)
}

detekt {
    config.setFrom("$projectDir/detekt.yml")
}

dependencies {
    implementation(project(":datasource-architecture"))
    implementation(project(":datasource-source"))
    implementation(project(":coroutine"))

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.hamcrest)
    testImplementation(libs.test.mockito)
    testImplementation(libs.test.mockito.kotlin)
    testImplementation(libs.test.kotlinx.coroutines)
    testImplementation(project(":coroutine-test"))

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
