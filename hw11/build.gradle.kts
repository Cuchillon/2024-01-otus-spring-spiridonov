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
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-data-mongodb-reactive")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-data-mongodb")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-webflux")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-validation")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-reactor")
    implementation(group = "io.projectreactor.kotlin", name = "reactor-kotlin-extensions")
    implementation(group = "com.fasterxml.jackson.module", name = "jackson-module-kotlin", version = libs.versions.jackson.get())
    implementation(group = "de.flapdoodle.embed", name = "de.flapdoodle.embed.mongo",version = libs.versions.flapdoodle.get())
    implementation(group = "de.flapdoodle.embed", name = "de.flapdoodle.embed.mongo.spring30x",version = libs.versions.flapdoodle.get())
    implementation(group = "com.github.cloudyrock.mongock", name = "mongock-spring-v5",version = libs.versions.mongock.get())
    implementation(group = "com.github.cloudyrock.mongock", name = "mongodb-springdata-v3-driver",version = libs.versions.mongock.get())
    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
    testImplementation(group = "io.projectreactor", name = "reactor-test")
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
    annotation("org.springframework.data.mongodb.core.mapping.Document")
}