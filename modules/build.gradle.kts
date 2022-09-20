
plugins {
    id("maven-publish")
}

subprojects {
    apply(plugin = "maven-publish")


    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                val REPO = rootProject.name
                val OWNER = findProperty("github.owner")

                url = uri("https://maven.pkg.github.com/$OWNER/$REPO")
                credentials {

                    username = findProperty("github.username") as? String
                    password = findProperty("github.token") as? String
                }
            }
        }
        publications {
            register<MavenPublication>(project.name) {
                groupId = rootProject.properties["group"]?.toString()!!
                artifactId = project.name
                version = project.version.toString()
//            artifact(tasks["jar"])
                from(components["java"])
            }
        }

    }


}