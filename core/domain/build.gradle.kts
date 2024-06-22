plugins {
    id("baltroid.android.library")
    id("baltroid.android.hilt")
}

android {
    namespace = "com.baltroid.core.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)
}