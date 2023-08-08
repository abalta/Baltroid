plugins {
    id("baltroid.android.library")
    kotlin("kapt")
}

android {
    namespace = "com.baltroid.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    kapt(libs.hilt.compiler)
}