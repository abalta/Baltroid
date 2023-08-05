plugins {
    id("baltroid.android.library")
    id("baltroid.android.hilt")
}

android {
    namespace = "com.baltroid.apps.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}