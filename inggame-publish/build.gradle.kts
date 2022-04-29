plugins {
    `maven-publish`
}
apply(plugin = "com.github.johnrengelman.shadow")
val rootName = "inggame-modules"
val repoName = "inggameteam"

dependencies {
    implementation(project(":inggame-modules"))
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    archiveFileName.set("$rootName.jar")
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>(rootName) {
            groupId = "io.github.$repoName"
            artifactId = rootName
            version = rootProject.version.toString()
            artifact(tasks["shadowJar"])
        }
    }
    repositories {
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
            credentials {
                val prop = org.jetbrains.kotlin.konan.properties.Properties()
                prop.load(rootProject.file("env.properties").inputStream())
                username = (prop["gpr.user"] ?: System.getenv("gpr.user"))?.toString()
                password = (prop["gpr.key"] ?: System.getenv("gpr.key"))?.toString()
            }
        }
    }
}
