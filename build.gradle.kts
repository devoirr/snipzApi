plugins {
    kotlin("jvm") version "2.1.10"
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

group = "me.snipz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.purpurmc.org/snapshots")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    compileOnly("org.purpurmc.purpur", "purpur-api", "1.21.4-R0.1-SNAPSHOT")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
    implementation("com.github.retrooper:packetevents-spigot:2.7.0")
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