plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.mitteloupe.whoami.home.ui"
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
        kotlinCompilerExtensionVersion = "1.5.9"
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
    implementation(projects.homePresentation)

    implementation(projects.architectureUi)
    implementation(projects.architecturePresentation)

    implementation(projects.coroutine)
    implementation(projects.widget)

    implementation(projects.analytics)

    implementation(platform(libs.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    implementation(libs.opensource.licenses)

    debugImplementation("androidx.compose.ui:ui-tooling")

    testImplementation(libs.test.junit)
    testImplementation(libs.test.hamcrest)
    testImplementation(libs.test.mockito.kotlin)

    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.androidx.espresso.core)
}
