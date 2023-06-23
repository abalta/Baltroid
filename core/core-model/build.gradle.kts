plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.androidx.annotation)
    implementation(project(":core:core-common"))
}