import java.awt.SystemColor.window

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.hilt)
//    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.ykis.ykispam"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ykis.ykispam"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.materialWindow)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.adaptive)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.constraintlayout)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
//    implementation("androidx.compose.runtime:runtime-livedata")
//    implementation(libs.SSJetPackComposeProgressButton)
    implementation("com.github.SimformSolutionsPvtLtd:SSJetPackComposeProgressButton:1.0.6")


//    firebase
    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-config-ktx")
    implementation(libs.play.services.auth)




//    hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
//    ksp(libs.hilt.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)
// Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.legacy.support.v4)

    kapt(libs.androidx.room.compiler)
//    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.scalars)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.gson)

    //okHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)


    //coil
    implementation(libs.coil.compose)
    //Xpay
//    implementation(libs.pustovitandriy.xpay.android.sdk)
//    implementation("com.github.pustovitandriy:XPAY_Android_SDK:1.0.0")


//    implementation(libs.play.services)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    androidTestImplementation(libs.ui.test.junit4)
//    debugImplementation(libs.ui.tooling)
//    debugImplementation(libs.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
    useBuildCache = false
    showProcessorStats = true
}