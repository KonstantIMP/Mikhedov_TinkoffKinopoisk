pluginManagement {
    repositories {
        gradlePluginPortal()

        mavenCentral()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        maven("https://jitpack.io")

        google()
        mavenCentral()
    }
}

rootProject.name = "kinopoisk_tinkoff"

include(":app")

