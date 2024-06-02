plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.22"
    id("io.gitlab.arturbosch.detekt").version("1.23.5")
}

group = "com.ferick"
version = "0.1.0"

java {
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-integration")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-data-jpa")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-validation")
    implementation(group = "org.springframework.integration", name = "spring-integration-jpa")
    implementation(group = "com.h2database", name = "h2", version = libs.versions.h2.get())
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = libs.versions.coroutines.get())
    implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = libs.versions.jackson.get())
    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
    testImplementation(group = "io.mockk", name = "mockk",version = libs.versions.mockk.get())
    testImplementation(group = "com.ninja-squad", name = "springmockk", version = libs.versions.springmockk.get())
    testImplementation(group = "org.apache.commons", name = "commons-lang3", version = libs.versions.commonsLang.get())
    implementation(kotlin("stdlib-jdk8"))
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

allOpen {
    annotation("jakarta.persistence.Entity")
}