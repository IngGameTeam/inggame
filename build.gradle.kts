buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}
val kotlin_version = "1.6.10"
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

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.github.johnrengelman.shadow")

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("${project.name}.jar")
    }

    repositories {
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

        testImplementation(kotlin("test"))
        testImplementation("com.github.seeseemelk:MockBukkit-v1.18:1.24.1")
        testImplementation("org.slf4j:slf4j-api:1.7.36")
        testImplementation("org.slf4j:slf4j-simple:1.7.36")
        testImplementation("io.github.brucefreedy:mccommand:1.0.2")
        compileOnly(kotlin("test"))
        compileOnly("io.github.brucefreedy:mccommand:1.0.2")
        compileOnly("net.kyori:adventure-api:4.10.1")
        compileOnly("io.github.monun:invfx-api:3.1.0")
        compileOnly("com.mojang:authlib:1.5.21")
        compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
        compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")
        compileOnly("com.eatthepath:fast-uuid:0.2.0")
        compileOnly("net.jafama:jafama:2.3.2")
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    }

    if (version == "unspecified") {
        version = rootProject.version
    }

}

fun childTreeApi(p: Project) {
    p.childProjects.values.forEach {
        var parentProj = it.parent
        while (parentProj !== null && parentProj !== rootProject) {
            parentProj.dependencies.api(it)
            parentProj = parentProj.parent
        }
        childTreeApi(it)
    }
}
childTreeApi(rootProject)
