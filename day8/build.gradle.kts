plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("my-java-library")
}


dependencies {
    testImplementation(kotlin("test"))
}
