typealias ShadowJar = com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}

plugins {
    kotlin("jvm") version Dependency.Kotlin.Version
    kotlin("plugin.lombok") version Dependency.Kotlin.Version
    id("org.jetbrains.kotlin.plugin.noarg") version Dependency.Kotlin.Version
    id("com.google.devtools.ksp") version Dependency.Ksp.Version
    application
}



allprojects {
    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")
    apply(plugin = "kotlin")
    apply(plugin = "org.gradle.application")
    apply(plugin = "com.github.johnrengelman.shadow")
    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("${rootProject.name}.jar")
    }

    application {
        mainClass.set("io.github.inggameteam.inggame.component.TestAppKt")
    }

    noArg {
        annotation("io.github.inggameteam.inggame.mongodb.Model")
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
        api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

        testCompileOnly("org.slf4j:slf4j-api:2.0.5")
        testCompileOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.19.0")

        compileOnly("org.spigotmc:spigot-api:${Dependency.PaperAPI.Version}")
        testCompileOnly("org.spigotmc:spigot-api:${Dependency.PaperAPI.Version}")

        compileOnly("org.jetbrains.kotlin:kotlin-stdlib:${Dependency.Kotlin.Version}")
        compileOnly("org.jetbrains.kotlin:kotlin-reflect:${Dependency.Kotlin.Version}")

        compileOnly("org.mongodb:mongodb-driver-sync:4.8.1")
        testCompileOnly("org.mongodb:mongodb-driver-sync:4.8.1")

        compileOnly("org.reflections:reflections:0.10.2")
        testCompileOnly("org.reflections:reflections:0.10.2")

        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        testCompileOnly("org.projectlombok:lombok:1.18.24")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

        implementation("io.insert-koin:koin-core:${Dependency.Koin.Version}")
        testImplementation("io.insert-koin:koin-test:${Dependency.Koin.Version}")
        testImplementation("io.insert-koin:koin-test-junit4:${Dependency.Koin.Version}")
        testImplementation("io.insert-koin:koin-test-junit5:${Dependency.Koin.Version}")


    }
    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        dependsOn(tasks.processResources)
        archiveFileName.set("${rootProject.name}.jar")

        doLast {
            copy {
                val sep = File.separator
                from("${buildDir.absolutePath}${sep}libs$sep${project.name}.jar")
                into("${project.buildDir.absolutePath}${sep}dist")
            }
        }
    }

    tasks {
        processResources {
            repeat(2) {
                filesMatching("**/*.yml") {
                    expand(HashMap(rootProject.properties)
                        .apply { putAll(project.properties) }
                        .apply { put("version", rootProject.version)})
                }
            }
        }
    }

}


