plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.pruebacamaraappplantas"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pruebacamaraappplantas"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true // Habilitar Jetpack Compose
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    //api
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.google.android.material:material:1.3.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation ("androidx.core:core-splashscreen:1.0.0")  // Añadir la biblioteca de SplashScreen
    implementation ("androidx.biometric:biometric:1.2.0-alpha05")
    implementation ("com.tbuonomo:dotsindicator:4.3")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("androidx.activity:activity-compose:1.8.2")
    implementation ("androidx.compose.ui:ui:1.5.3")
    implementation ("androidx.compose.material:material:1.5.3")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.3")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("com.google.mlkit:image-labeling:17.0.7")
    implementation ("io.coil-kt:coil-compose:2.0.0")

    // Dependencia para CameraX (si vas a capturar fotos con la cámara)
    implementation ("androidx.camera:camera-core:1.3.0")
    implementation ("androidx.camera:camera-camera2:1.3.0")
    implementation ("androidx.camera:camera-lifecycle:1.3.0")
    implementation ("androidx.camera:camera-view:1.3.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}