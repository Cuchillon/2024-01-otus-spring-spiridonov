plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("io.gitlab.arturbosch.detekt").version("1.23.5")
}

group = "com.ferick"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.springframework.boot", name = "spring-boot-starter")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect")
    implementation(group = "com.opencsv", name = "opencsv", version = libs.versions.opencsv.get())
    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
    testImplementation(group = "io.mockk", name = "mockk",version = libs.versions.mockk.get())
}

tasks.test {
    useJUnitPlatform()
}

tasks.detekt {
    config.setFrom("config/detekt/detekt.yml")
}

kotlin {
    jvmToolchain(17)
}