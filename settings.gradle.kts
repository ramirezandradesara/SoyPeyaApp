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
include(":core:model")
include(":data")
include(":library:utils")
include(":feature:login")
include(":feature:register")
include(":library:ui:components")
include(":core:approutes")
include(":core:constants:appinfo")
include(":feature:products")
include(":feature:cart")
include(":feature:navigation")
include(":feature:profile")
include(":feature:orders")
include(":library2")
