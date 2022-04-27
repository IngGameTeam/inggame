tasks {
    processResources {
        filesMatching("**/*.yml") {
            expand(rootProject.properties)
        }
    }
}
