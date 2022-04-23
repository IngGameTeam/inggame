buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}
val kotlin_version = "1.6.10"
val project_name: String by project
plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.10" apply false
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "com.github.johnrengelman.shadow")
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("$project_name.jar")
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.dmulloy2.net/repository/public/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/public/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://libraries.minecraft.net/") }
    maven { url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/") }

}

dependencies {
    compileOnly ("com.mojang:authlib:1.5.21")
    compileOnly ("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
    compileOnly ("org.spigotmc:spigot:1.18-R0.1-SNAPSHOT")
    compileOnly ("com.eatthepath:fast-uuid:0.1")
    compileOnly ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    compileOnly ("io.github.monun:invfx-api:3.1.0")
}
