plugins {
    id("baltroid.android.library")
    id("baltroid.android.hilt")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.baltroid.core.firestore"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.coroutines)
}