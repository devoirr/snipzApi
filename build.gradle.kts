plugins {
    kotlin("jvm") version "2.1.10"
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

group = "me.snipz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.purpurmc.org/snapshots")
}

dependencies {
    compileOnly("org.purpurmc.purpur", "purpur-api", "1.21.3-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(21)
}

tasks.shadowJar {
    dependencies {
        exclude(dependency("org.jetbrains:annotations"))
    }

    archiveFileName.set("snipzApi.jar")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}