plugins {
    id("baltroid.android.library")
    id("baltroid.android.hilt")
}

android.namespace = "com.baltroid.core.data"


dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}