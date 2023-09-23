include("day4")

dependencyResolutionManagement {
    repositories.mavenCentral()
}

pluginManagement {
    repositories.gradlePluginPortal();

    includeBuild("gradle/plugins")
}

