import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.baltroid.apps.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
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
        register("androidFirebase") {
            id = "baltroid.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
        register("jvmLibrary") {
            id = "baltroid.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}