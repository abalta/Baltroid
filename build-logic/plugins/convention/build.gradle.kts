import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.baltroid.apps.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "baltroid.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "baltroid.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "baltroid.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "baltroid.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "baltroid.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidBenchmark") {
            id = "baltroid.android.benchmark"
            implementationClass = "AndroidBenchmarkConventionPlugin"
        }
        register("androidLint") {
            id = "baltroid.android.lint"
            implementationClass = "AndroidLintConventionPlugin"
        }
        register("androidSigningConfig") {
            id = "baltroid.android.signingconfig"
            implementationClass = "AndroidSigningConfigConventionPlugin"
        }
        register("androidFlavors") {
            id = "baltroid.android.application.flavors"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
        register("spotless") {
            id = "baltroid.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
        register("detekt") {
            id = "baltroid.detekt"
            implementationClass = "DetektConventionPlugin"
        }
        register("androidHilt") {
            id = "baltroid.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
    }
}