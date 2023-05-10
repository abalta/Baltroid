plugins {
    id("baltroid.android.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android.namespace = "com.baltroid.core.database"

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)
    implementation(libs.bundles.androidx.room)
    ksp(libs.androidx.room.compiler)
    implementation(libs.javax.inject)
}