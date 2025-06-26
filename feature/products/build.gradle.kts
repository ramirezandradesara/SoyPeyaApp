plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

hilt {
    enableAggregatingTask = false
}

android {
    namespace = "com.soyhenryfeature.products"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.activity)

    implementation(libs.androidx.navigation.compose)
    implementation(project(":feature:cart"))
    implementation(project(":core:model"))
    implementation(project(":core:approutes"))
    implementation(project(":core:constants:appinfo"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.lifecycle.viewmodel) // ViewModel

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("io.coil-kt:coil-compose:2.4.0")
    testImplementation(libs.junit)

    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.navigation.ui)
    implementation (libs.lifecycle.viewmodel)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}