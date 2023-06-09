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
    }
}

rootProject.name = "Baltroid"

include(":app")
include(":core:core-common")
include(":core:core-network")
/*
include(":benchmark")
include(":core:core-data")
include(":core:core-database")
include(":core:core-datastore")
include(":core:core-domain")
include(":core:core-model")
include(":core:core-ui")
include(":core:core-designsystem")
include(":core:core-navigation")
include(":features:feature-home")
include(":features:feature-search")
include(":features:feature-wishlist")
include(":features:feature-settings")
include(":features:feature-list")
include(":features:feature-details")*/
