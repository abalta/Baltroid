plugins {
    id("baltroid.android.library")
    id("baltroid.android.library.compose")
}

android {
    namespace = "com.baltroid.model"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.compose.runtime)
}