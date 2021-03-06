plugins {
    `maven-publish`
}
apply(plugin = "com.github.johnrengelman.shadow")
val rootName = "inggame-modules"

dependencies {
    implementation(project(":modules"))
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    archiveFileName.set("$rootName.jar")
    archiveClassifier.set("")
}

publishing {
    publications {
        create<MavenPublication>(rootName) {
            groupId = rootProject.properties["group"]?.toString()!!
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
                val file = File(projectDir, "env.properties")
                if (file.exists()) {
                    prop.load(file.inputStream())
                }
                username = prop["gpr.user"]?.toString()?: ""
                password = prop["gpr.key"]?.toString()?: ""

//                username = (prop["gpr.user"] ?: System.getenv("gpr.user"))?.toString()?: ""
//                password = (prop["gpr.key"] ?: System.getenv("gpr.key"))?.toString()?: ""
            }
        }
    }
}
