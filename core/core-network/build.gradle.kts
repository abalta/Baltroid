plugins {
    id("baltroid.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android.namespace = "com.baltroid.core.network"

dependencies {
    implementation(project(":core:core-common"))
    implementation(project(":core:core-datastore"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.retrofit)
    implementation(libs.javax.inject)
}