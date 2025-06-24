import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.dev.exploreminsk"
    compileSdk = 35

    val localProperties = Properties()
    val localPropertiesFile = File(rootDir, "local.properties")
    if(localPropertiesFile.exists() && localPropertiesFile.isFile){
        localPropertiesFile.inputStream().use {
            localProperties.load(it)
        }
    }

    defaultConfig {
        applicationId = "com.dev.exploreminsk"
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
            buildConfigField("String", "supabaseUrl", localProperties.getProperty("supabaseUrl"))
            buildConfigField("String", "supabaseSecret", localProperties.getProperty("supabaseSecret"))
        }
        debug {
            buildConfigField("String", "supabaseUrl", localProperties.getProperty("supabaseUrl"))
            buildConfigField("String", "supabaseSecret", localProperties.getProperty("supabaseSecret"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.androidx.media3.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //splash-screen
    implementation (libs.androidx.core.splashscreen)

    //room
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    //dagger-hilt
    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)

     //datastore
    implementation(libs.androidx.datastore.preferences)

    //supabase
    implementation(platform(libs.bom))
    implementation(libs.supabase.postgrest.kt)
    implementation (libs.storage.kt)
    implementation(libs.supabase.auth.kt)
    implementation(libs.gotrue.kt) // проверь последнюю


    //serialization
    implementation(libs.kotlinx.serialization.json)

    //ktor
    implementation(libs.ktor.client.android)

    //coil
    implementation(libs.coil.compose)

    //accompanist-navigation
    implementation(libs.accompanist.navigation.animation)

    //gson
    implementation ("com.google.code.gson:gson:2.8.8")


}