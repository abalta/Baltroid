plugins {
    id("baltroid.android.library")
    kotlin("kapt")
}

android {
    namespace = "com.baltroid.core.domain"
}

dependencies {
    api(project(":core:common"))
    implementation(libs.hilt.android)

    kapt(libs.hilt.compiler)
}