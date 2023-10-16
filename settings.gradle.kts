dependencyResolutionManagement {
    repositories.mavenCentral()
}

pluginManagement {
    repositories.gradlePluginPortal();

    includeBuild("gradle/plugins")
}

include("day4")
include("day5")
include("day6")
include("day7")
include("day8")
include("day9")
include("days")