plugins {
    kotlin("android")
    id("com.android.library")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.gradle.plugin)
    kotlin("kapt")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

android {
    compileSdk = 35
    namespace = "com.activecampaign.theme"

    defaultConfig {
        minSdk = 24
    }

    dependencies {
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.google.fonts)
        implementation(libs.hilt.android)
        implementation(libs.hilt.navigation.compose)
        kapt(libs.hilt.compiler)
        implementation(libs.datastore.preferences)
        implementation(libs.androidx.core.ktx)
        testImplementation(libs.junit5.api)
        testImplementation(libs.junit5.params)
        testRuntimeOnly(libs.junit5.engine)
        testImplementation(libs.mockk)
        testImplementation(libs.coroutines.test)
        testImplementation(libs.kotlin.test)
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
    }
}
