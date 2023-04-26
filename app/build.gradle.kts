plugins {
    id("baltroid.android.application")
    id("baltroid.android.application.compose")
    id("baltroid.android.lint")
    id("baltroid.android.signingconfig")

    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.baltroid.apps"

    defaultConfig {
        applicationId = "com.baltroid.apps"
        versionCode = 1
        versionName = "1.0.0"
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.compose.material)
}