plugins {
    id("baltroid.android.application")
    id("baltroid.android.application.compose")
    id("baltroid.android.lint")
    id("baltroid.android.signingconfig")

    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.baltroid.apps"

    defaultConfig {
        applicationId = "com.baltroid.apps"
        versionCode = 3
        versionName = "1.0.4"
    }
}

dependencies {
    implementation(project(":core:core-common"))
    implementation(project(":core:core-data"))
    implementation(project(":core:core-database"))
    implementation(project(":core:core-datastore"))
    implementation(project(":core:core-network"))
    implementation(project(":core:core-domain"))
    implementation(project(":core:core-model"))
    implementation(project(":core:core-ui"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.room.runtime)

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.paging.compose)
    implementation(libs.material.dialogs.datetime)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.coil.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.accompanist.navigation.animation)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.revenuecat)
}