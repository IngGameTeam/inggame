typealias ShadowJar = com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

version = "${ProjectVersion.gitTag}-${ProjectVersion.gitCommitId}"

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

    version = rootProject.version

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        archiveFileName.set("${rootProject.name}.jar")
    }

    application {
        mainClass.set("io.github.inggameteam.inggame.component.TestAppKt")
    }

    noArg {
        annotations(
            "io.github.inggameteam.inggame.mongodb.NoArgsConstructor",
            "io.github.inggameteam.inggame.mongodb.Model"
        )
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
        maven { url = uri("https://repo.papermc.io/repository/maven-public/") }

    }

    dependencies {
//        api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

        testImplementation("com.github.seeseemelk:MockBukkit-v1.18:1.24.1")

        testImplementation(platform("org.junit:junit-bom:5.9.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        compileOnly("com.eatthepath:fast-uuid:0.2.0")
        testApi("com.eatthepath:fast-uuid:0.2.0")

        compileOnly("org.spigotmc:spigot-api:${Dependency.PaperAPI.Version}")
        testCompileOnly("org.spigotmc:spigot-api:${Dependency.PaperAPI.Version}")

        compileOnly("org.jetbrains.kotlin:kotlin-stdlib:${Dependency.Kotlin.Version}")
        testApi("org.jetbrains.kotlin:kotlin-stdlib:${Dependency.Kotlin.Version}")
        compileOnly("org.jetbrains.kotlin:kotlin-reflect:${Dependency.Kotlin.Version}")
        testApi("org.jetbrains.kotlin:kotlin-reflect:${Dependency.Kotlin.Version}")

        compileOnly("org.mongodb:mongodb-driver-sync:4.8.1")
        testApi("org.mongodb:mongodb-driver-sync:4.8.1")

        compileOnly("io.github.bruce0203:reflections:0.10.3")
        testApi("io.github.bruce0203:reflections:0.10.3")

        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        testCompileOnly("org.projectlombok:lombok:1.18.24")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

        compileOnly("io.insert-koin:koin-core:${Dependency.Koin.Version}")
        testImplementation("io.insert-koin:koin-test:${Dependency.Koin.Version}")
        testImplementation("io.insert-koin:koin-test-junit4:${Dependency.Koin.Version}")
        testImplementation("io.insert-koin:koin-test-junit5:${Dependency.Koin.Version}")


    }
    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        exclude("META-INF/**", "META-INF/MANIFEST.MF")
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
            exclude("META-INF/**")
            repeat(2) {
                filesMatching("**/*.yml") {
                    expand(HashMap(rootProject.properties)
                        .apply { putAll(project.properties) }
                        .apply { put("version", rootProject.version)})
                }
            }
        }

    }

    tasks.test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }


}


