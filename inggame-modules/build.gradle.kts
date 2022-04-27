val core = project(":${rootProject.name}-core")

subprojects {
    if (version == "unspecified") {
        version = rootProject.version
    }

}
dependencies {
    implementation(core)
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.1.2")
}
