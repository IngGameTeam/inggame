buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
        val kotlinVersion = "1.6.10"
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath(kotlin("serialization", version = kotlinVersion))
    }
}
val kotlin_version = "1.6.10"
plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
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
        maven { url = uri("https://repo.codemc.io/repository/maven-public/") }
        maven { url = uri("https://papermc.io/repo/repository/maven-public/") }

    }

    dependencies {
        compileOnly(rootProject.fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

        testImplementation(kotlin("test"))
        testImplementation("com.github.seeseemelk:MockBukkit-v1.18:1.24.1")
        testImplementation("org.slf4j:slf4j-api:1.7.36")
        testImplementation("org.slf4j:slf4j-simple:1.7.36")
        compileOnly(kotlin("test"))

        compileOnly("net.kyori:adventure-api:4.10.1")
        compileOnly("io.github.monun:invfx-api:3.1.0")
        compileOnly("com.mojang:authlib:1.5.21")
        compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
        compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")
        compileOnly("com.eatthepath:fast-uuid:0.2.0")
        compileOnly("net.jafama:jafama:2.3.2")
        compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
        compileOnly("org.mongodb:mongodb-driver-sync:4.6.0")
        compileOnly("org.json:json:20220924")
        testApi("org.json:json:20220924")
        api("io.papermc:paperlib:1.0.7")
        api("net.openhft:compiler:2.4.1")
        compileOnly("com.github.NuVotifier:NuVotifier:2.7.2")
        compileOnly("io.javalin:javalin:5.1.3")
        compileOnly(group = "org.popcraft", name = "chunky-common", version = "1.3.38")
    }

}

fun String.runCommand(workingDir: File = file("./")): String {
    val parts = this.split("\\s".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

    proc.waitFor(1, TimeUnit.MINUTES)
    return proc.inputStream.bufferedReader().readText().trim()
}

val gitTag = "git describe --abbrev=0 --tags".runCommand()
val gitCommitId = "git rev-parse --short=8 HEAD".runCommand()

rootProject.version = gitCommitId
println(rootProject.version)
fun childTree(p: Project) {
    p.childProjects.values.forEach {
        it.apply {
            if (version == "unspecified") {
                version = rootProject.version
            }
        }
        var parentProj = it.parent
        while (parentProj !== null && parentProj !== rootProject) {
            parentProj.dependencies.apply {
                api(it)
                testApi(it)
            }
            parentProj = parentProj.parent
        }
        childTree(it)
    }
}
childTree(rootProject)

