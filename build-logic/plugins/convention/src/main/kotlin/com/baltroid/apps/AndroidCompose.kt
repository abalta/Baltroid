package com.baltroid.apps

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *>) =
    with(commonExtension) {
        defaultConfig.vectorDrawables.useSupportLibrary = true
        buildFeatures.compose = true
        composeOptions.kotlinCompilerExtensionVersion =
            libs.versions.androidx.compose.compiler.get()

        dependencies {
            val bom = libs.androidx.compose.bom
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))
        }
    }