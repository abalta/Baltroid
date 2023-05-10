plugins {
    id("baltroid.android.library")
    id("baltroid.android.library.compose")
}

android.namespace = "com.baltroid.core.ui"

dependencies {
    api(project(":core:core-model"))
    api(project(":core:core-domain"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.androidx.paging.compose)
}