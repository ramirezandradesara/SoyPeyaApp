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
include(":appmodule")
include(":feature:cart")
include(":core:model")
include(":data")
include(":library:utils")
include(":feature:card")
include(":feature:login")
include(":feature:home")
include(":feature:register")
include(":library:ui:components")
