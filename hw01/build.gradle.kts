plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.ferick"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.springframework", name = "spring-context", version = libs.versions.spring.get())
    implementation(group = "com.opencsv", name = "opencsv", version = libs.versions.opencsv.get())
    testImplementation(group = "org.jetbrains.kotlin", name = "kotlin-test", version = libs.versions.kotlin.get())
    testImplementation(group = "io.mockk", name = "mockk",version = libs.versions.mockk.get())
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}