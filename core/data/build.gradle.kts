plugins {
    id("baltroid.android.library")
    id("baltroid.android.hilt")
}

android {
    namespace = "com.baltroid.apps.core.data"
}

dependencies {
    implementation(project(":core:firestore"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
}