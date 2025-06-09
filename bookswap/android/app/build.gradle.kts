plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.flutter.flutter-gradle-plugin")
}

android {
    namespace = "com.example.bookswap"
    compileSdk = flutter.compileSdkVersion
    ndkVersion = "29.0.13113456"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // Update to 17 for future-proofing
        targetCompatibility = JavaVersion.VERSION_17 // Update to 17 for future-proofing
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString() // Update to 17 for future-proofing
    }

    defaultConfig {
        applicationId = "com.example.bookswap"
        minSdk = flutter.minSdkVersion
        targetSdk = flutter.targetSdkVersion
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

flutter {
    source = "../.."
}