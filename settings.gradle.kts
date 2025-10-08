pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RVideo"
include(":app")
include(":features:auth:api")
include(":features:auth:impl")
include(":features:home:api")
include(":features:home:impl")
include(":core:network:api")
include(":core:network:impl")
include(":core:database:api")
include(":core:database:impl")
include(":core:auth:api")
include(":core:auth:impl")
include(":core:navigation:api")
include(":core:navigation:impl")
include(":common:api")
include(":common:impl")
include(":common:ui")
