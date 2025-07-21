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

rootProject.name = "SoyPeyaApp"
include(":app")
include(":data")
include(":feature:login")
include(":feature:register")
include(":feature:products")
include(":feature:cart")
include(":feature:navigation")
include(":feature:profile")
include(":feature:orders")
include(":library")
include(":core")
