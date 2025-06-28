plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    kotlin("kapt")
}

hilt {
    enableAggregatingTask = false 
}

android {
    namespace = "com.soyhenry.soypeyaapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.soyhenry.soypeyaapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.retrofit) // Retrofit
    implementation(libs.retrofit.gson) // Gson
    implementation(libs.okhttp.logging)  // HTTP request debugger

    implementation(libs.lifecycle.viewmodel) // ViewModel
    implementation(libs.lifecycle.livedata) // LiveData

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.navigation.fragment) // Navigation
    implementation(libs.navigation.ui) // Navigation

    implementation(libs.glide)
    kapt(libs.glide.compiler)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.javapoet)

    implementation(project(":feature:login"))
    implementation(project(":feature:register"))
    implementation(project(":feature:products"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:navigation"))
    implementation(project(":core:approutes"))

    implementation(libs.cloudinary)

    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}