plugins {
    id("baltroid.android.library")
}

android.namespace = "com.baltroid.core.datastore"

dependencies {
    api(libs.androidx.datastore.preferences)
    implementation(libs.dagger.hilt.android)
}