pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) }
    }
}

rootProject.name = "Baltroid"

include(":app")
include(":core:common")
include(":core:network")
include(":core:firestore")
include(":core:data")
include(":core:model")
include(":core:designsystem")
include(":core:domain")
