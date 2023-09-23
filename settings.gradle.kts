dependencyResolutionManagement {
    repositories.mavenCentral()
}

pluginManagement {
    repositories.gradlePluginPortal();

    includeBuild("gradle/plugins")
}

include("day4")
include("day5")