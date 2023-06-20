pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

rootProject.name = "Baltroid"

include(":app")
include(":core:core-common")
include(":core:core-network")
include(":core:core-data")
include(":core:core-database")
include(":core:core-domain")
include(":core:core-datastore")
include(":core:core-model")
include(":core:core-ui")
/*
include(":benchmark")
include(":core:core-designsystem")
include(":core:core-navigation")
include(":features:feature-home")
include(":features:feature-search")
include(":features:feature-wishlist")
include(":features:feature-settings")
include(":features:feature-list")
include(":features:feature-details")*/

