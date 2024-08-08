plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.mitteloupe.whoami.test"
    compileSdk = 34

    defaultConfig {
        minSdk = 22
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
        kotlinCompilerExtensionVersion = "1.5.15"
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
    implementation(libs.test.androidx.junit)
    implementation(libs.test.androidx.espresso.core)
    implementation("androidx.compose.ui:ui-test-junit4")
    implementation(libs.test.android.hilt)
    implementation(libs.test.android.uiautomator)
    implementation(libs.test.androidx.espresso.core)
    implementation(libs.okhttp3)
    implementation(libs.test.android.mockwebserver)
    implementation(libs.androidx.appcompat)
    implementation(libs.test.androidx.rules)
    implementation(libs.androidx.recyclerview)
    implementation(kotlin("reflect"))
}
