plugins {
    id("baltroid.android.library")
    id("baltroid.android.hilt")
}

android {
    namespace = "com.baltroid.core.data"
}

dependencies {
    implementation(project(":core:firestore"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
}