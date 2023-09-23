plugins {
    id("java")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}