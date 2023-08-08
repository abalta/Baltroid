plugins {
    id("baltroid.android.library")
    id("baltroid.android.hilt")
}

android {
    namespace = "com.baltroid.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}