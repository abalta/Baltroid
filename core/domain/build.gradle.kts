plugins {
    id("baltroid.android.library")
    kotlin("kapt")
}

android {
    namespace = "com.baltroid.core.domain"
}

dependencies {
    api(project(":core:common"))
    implementation(project(":core:network"))
    
    implementation(libs.hilt.android)
    implementation(libs.pagingv3)

    kapt(libs.hilt.compiler)
}