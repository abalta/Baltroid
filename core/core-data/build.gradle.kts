plugins {
    id("baltroid.android.library")
}

android.namespace = "com.baltroid.core.data"

dependencies {
    implementation(project(":core:core-database"))
    implementation(project(":core:core-datastore"))
    implementation(project(":core:core-network"))
    implementation(project(":core:core-domain"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
    implementation(libs.androidx.paging.common)
    implementation(libs.underscore)
    implementation(libs.google.gson)
}