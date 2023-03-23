@file:Suppress("deprecation")

typealias ShadowJar = com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "io.github.inggameteam.inggame"
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
    id("maven-publish")
}


allprojects {
    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")
    apply(plugin = "kotlin")
    apply(plugin = "org.gradle.application")
    apply(plugin = "com.github.johnrengelman.shadow")

    version = rootProject.version

    application {
        mainClass.set("io.github.inggameteam.inggame.component.TestAppKt")
    }

    noArg {
        annotations(
            "io.github.inggameteam.inggame.utils.NoArgsConstructor",
            "io.github.inggameteam.inggame.utils.Model"
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
        maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
        maven { url = uri("https://raw.githubusercontent.com/Bruce0203/library-mirror/spigot-1.19.3/") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/releases/") }
    }

    dependencies {
//        api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

        testImplementation("com.github.seeseemelk:MockBukkit-v1.18:1.24.1")

        testImplementation(platform("org.junit:junit-bom:5.9.2"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        compileOnly("com.eatthepath:fast-uuid:0.2.0")
        testApi("com.eatthepath:fast-uuid:0.2.0")

        compileOnly("io.github.bruce0203:nbt-api:6")
        testCompileOnly("io.github.bruce0203:nbt-api:6")

        compileOnly("io.papermc.paper:paper-api:${Dependency.PaperAPI.Version}")
        testCompileOnly("io.papermc.paper:paper-api:${Dependency.PaperAPI.Version}")

        compileOnly("org.jetbrains.kotlin:kotlin-stdlib:${Dependency.Kotlin.Version}")
        testApi("org.jetbrains.kotlin:kotlin-stdlib:${Dependency.Kotlin.Version}")
        compileOnly("org.jetbrains.kotlin:kotlin-reflect:${Dependency.Kotlin.Version}")
        testApi("org.jetbrains.kotlin:kotlin-reflect:${Dependency.Kotlin.Version}")

        compileOnly("org.mongodb:mongodb-driver-sync:${Dependency.MongoDB.Version}")
        testApi("org.mongodb:mongodb-driver-sync:${Dependency.MongoDB.Version}")

//        compileOnly("io.github.bruce0203:reflections:0.10.3.4")
//        testApi("io.github.bruce0203:reflections:0.10.3.4")

        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")
        testCompileOnly("org.projectlombok:lombok:1.18.24")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

        compileOnly("io.insert-koin:koin-core:${Dependency.Koin.Version}")
        testImplementation("io.insert-koin:koin-test:${Dependency.Koin.Version}")
        testImplementation("io.insert-koin:koin-test-junit4:${Dependency.Koin.Version}")
        testImplementation("io.insert-koin:koin-test-junit5:${Dependency.Koin.Version}")

        compileOnly("org.burningwave:core:12.62.6")
        testCompileOnly("org.burningwave:core:12.62.6")

    }



    tasks.withType<Jar> {
        dependsOn(tasks.processResources)
        archiveFileName.set("${project.name}.jar")
        if (project != rootProject) destinationDirectory.set(File(rootProject.buildDir, "dist"))
    }
    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        exclude("META-INF/**", "META-INF/MANIFEST.MF")
        dependsOn(tasks.processResources)
        archiveFileName.set("${project.name}.jar")
        if (project != rootProject) destinationDirectory.set(File(rootProject.buildDir, "dist"))
    }

    tasks {
        processResources {
            exclude("META-INF/**")
            repeat(2) {
                filesMatching("**/*.yml") {
                    expand(HashMap(rootProject.properties)
                        .apply { putAll(project.properties) }
                        .apply { put("version", rootProject.version)}
                        .apply { put("mainClass", "io.github.inggameteam.inggame.plugin.Plugin")}
                    )
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


    lateinit var sourcesArtifact: PublishArtifact


    tasks {
        artifacts {
            sourcesArtifact = archives(getByName("shadowJar")) {
                classifier = null
            }
        }
    }

    apply(plugin = "maven-publish")

    publishing {
        val repo = System.getenv("GITHUB_REPOSITORY")
        if (repo === null) return@publishing
        repositories {
            maven {
                url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
                credentials {

                    username = System.getenv("SONATYPE_USERNAME")
                    password = System.getenv("SONATYPE_PASSWORD")
                }
            }
        }
        publications {
            register<MavenPublication>(project.name) {
//                val githubUserName = repo.substring(0, repo.indexOf("/"))
                groupId = "io.github.bruce0203"
                artifactId = rootProject.name + "-" + project.name.toLowerCase()
                version = System.getenv("GITHUB_BUILD_NUMBER")?: project.version.toString()
                artifact(sourcesArtifact)
            }
        }

    }

}

