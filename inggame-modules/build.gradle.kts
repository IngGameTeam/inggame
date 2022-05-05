subprojects {
    if (version == "unspecified") {
        version = rootProject.version
    }

}

dependencies {
    childProjects.values.forEach { api(it) }
    implementation(project(":inggame-modules:inggame-api"))
}
