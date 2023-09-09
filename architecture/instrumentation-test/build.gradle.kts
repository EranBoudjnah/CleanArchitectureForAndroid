plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.mitteloupe.whoami.test"
    compileSdk = 34

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
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
    config.setFrom("$projectDir/../../detekt.yml")
}

dependencies {
    implementation(projects.architecturePresentation)

    implementation(projects.coroutine)
    implementation(projects.widget)

    implementation(platform(libs.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation(libs.test.junit)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso.core)
    implementation("androidx.compose.ui:ui-test-junit4")
    implementation(libs.test.android.hilt)
    implementation(libs.test.android.uiautomator)
    implementation(libs.okhttp3)
    implementation(libs.test.android.mockwebserver)
    implementation(libs.androidx.appcompat)
    implementation(libs.test.androidx.rules)
    implementation(libs.androidx.recyclerview)
}
