import com.github.jengelman.gradle.plugins.shadow.transformers.AppendingTransformer
import com.github.jengelman.gradle.plugins.shadow.transformers.ManifestResourceTransformer

plugins {
    kotlin("jvm") version "1.9.21"
    id("io.gitlab.arturbosch.detekt").version("1.23.3")
    id("com.github.johnrengelman.shadow").version("8.1.1")
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

tasks.detekt {
    config.setFrom("config/detekt/detekt.yml")
}

tasks.jar {
    manifest {
        attributes(mapOf("Main-Class" to "com.ferick.ApplicationKt"))
    }
    val  contents = configurations.runtimeClasspath.get()
        .map { if (it.isDirectory) it else zipTree(it) } + sourceSets["main"].output
    from(contents)
}

tasks.shadowJar {
    archiveBaseName.set("ferick")
    archiveClassifier.set("hw01")
    archiveVersion.set(project.version.toString())
    transform(AppendingTransformer::class.java) {
        resource = "META-INF/spring.handlers"
    }
    transform(AppendingTransformer::class.java) {
        resource = "META-INF/spring.schemas"
    }
    transform(ManifestResourceTransformer::class.java) {
        mainClass = "com.ferick.ApplicationKt"
    }
}

kotlin {
    jvmToolchain(17)
}