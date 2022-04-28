val core = project(":${rootProject.name}-core")

subprojects {
    if (version == "unspecified") {
        version = rootProject.version
    }

}

dependencies {
    implementation(core)
}