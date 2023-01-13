dependencies {
    listOf(
        ":utils",
        ":mongodb",
        ":guiapi",
        ":command"
    ).forEach { compileOnly(project(it)); testCompileOnly(project(it)) }
}